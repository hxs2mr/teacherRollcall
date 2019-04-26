package com.gykj.rollcall.ui.analyse.fragment;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.mvvmlibrary.entity.BaseEntity;
import com.gykj.mvvmlibrary.entity.Config;
import com.gykj.mvvmlibrary.utils.DateUtil;
import com.gykj.mvvmlibrary.utils.RxUtils;
import com.gykj.rollcall.R;
import com.gykj.rollcall.model.EndDormitorBean;
import com.gykj.rollcall.model.EndUserBean;
import com.gykj.rollcall.model.RollCallIdBean;
import com.gykj.rollcall.model.api.RollCallApi;
import com.gykj.rollcall.ui.analyse.activity.RecordDetailActivity;
import com.gykj.rollcall.ui.notice.NoticeDetailActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.gykj.rollcall.utils.LineChartManager.notifyDataSetChanged;

/**
 * desc   : 已结束ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/229:41
 * version: 1.0
 */
public class EndViewModel extends BaseViewModel {

    //用户名的绑定
    public ObservableField<String> username = new ObservableField<>("李四测试");

    //饼状图的数据
    public List<PieEntry> PieEntrys = new ArrayList<>();

    //未签到TOP10 数据
     public  List<Entry> userlineEntrys;
    public  String[] userlineName;

    //签到率
    public List<BarEntry> entryList =  new ArrayList<>();


   /* ArrayList entries = new ArrayList<>();//将数据源添加到图标
        for (int i = 0; i < dateList.size(); i++) {
        entries.add(new BarEntry(i, (float) (Math.random() * 1000)));
    }*/

    /**
     * 点击签到记录
     */
    public BindingCommand dmrecord = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString(Config.NAME,username.get());
            startActivity(RecordDetailActivity.class,bundle);
        }
    });
    public EndViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 签到记录
     */
    public void   rollCallIdPage(int status, Context context)
    {
        Disposable disposable = RollCallApi.getInstance().rollCallIdPage(status)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<RollCallIdBean>>() {
                    @Override
                    public void accept(BaseEntity<RollCallIdBean> bean) throws Exception {


                    }
                },getErrorConsumer());
        addDisposable(disposable);
    }

    /**
     * 个人未签到top10
     */
    public void   userUnSignTop()
    {
        Disposable disposable = RollCallApi.getInstance().userUnSignTop()
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<List<EndUserBean>>>() {
                    @Override
                    public void accept(BaseEntity<List<EndUserBean>> bean) throws Exception {
                        showuserlinechart(bean.getData());
                    }
                },getErrorConsumer());
        addDisposable(disposable);
    }

    private void showuserlinechart(List<EndUserBean> data) {
        userlineEntrys =new ArrayList<>();
        userlineName = new String[data.size()];
        for(int i=0;i<data.size();i++){
            userlineEntrys.add(new Entry(i, data.get(i).getNum()));
            userlineName[i] = data.get(i).getUserName();
        }
        uc.isUserLineChart.set(!uc.isUserLineChart.get());
    }


    /**
     * 宿舍未签到top10
     */
    public void   dormitoryUnSignNumTop()
    {
        Disposable disposable = RollCallApi.getInstance().dormitoryUnSignNumTop()
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<List<EndDormitorBean>>>() {
                    @Override
                    public void accept(BaseEntity<List<EndDormitorBean>> bean) throws Exception {
                        showhodlePieChart(bean.getData());
                    }
                },getErrorConsumer());
        addDisposable(disposable);
    }


    /**
     * 历史签到
     */
    public void  rollHistory(String starttime,String endtime)
    {
        Disposable disposable = RollCallApi.getInstance().rollHistory(starttime,endtime)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<Object>>() {
                    @Override
                    public void accept(BaseEntity<Object> bean) throws Exception {


                    }
                },getErrorConsumer());
        addDisposable(disposable);
    }

    /**
     * 未签到率top10
     */
    public void   unSignPrtTop()
    {
        Disposable disposable = RollCallApi.getInstance().unSignPrtTop()
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<Object>>() {
                    @Override
                    public void accept(BaseEntity<Object> bean) throws Exception {


                    }
                },getErrorConsumer());
        addDisposable(disposable);
    }

    private void showhodlePieChart(List<EndDormitorBean> data) {
        // 设置每份所占数量
        PieEntrys = new ArrayList<>();
        for (int i =0 ; i < data.size();i++)
        {
            PieEntrys.add(new PieEntry(data.get(i).getNum(), data.get(i).getDormitoryName()));
        }
        uc.isPie.set(!uc.isPie.get());
    }
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    public class UIChangeObservable {
        //Drawer显示
        public ObservableBoolean isPie= new ObservableBoolean(false);
        public ObservableBoolean isUserLineChart= new ObservableBoolean(false);
    }
    @Override
    public void onDestroy() {
        if(isAttached()){
            cancel();
        }
        super.onDestroy();
    }

}
