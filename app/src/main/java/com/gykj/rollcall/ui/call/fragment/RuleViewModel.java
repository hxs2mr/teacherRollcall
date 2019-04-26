package com.gykj.rollcall.ui.call.fragment;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.mvvmlibrary.binding.command.BindingConsumer;
import com.gykj.mvvmlibrary.entity.BaseEntity;
import com.gykj.mvvmlibrary.entity.callrealm.CallRealmManager;
import com.gykj.mvvmlibrary.entity.callrealm.IAddRealmListener;
import com.gykj.mvvmlibrary.utils.RxUtils;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.adapter.RuleAdapter;
import com.gykj.rollcall.adapter.SingleRuleWeekAdapter;
import com.gykj.rollcall.entity.RoomEntity;
import com.gykj.rollcall.entity.WeekEntity;
import com.gykj.rollcall.model.DormitoryBean;
import com.gykj.rollcall.model.api.RollCallApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * desc   : 定时点名ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2814:32
 * version: 1.0
 */
public class RuleViewModel extends BaseViewModel {

    //RecyclerView多布局写法
    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    public  SingleRuleWeekAdapter   weekAdapter = new SingleRuleWeekAdapter(R.layout.layout_rule_week_item);

    public  RuleAdapter adapter = new RuleAdapter(R.layout.layout_rule_room_item);

    public RuleViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField obdate= new ObservableField("");
    public ObservableField obtime = new ObservableField("");

    //全选绑定
    public BindingCommand<Boolean> onCheckedChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean isChecked) {
            for (int i = 0;i<adapter.getData().size();i++){
                adapter.getData().get(i).setCheck(isChecked);
            }
            adapter.notifyDataSetChanged();
        }
    });

    //全选绑定
    public BindingCommand<Boolean> onWeekCheckedChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean isChecked) {
            for (int i = 0;i<weekAdapter.getData().size();i++){
                weekAdapter.getData().get(i).setCheck(isChecked);
            }
            weekAdapter.notifyDataSetChanged();
        }
    });
    /**
     * 请求网络
     */
    public void requestNetwork(){

        Disposable disposable = RollCallApi.getInstance().dormitory()
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<List<DormitoryBean>>>() {
                    @Override
                    public void accept(BaseEntity<List<DormitoryBean>> dormitoryBeanBaseEntity) throws Exception {
                        //获取成功
                        adapter.setNewData(dormitoryBeanBaseEntity.getData());
                    }
                },getErrorConsumer());

        addDisposable(disposable);

        List<WeekEntity> list1 = new ArrayList<>();
        for(int i = 0;i<7;i++){
            WeekEntity entity = new WeekEntity();
            entity.setId(i);
            entity.setWeekName("星期"+(i+1));
            entity.setCheck(true);
            list1.add(entity);
        }
        weekAdapter.setNewData(list1);
    }
    /***
     * 日期选择
     */
    public  BindingCommand onclickDate = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            uc.showdate.set(!uc.showdate.get());
        }
    });

    /**
     * 时间段选择
     */

    public  BindingCommand onclickTime = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.showtime.set(!uc.showtime.get());
        }
    })  ;

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //日期选择
        public ObservableBoolean showdate = new ObservableBoolean(false);

        public ObservableBoolean showtime = new ObservableBoolean(false);

    }

    /**
     * 取消点名
     */
    public BindingCommand cancelclicklinster = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    /**
     * 确定发起点名
     */
    public BindingCommand okclicklinster = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //int rolltype,String startTime,String endTime,
            //                              String singleStartDate,String singleEndDate,String weekList,String dormitoryList)
            String  date = (String) obdate.get();
            String  time = (String) obtime.get();
            if(TextUtils.isEmpty(date))
            {
                ToastUtils.showShort("请选择点名日期!");
                return;
            }
            if(TextUtils.isEmpty(time))
            {
                ToastUtils.showShort("请选取点名时间!");
                return;
            }
            if(adapter.getData().size()==0)
            {
                ToastUtils.showShort("暂无宿舍可选!");
                return;
            }
            String[]  time_agg =time.split("--");

            //筛选出勾选的宿舍
            String dormitorylist = "";
            for ( int i = 0 ; i < adapter.getData().size(); i++)
            {

                if(adapter.getData().get(i).isCheck())
                {
                    dormitorylist = dormitorylist+adapter.getData().get(i).getId()+",";
                }
            }
            //筛选出勾选的星期
            String weeklist="";

            for ( int j = 0 ; j < weekAdapter.getData().size(); j++)
            {

                if(weekAdapter.getData().get(j).isCheck())
                {
                    weeklist = weeklist+weekAdapter.getData().get(j).getId()+",";
                }
            }

            if(dormitorylist.equals(""))
            {
                ToastUtils.showShort("请选择需要点名的宿舍!");
                return;
            }
            if(weeklist.equals(""))
            {
                ToastUtils.showShort("请选择星期!");
                return;
            }
            weeklist = weeklist.substring(0,weeklist.length()-1);
            dormitorylist = dormitorylist.substring(0,dormitorylist.length()-1);

            System.out.println("星期："+weeklist+"       寝室号："+dormitorylist);
            rollcallrule(0,time_agg[0],time_agg[1],date,date,weeklist,dormitorylist);
        }
    });
    /**
     * 发起点名请求
     */
    public void rollcallrule(int rolltype,String startTime,String endTime,
                             String singleStartDate,String singleEndDate,String weekList,String dormitoryList)
    {
        Disposable disposable = RollCallApi.getInstance().rollcallrule(rolltype,startTime,endTime,singleStartDate,singleEndDate,weekList,dormitoryList)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<Integer>>() {
                    @Override
                    public void accept(BaseEntity<Integer> indexId) throws Exception {
                        ToastUtils.showShort("发起点名成功!");
                        //进行入库保存点名规则
                        //需要数据库保存新的点名规则
                        CallRealmManager.getInstance().addCallRealm(indexId.getData(), 1, singleStartDate, singleEndDate, startTime, endTime, rolltype, weekList, new IAddRealmListener() {
                            @Override
                            public void success() {

                            }

                            @Override
                            public void error(Throwable erroe) {
                                Log.d("HXS","添加点名规则失败!");
                            }
                        });
                        finish();

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
