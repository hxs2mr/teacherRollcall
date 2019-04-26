package com.gykj.rollcall.ui.call;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.mvvmlibrary.entity.BaseEntity;
import com.gykj.mvvmlibrary.entity.callrealm.CallRealm;
import com.gykj.mvvmlibrary.entity.callrealm.CallRealmManager;
import com.gykj.mvvmlibrary.entity.callrealm.IAddRealmListener;
import com.gykj.mvvmlibrary.utils.DateUtil;
import com.gykj.mvvmlibrary.utils.RxUtils;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.R;
import com.gykj.rollcall.adapter.CallAdapter;
import com.gykj.rollcall.model.RollPageBean;
import com.gykj.rollcall.model.StartCallBean;
import com.gykj.rollcall.model.api.RollCallApi;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * desc   : 人员点名ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2712:18
 * version: 1.0c
 */
public class CallViewModel extends BaseViewModel {
    public int pageTotal;
    private Realm mRealm;
    //发布点名点击绑定
    public BindingCommand releaseOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();

            bundle.putInt("FLAGE",0);//0表示  发布 1表示编辑
            startActivity(ReleaseCallActivity.class,bundle);
        }
    });

    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    public CallAdapter  adapter = new CallAdapter(R.layout.layout_call_item);


    public CallViewModel(@NonNull Application application) {
        super(application);
    }
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    /**
     * 获取规则点名
     */
    public void rollpage(int pageindex, int pagesize,boolean isfrshing, Context context)
    {
        Disposable disposable = RollCallApi.getInstance().rollpage1(pageindex,pagesize)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<RollPageBean>>() {
                    @Override
                    public void accept(BaseEntity<RollPageBean> bean) throws Exception {
                        //获取到的数据
                        pageTotal = bean.getData().getPages();//获取到的总页数
                        uc.changeflage.set(pageTotal);

                        if (bean.getData().getRecords() == null || bean.getData().getRecords().size() <= 0) {
                            View emptyView = View.inflate(context, R.layout.item_notarget, null);
                            adapter.setEmptyView(emptyView);
                            //通知刷新停止
                            uc.isRefresh.set(!uc.isRefresh.get());
                        }

                        if (isfrshing) {
                            adapter.getData().clear();
                            adapter.setNewData(bean.getData().getRecords());
                            //通知刷新停止
                            uc.isRefresh.set(!uc.isRefresh.get());
                        } else {
                            adapter.addData(bean.getData().getRecords());
                            adapter.loadMoreComplete();
                        }

                        /**
                         * 点名规则对比 是否入库
                         */
                         if(mRealm==null)
                         {
                            mRealm =  Realm.getDefaultInstance();
                         }
                          RealmResults<CallRealm> callRealms = mRealm.where(CallRealm.class).findAll();
                          if(callRealms == null||callRealms.size() <= 0)
                          {
                              initCallRealm(bean.getData());
                          }

                    }
                },getErrorConsumer());
        addDisposable(disposable);
    }

    //删除点名规则
    public void deleteroll(int id,int postion)
    {
        Disposable disposable = RollCallApi.getInstance().deleteroll(id)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<List<String>>>() {
                    @Override
                    public void accept(BaseEntity<List<String>> dormitoryBeanBaseEntity) throws Exception {
                        deleteCallRealm(id);//从数据库中去除该条点名规则
                        adapter.remove(postion);
                    }
                },getErrorConsumer());

        addDisposable(disposable);
    }

    /**编辑通知**/
    public void changeroll(int rolltype,int id,String startTime,String endTime,String singleStartDate,String singleEndDate,String weekList,String dormitoryList)
    {
        //int rolltype,int id,String startTime,String endTime,
        //   String singleStartDate,String singleEndDate,String weekList,String dormitoryList)
        Disposable disposable = RollCallApi.getInstance().changeroll(rolltype,id,startTime,endTime,singleStartDate,singleEndDate,weekList,dormitoryList)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<List<String>>>() {
                    @Override
                    public void accept(BaseEntity<List<String>> dormitoryBeanBaseEntity) throws Exception {

                        ToastUtils.showShort("编辑成功");
                    }
                },getErrorConsumer());

        addDisposable(disposable);
    }

    /**发起点名**/
    public void rollCallRuleId(int id,int status)
    {
        Disposable disposable = RollCallApi.getInstance().rollCallRuleId(id)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<StartCallBean>>() {
                    @Override
                    public void accept(BaseEntity<StartCallBean> re) throws Exception {
                            Log.d("hxs","点名规则ID:"+re.getData());
                    }
                },getErrorConsumer());

        addDisposable(disposable);
    }

    /**修改点名规则成功**/
    public void changestatus(int id,int status,String startdate,String starttime,  Long getendtime)
    {
        Disposable disposable = RollCallApi.getInstance().changestatusroll(id,status)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<Object>>() {
                    @Override
                    public void accept(BaseEntity<Object> re) throws Exception {
                        if(status==1)
                        {
                            Log.d("HXS","当前时间:"+startdate + starttime );
                            long newtime = DateUtil.getCurrentTimeStamp()/1000;
                            Log.d("HXS","当前时间戳"+newtime);

                            long getstarttime=DateUtil.getTimeStamp("yyyy年MM月dd HH时mm分",startdate+" "+starttime)/1000;

                            Log.d("HXS","开始时间戳："+getstarttime);
                            Log.d("HXS","结束时间戳："+getendtime);

                            if(getstarttime<=newtime && getendtime>newtime)  //可以发起点名
                            {
                                 //rollCallRuleId(id,1);//开启点名
                            }else  if(newtime>=getendtime){//该点名规则已过期

                                ToastUtils.showShort("点名已开时间到会自动发起点名!");
                            }
                        }
                        Log.d("HXS","修改状态成功!");
                    }
                },getErrorConsumer());

        addDisposable(disposable);
    }
    /**
     * 点名规则数据库
     * @param bean
     */
    private  void initCallRealm(RollPageBean bean )
    {
        if(bean.getRecords().size()>0)
        {
            for (int i = 0 ; i  < bean.getRecords().size();i++)
            {
                /*
                * int rollCallRuleId,int status,String startdate,String enddate,
                                  String starttime,String endtime,int rollType,String weekList*/
                insertCallRealm(bean.getRecords().get(i).getId(),bean.getRecords().get(i).getStatus(),
                        bean.getRecords().get(i).getSingleStartDate(),bean.getRecords().get(i).getSingleEndDate(),
                        bean.getRecords().get(i).getStartTime(),    bean.getRecords().get(i).getEndTime(),    bean.getRecords().get(i).getRollType(),
                        bean.getRecords().get(i).getWeekList()+"");
            }

        }
    }
    public void show_bind(String title,int id,int postion,Context context)
    {
        final NormalDialog dialog = new NormalDialog(context);
        dialog.isTitleShow(false)//
                .bgColor(Color.parseColor("#ffffff"))//
                .cornerRadius(5)//
                .content(title)//
                .contentTextSize(18)
                .contentGravity(Gravity.CENTER)//
                .contentTextColor(Color.parseColor("#333333"))//
                .dividerColor(Color.parseColor("#222222"))//
                .btnTextSize(16.5f, 16.5f)//
                .btnText("取消","确定")
                .btnTextColor(Color.parseColor("#cdcdcd"), Color.parseColor("#707070"))//
                .widthScale(0.3f)//
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        deleteroll(id,postion);
                        dialog.dismiss();
                    }
                });
    }


    @Override
    public void onDestroy() {
        if(isAttached()){
            cancel();
        }
        super.onDestroy();
    }
    public class UIChangeObservable {
        //Drawer显示
        public ObservableBoolean showDrawer = new ObservableBoolean(false);

        public ObservableBoolean clickPosition = new ObservableBoolean(false);
        public ObservableBoolean isRefresh = new ObservableBoolean(false);

        public ObservableInt  changeflage = new ObservableInt(0);

    }
    private void  insertCallRealm(int rollCallRuleId,int status,String startdate,String enddate,
                                  String starttime,String endtime,int rollType,String weekList) {
        Realm mRealm = Realm.getDefaultInstance();
        CallRealm faceRealm = mRealm.where(CallRealm.class).equalTo("rollCallRuleId", rollCallRuleId).findFirst();
        if (faceRealm == null)
         {
             //不存在测 添加
             CallRealmManager.getInstance().addCallRealm(rollCallRuleId, status, startdate, enddate, starttime, endtime, rollType, weekList, new IAddRealmListener() {
                 @Override
                 public void success() {

                     Log.d("HXS","添加成功");

                 }
                 @Override
                 public void error(Throwable erroe) {

                     Log.d("HXS","添加失败");

                 }
             });
         }else {//存在则更新一下
            //不存在测 添加
            CallRealmManager.getInstance().updateCallRealm(rollCallRuleId, status, startdate, enddate, starttime, endtime, rollType, weekList, new IAddRealmListener() {
                @Override
                public void success() {
                    Log.d("HXS","更新成功");
                }

                @Override
                public void error(Throwable erroe) {
                    Log.d("HXS","更新失败");
                }
            });
        }
    }

    private void deleteCallRealm(int callid)
    {
        CallRealmManager.getInstance().deleteCallRealm(callid, 0, new IAddRealmListener() {
            @Override
            public void success() {
                Log.d("HXS","删除成功");
            }

            @Override
            public void error(Throwable erroe) {
                Log.d("HXS","删除失败");
            }
        });
    }

    //修改数据库中status  用于启动点名

    public void changeRealmStatus(int id, int status)
    {
        CallRealmManager.getInstance().changeStatusCallRealm(id,status, new IAddRealmListener() {
            @Override
            public void success() {

            }

            @Override
            public void error(Throwable erroe) {

            }
        });
    }

}
