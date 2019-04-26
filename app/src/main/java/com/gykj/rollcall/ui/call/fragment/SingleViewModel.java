package com.gykj.rollcall.ui.call.fragment;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.mvvmlibrary.binding.command.BindingConsumer;
import com.gykj.mvvmlibrary.bus.Messenger;
import com.gykj.mvvmlibrary.entity.BaseEntity;
import com.gykj.mvvmlibrary.entity.callrealm.CallRealmManager;
import com.gykj.mvvmlibrary.entity.callrealm.IAddRealmListener;
import com.gykj.mvvmlibrary.utils.RxUtils;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.R;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.adapter.SingleAdapter;
import com.gykj.rollcall.entity.RoomEntity;
import com.gykj.rollcall.model.DormitoryBean;
import com.gykj.rollcall.model.RollPageBean;
import com.gykj.rollcall.model.api.RollCallApi;
import com.gykj.rollcall.ui.loss.LossViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import retrofit2.http.PUT;

/**
 * desc   : 单次点名ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2814:32
 * version: 1.0
 */
public class SingleViewModel extends BaseViewModel {
    public SingleViewModel(@NonNull Application application) {
        super(application);
    }

    //给RecyclerView添加ObservableList
    //public ObservableList<SingleItemViewModel> observableList = new ObservableArrayList<>();
/*    //给RecyclerView添加ItemBinding
    public ItemBinding<SingleItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.layout_single_room_item);
    //RecyclerView多布局写法
    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    public final BindingRecyclerViewAdapter<SingleItemViewModel> adapter = new BindingRecyclerViewAdapter<>();*/

     public SingleAdapter adapter =  new SingleAdapter(R.layout.layout_single_room_item);

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

    /**
     * 确定发起点名
     */
    public BindingCommand okclicklinster = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //int rolltype,String startTime,String endTime,
            //                             String singleStartDate,String singleEndDate,String weekList,String dormitoryList)
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
            String dormitoryList = "";
            for ( int i = 0 ; i < adapter.getData().size(); i++)
            {
                    if(adapter.getData().get(i).isCheck())
                    {
                        dormitoryList = dormitoryList+",";
                        dormitoryList = dormitoryList+adapter.getData().get(i).getId();
                    }
            }
            Log.d("HXS:",dormitoryList);
            if(dormitoryList.equals(""))
            {
                ToastUtils.showShort("请选择需要点名的宿舍!");
                return;
            }
            dormitoryList = dormitoryList.substring(1,dormitoryList.length());
            Log.d("HXS1:",dormitoryList);
            rollcallrule(0,time_agg[0],time_agg[1],date,date,"",dormitoryList);
        }
    });


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
     * 获取寝室编号
     */
    public void dormitory()
    {
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
    }
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
                    public void accept(BaseEntity<Integer> stringBaseEntity) throws Exception {
                        System.out.println("返回的值是:"+stringBaseEntity.getData());
                        ToastUtils.showShort("发起点名规则成功!");
                        /*
                        * int rollCallRuleId, int status, String startdate, String enddate,
                             String starttime, String endtime, int rollType, String weekList, IAddRealmListener listener)
                        * */
                        //需要数据库保存新的点名规则
                        CallRealmManager.getInstance().addCallRealm(stringBaseEntity.getData(), 1, singleStartDate, singleEndDate, startTime, endTime, rolltype, weekList, new IAddRealmListener() {
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

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //日期选择
        public ObservableBoolean showdate = new ObservableBoolean(false);

        public ObservableBoolean showtime = new ObservableBoolean(false);

    }

    @Override
    public void onDestroy() {
        if(isAttached()){
            cancel();
        }
        super.onDestroy();
    }
}
