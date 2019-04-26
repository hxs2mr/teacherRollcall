package com.gykj.rollcall.ui.index;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.mvvmlibrary.entity.BaseEntity;
import com.gykj.mvvmlibrary.entity.callrealm.CallRealm;
import com.gykj.mvvmlibrary.entity.callrealm.CallRealmManager;
import com.gykj.mvvmlibrary.entity.callrealm.IAddRealmListener;
import com.gykj.mvvmlibrary.utils.DateUtil;
import com.gykj.mvvmlibrary.utils.RxUtils;
import com.gykj.rollcall.model.StartCallBean;
import com.gykj.rollcall.model.api.RollCallApi;
import com.gykj.rollcall.ui.setting.ConverseActivity;

import java.security.PublicKey;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * desc   : 主界面ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2710:20
 * version: 1.0
 */
public class MainViewModel extends BaseViewModel {

    //选中的位置
    public int index = 0;

    //菜单按钮的点击事件
    public BindingCommand menuOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.showDrawer.set(!uc.showDrawer.get());
        }
    });

    //语音按钮的点击事件
    public BindingCommand voiceOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(ConverseActivity.class);
        }
    });

    //通知通告按钮的点击事件
    public BindingCommand noticeOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            index = 0;
            uc.clickPosition.set(!uc.clickPosition.get());
        }
    });


    //人员点名按钮的点击事件
    public BindingCommand callOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            index = 1;
            uc.clickPosition.set(!uc.clickPosition.get());
        }
    });


    //物品借用按钮的点击事件
    public BindingCommand borrowOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            index = 2;
            uc.clickPosition.set(!uc.clickPosition.get());
        }
    });


    //报损按钮的点击事件
    public BindingCommand lossOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            index = 3;
            uc.clickPosition.set(!uc.clickPosition.get());
        }
    });


    //门禁考勤按钮的点击事件
    public BindingCommand doorOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            index = 4;
            uc.clickPosition.set(!uc.clickPosition.get());
        }
    });


    //点名统计分析按钮的点击事件
    public BindingCommand analyseOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            index = 5;
            uc.clickPosition.set(!uc.clickPosition.get());
        }
    });

    //报警记录的点击事件
    public BindingCommand policeOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
             index = 6;
             uc.clickPosition.set(!uc.clickPosition.get());
        }
    });

    //设置按钮的点击事件
    public BindingCommand settingOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            index = 7;
            uc.clickPosition.set(!uc.clickPosition.get());
        }
    });

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //Drawer显示
        public ObservableBoolean showDrawer = new ObservableBoolean(false);

        public ObservableBoolean clickPosition = new ObservableBoolean(false);
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 开启服务  启动定时器   每隔一分钟轮巡一遍数据库中保存的点名规则
     */
    public void initdata(CallRealm callRealm)
    {
        long newtime = System.currentTimeMillis()/1000;
        Log.d("HXS","当前时间戳"+newtime);

        long getstarttime=DateUtil.getTimeStamp("yyyy年MM月dd HH时mm分",callRealm.getStartdate()+" "+callRealm.getStarttime())/1000;

        long getendtime = DateUtil.getTimeStamp("yyyy年MM月dd HH时mm分",callRealm.getEnddate()+" "+callRealm.getEndtime())/1000;

        Log.d("HXS","开始时间戳："+getstarttime);
        Log.d("HXS","结束时间戳："+getendtime);

        if( getstarttime <= newtime && newtime < getendtime )//满足点名的要求  发起点名
        {
            Log.d("HXS","发起点名了");
            /**以下为发起点名的接口**/
            rollCallRuleId(callRealm.getRollCallRuleId(),callRealm.getStatus());

        }else if (newtime>getendtime){
            Log.d("HXS","结束点名了！ID:"+callRealm.getRollCallRuleId());
            //如果时间超过了最大的点名时间范围侧除掉
            CallRealmManager.getInstance().deleteCallRealm(callRealm.getRollCallRuleId(), 0, new IAddRealmListener() {
                @Override
                public void success() {
                    Log.d("HXS","删除超时的点名规则成功");
                }
                @Override
                public void error(Throwable erroe) {
                    Log.d("HXS","删除超时单次点名规则失败"+erroe.getMessage());
                }
            });
        }

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
                        //发起点名之后保持RollCallId
                        CallRealmManager.getInstance().updateIsRunning(id,1,re.getData().getRollCallId());
                        Log.d("hxs","点名规则ID:"+re.getData());
                    }
                },getErrorConsumer());

        addDisposable(disposable);
    }

    /**发起点名**/
    public void endCall(int id,int callid)
    {
        Disposable disposable = RollCallApi.getInstance().endCall(callid)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<String>>() {
                    @Override
                    public void accept(BaseEntity<String> re) throws Exception {
                        //结束点名之后   修改运行的状态
                        CallRealmManager.getInstance().updateIsRunning(id,0,callid);
                        Log.d("hxs","结束点名规则:"+re.getData());
                    }
                },getErrorConsumer());

        addDisposable(disposable);
    }


    @Override
    public void onDestroy() {
        if(isAttached()){
            cancel();
        }
        super.onDestroy();
    }
}
