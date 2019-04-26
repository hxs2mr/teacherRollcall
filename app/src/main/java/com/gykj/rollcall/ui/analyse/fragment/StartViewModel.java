package com.gykj.rollcall.ui.analyse.fragment;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.entity.BaseEntity;
import com.gykj.mvvmlibrary.utils.RxUtils;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.adapter.CallIngAdapter;
import com.gykj.rollcall.entity.StartRecordBean;
import com.gykj.rollcall.model.NoticeBean;
import com.gykj.rollcall.model.RollCallIdBean;
import com.gykj.rollcall.model.api.RollCallApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

import static com.gykj.mvvmlibrary.utils.Utils.getContext;
/**
 * desc   : 进行中ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/229:41
 * version: 1.0
 */
public class StartViewModel extends BaseViewModel {

    //正在点名
    public CallIngAdapter  adapter = new CallIngAdapter(R.layout.layout_item_startcall);
    public void   rollCallIdPage( int status,Context context)
    {
        adapter.getData().clear();
        Disposable disposable = RollCallApi.getInstance().rollCallIdPage(status)
            .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<BaseEntity<RollCallIdBean>>() {
                @Override
                public void accept(BaseEntity<RollCallIdBean> bean) throws Exception {
                        if(bean.getData().getRecords().size()>0)
                        {
                            //获取详情
                            for (int i =0 ; i< bean.getData().getRecords().size() ; i++)
                            {
                                  rollCallDetail(bean.getData().getRecords().get(i).getId());
                            }
                        }else {
                            View emptyView = View.inflate(context, R.layout.item_notarget, null);
                            adapter.setEmptyView(emptyView);
                            //通知刷新停止
                            uc.isRefresh.set(!uc.isRefresh.get());
                        }
                }
            },getErrorConsumer());
    addDisposable(disposable);
}

    /**
     * 获取某个点名的详情
     * @param id
     */
    public  void rollCallDetail( int id)
    {
        Disposable disposable = RollCallApi.getInstance().rollCallDetail(id)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<StartRecordBean>>() {
                    @Override
                    public void accept(BaseEntity<StartRecordBean> bean) throws Exception {
                        //详情的数据
                        //通知刷新停止
                        adapter.addData(bean.getData());
                        uc.isRefresh.set(!uc.isRefresh.get());
                    }
                },getErrorConsumer());
        addDisposable(disposable);
    }


    public StartViewModel(@NonNull Application application) {
        super(application);
    }

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    public class UIChangeObservable {
        //Drawer显示
        public ObservableBoolean isRefresh = new ObservableBoolean(false);

    }

    @Override
    public void onDestroy() {
        if(isAttached()){
            cancel();
        }
        super.onDestroy();
    }
}
