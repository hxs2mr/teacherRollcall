package com.gykj.rollcall.ui.notice;
import android.app.Application;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.mvvmlibrary.bus.RxBus;
import com.gykj.mvvmlibrary.entity.BaseEntity;
import com.gykj.mvvmlibrary.utils.Contract;
import com.gykj.mvvmlibrary.utils.RxUtils;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.adapter.NoticAdapter;
import com.gykj.rollcall.entity.MainEntity;
import com.gykj.rollcall.model.NoticeBean;
import com.gykj.rollcall.model.api.RollCallApi;
import com.gykj.rollcall.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * desc   : 主界面ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2416:02
 * version: 1.0
 */
public class NoticeViewModel extends BaseViewModel {

        //recycleview滑动到底部会触发这个时间

        private int LIMIT= 6;
        public int pageTotal;
        public ObservableBoolean isRefreshing = new ObservableBoolean(false);

     /*   public final ReplyCommand<Integer> loadMoreCommand = new ReplyCommand<>(
                (count) -> {
            *//*count 代表当前RecyclerView 有多少个Item,通过这个值我们可以
              得到当前应该去加载第几页的数据*//*
                    int page=count / LIMIT;
                  //  loadData(page)  记载第几页
                });*/
        //发布通知点击绑定
        public BindingCommand releaseOnClickCommand = new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                Bundle bundle = new Bundle();
                bundle.putInt(Contract.EditFlage,0);

               startActivity(ReleaseActivity.class);
            }
        });

        public NoticeViewModel(@NonNull Application application) {
            super(application);
        }

        //给RecyclerView添加ObservableList
     //   public ObservableList<NoticeItemViewModel> observableList = new ObservableArrayList<>();

        //给RecyclerView添加ItemBinding
       // public ItemBinding<NoticeItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.layout_notice_item);

        //RecyclerView多布局写法
        //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
        // public final BindingRecyclerViewAdapter<NoticeItemViewModel> adapter = new BindingRecyclerViewAdapter<>();

        public NoticAdapter adapter = new NoticAdapter(R.layout.layout_notice_item);
        //封装一个界面发生改变的观察者
        public UIChangeObservable uc = new UIChangeObservable();


        public void requestnotice(int pageindex, int pagesize, boolean isfrshing, Context context)
        {
            Disposable disposable = RollCallApi.getInstance().notice(pageindex,pagesize)
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BaseEntity<NoticeBean>>() {
                        @Override
                        public void accept(BaseEntity<NoticeBean> bean) throws Exception {

                            //获取到的数据
                            pageTotal = bean.getData().getPages();//获取到的总页数
                            uc.changeflage.set(pageTotal);

                            //返回的数据 第一次
                            if (bean.getData().getRecords() == null || bean.getData().getRecords().size() <= 0) {
                                    View emptyView = View.inflate(context, R.layout.item_notarget, null);
                                    adapter.setEmptyView(emptyView);
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

                        }
                    },getErrorConsumer());
            addDisposable(disposable);
        }



    /**删除某条通知*/
        public void deletnotice(int id,int postion)
        {
            Disposable disposable = RollCallApi.getInstance().delnotice(id)
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BaseEntity<String>>() {
                        @Override
                        public void accept(BaseEntity<String> result) throws Exception {
                            adapter.remove(postion);
                            //删除成功
                        }
                    },getErrorConsumer());
            addDisposable(disposable);
        }




        public class UIChangeObservable {
            //Drawer显示
            public ObservableBoolean showDrawer = new ObservableBoolean(false);

            public ObservableBoolean clickPosition = new ObservableBoolean(false);
            public ObservableBoolean isRefresh = new ObservableBoolean(false);

            public ObservableInt  changeflage = new ObservableInt(0);

        }
}
