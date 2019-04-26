package com.gykj.rollcall.ui.index;

import android.databinding.Observable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.view.Gravity;

import com.google.gson.Gson;
import com.gykj.mvvmlibrary.base.BaseActivity;
import com.gykj.mvvmlibrary.entity.callrealm.CallRealm;
import com.gykj.mvvmlibrary.utils.DateUtil;
import com.gykj.mvvmlibrary.utils.KLog;
import com.gykj.mvvmlibrary.utils.LunarCalendar;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.mvvmlibrary.utils.Utils;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.app.RollCallApplication;
import com.gykj.rollcall.callback.CallTimerBack;
import com.gykj.rollcall.databinding.ActivityMainBinding;
import com.gykj.rollcall.entity.CityInfo;
import com.gykj.rollcall.entity.WeatherEntity;
import com.gykj.rollcall.manager.RCManager;
import com.gykj.rollcall.mq.RabbiMqEngine;
import com.gykj.rollcall.ui.analyse.AnalyseFragment;
import com.gykj.rollcall.ui.borrow.BorrowFragment;
import com.gykj.rollcall.ui.call.CallFragment;
import com.gykj.rollcall.ui.door.DoorFragment;
import com.gykj.rollcall.ui.loss.LossFragment;
import com.gykj.rollcall.ui.notice.NoticeFragment;
import com.gykj.rollcall.ui.police.PoliceFragment;
import com.gykj.rollcall.ui.setting.SettingFragment;
import com.gykj.rollcall.utils.FragmentFactory;
import com.gykj.rollcall.utils.HttpUtils;
import com.gykj.rollcall.utils.LocationUtils;
import com.gykj.rollcall.utils.MapUrlUtils;
import com.gykj.rollcall.utils.MenuUtils;
import com.gykj.rollcall.utils.ParseXmlUtils;
import com.gykj.rollcall.utils.TimerUtil;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * desc   :
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2710:19
 * version: 1.0
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {


    private Fragment[] fragments;
    private int index;
    //当前fragment的index
    private int currentTabIndex;
    private Fragment noticeFragment, callFragment, borrowFragment, lossFragment, doorFragment, analyseFragment, policeFragment, settingFragment;
    private Gson gson;

    private Timer mTimer;
    private TimerTask mDateTask;
    private TimerTask mWeatherTask;
    private Handler mHandler;
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    @Override
    public void initParam() {
        gson = new Gson();
        noticeFragment = FragmentFactory.createFragment(NoticeFragment.class, true);   //通知通告
        callFragment = FragmentFactory.createFragment(CallFragment.class, true);       //人员点名
        borrowFragment = FragmentFactory.createFragment(BorrowFragment.class, true);    //物品借用
        lossFragment = FragmentFactory.createFragment(LossFragment.class, true);        //物品报废
        doorFragment = FragmentFactory.createFragment(DoorFragment.class, true);        //门禁考勤
        analyseFragment = FragmentFactory.createFragment(AnalyseFragment.class, true); // 点名统计分析
        policeFragment = FragmentFactory.createFragment(PoliceFragment.class,true);   //报警记录
        settingFragment = FragmentFactory.createFragment(SettingFragment.class, true); //设置
        fragments = new Fragment[]{noticeFragment, callFragment, borrowFragment, lossFragment, doorFragment, analyseFragment,policeFragment,settingFragment};
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, noticeFragment)
             /*   .add(R.id.main_container, callFragment)
                .hide(callFragment).show(noticeFragment)*/
                .commitAllowingStateLoss();

        RollCallApplication.getInstance().connectRabbitMq("123456",this); //收到消息

        //MQ初始化
        RabbiMqEngine.getRabbiMqEngine().sendMessage(true,0,0,0); //发送消息初始化


        //初始化点名规则的数据库
        //定时器
        TimerUtil.getInstance().StartTimer(this, new CallTimerBack() {
            @Override
            public void Success(RealmResults<CallRealm> realms) {
                    for (int i = 0 ; i < realms.size() ; i ++)
                    {
                         CallRealm callRealm  = realms.get(i);
                        if(callRealm.getIsruning()!=1 && callRealm.getStatus() == 1 )//如果不是正在点名的规则  或是没有被禁用规则
                        {
                            viewModel.initdata(callRealm);
                        }else if(callRealm.getIsruning() == 1) //如果是正在点名
                        {
                            long newtime    = System.currentTimeMillis()/1000;
                            long getendtime = DateUtil.getTimeStamp("yyyy年MM月dd HH时mm分",callRealm.getEnddate()+" "+callRealm.getEndtime())/1000;

                            if( newtime >= getendtime )//表示此次点名结束      结束此次点名
                            {
                                viewModel.endCall(callRealm.getRollCallRuleId(),callRealm.getRollCallId());
                            }
                        }
                    }
            }

            @Override
            public void Error(String msg) {

            }
        });
    }
    @Override
    public void initData() {
        initTimer();
        initLocation();
        showDrawerDate();
        showDrawerWeather();
        showDrawerLocation();
    }

    private void initTimer() {
        mHandler = new Handler();
        mTimer = new Timer();
        mDateTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showDrawerDate();
                    }
                });
            }
        };
        mWeatherTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        initLocation();
                    }
                });
            }
        };
        mTimer.schedule(mDateTask,0,30*1000);
        mTimer.schedule(mWeatherTask,0,60*60*1000);
    }

    private void initLocation() {
        LocationUtils.getCurrentLocation(this, new LocationUtils.LocationCallBack() {
            @Override
            public void onSuccess(Location location) {
                KLog.d("lanzhu","精度="+location.getLongitude());
                KLog.d("lanzhu","纬度="+location.getLatitude());
                HttpUtils.sendOkHttpRequest(MapUrlUtils.getMapUrl(String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude())), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        KLog.d("lanzhu","获取城市失败="+e.getMessage());
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final CityInfo info = gson.fromJson(response.body().string(), CityInfo.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RCManager.getInstance().saveCityInfo(info);
                                KLog.d("lanzhu","info="+info.getResult().getAddressComponent().getCity());
                                showDrawerLocation();
                            }
                        });


                        HttpUtils.sendOkHttpRequest(MapUrlUtils.getWeatherUrl(info.getResult().getAddressComponent().getCity()), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                KLog.d("lanzhu","获取天气失败="+e.getMessage());
                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                WeatherEntity weather = gson.fromJson(response.body().string(), WeatherEntity.class);
                                RCManager.getInstance().saveWeatherInfo(weather);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showDrawerWeather();
                                    }
                                });
                            }
                        });
                    }
                });
            }
            @Override
            public void onFail(String msg) {
                KLog.d("lanzhu","错误="+msg);
            }
        });
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.showDrawer.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                binding.mainDrawerLayout.openDrawer(Gravity.START);
            }
        });
        viewModel.uc.clickPosition.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                index = viewModel.index;
                if (currentTabIndex != index) {
                    FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                    trx.hide(fragments[currentTabIndex]);
                    if (!fragments[index].isAdded()) {
                        trx.add(R.id.main_container, fragments[index]);
                    }
                    trx.show(fragments[index]).commit();
                }
                currentTabIndex = index;
                binding.mainTitleTv.setText(MenuUtils.getMenuList(Utils.getContext()).get(index).getMenu_title());
                binding.mainDrawerLayout.closeDrawer(Gravity.START);
            }
        });
    }

    public void showDrawerDate(){
        binding.mainTimeTv.setText(DateUtil.getDateNow("HH:mm"));
        binding.mainWeekTv.setText(DateUtil.getWeek());
        StringBuilder builder = new StringBuilder();
        builder.append(DateUtil.getDateNow("yyyy"))
                .append("年")
                .append(DateUtil.getDateNow("MM"))
                .append("月")
                .append(DateUtil.getDateNow("dd"))
                .append("日");
        binding.mainDateTv.setText(builder.toString());
        int[] lunar = LunarCalendar.solarToLunar(Integer.valueOf(DateUtil.getDateNow("yyyy")),
                Integer.valueOf(DateUtil.getDateNow("MM")),
                Integer.valueOf(DateUtil.getDateNow("dd")));
        binding.mainCalendarTv.setText(LunarCalendar.LUNAR_MONTH_INFO[(lunar[1]-1)]+LunarCalendar.LAUNAR_MONTH_DAY_INFO[(lunar[2]-1)]);
    }

    private void showDrawerWeather(){
        if(null != RCManager.getInstance().getWeatherEntity()){
            WeatherEntity entity = RCManager.getInstance().getWeatherEntity();

            if(entity.getData()!= null)
            {       
                if(entity.getData().getWendu()!=null)
                {
                    binding.mainTempTv.setText(entity.getData().getWendu()+"℃");
                    binding.mainHighTempTv.setText(entity.getData().getForecast().get(DateUtil.getWeekNumber()).getLow()+"~"+
                            entity.getData().getForecast().get(DateUtil.getWeekNumber()).getHigh());
                    binding.mainWeatherTv.setText(entity.getData().getForecast().get(DateUtil.getWeekNumber()).getType());
                }
            }

        }
    }

    private void showDrawerLocation(){
        CityInfo info = RCManager.getInstance().getCityInfo();
        if(null != info){
            binding.mainAddressTv.setText(info.getResult().getAddressComponent().getCity());
        }
    }

    @Override
    protected void onDestroy() {
        if(null != mWeatherTask){
            mWeatherTask.cancel();
        }
        if(null != mDateTask){
            mDateTask.cancel();
        }
        if(null != mTimer){
            mTimer.cancel();
        }
        KLog.d("lanzhu","定时器取消了");
        RabbiMqEngine.getRabbiMqEngine().destoryConnect();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
                if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                    finish();
                } else {
                    TOUCH_TIME = System.currentTimeMillis();
                    ToastUtils.showShort("再按一次退出!");
                }
        }
    }
}
