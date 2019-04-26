package com.gykj.rollcall.ui.borrow;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.mvvmlibrary.entity.BaseEntity;
import com.gykj.mvvmlibrary.utils.RxUtils;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.BR;
import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.rollcall.R;
import com.gykj.rollcall.adapter.BorrowAdapter;
import com.gykj.rollcall.entity.BorrowEntity;
import com.gykj.rollcall.model.BorrowBean;
import com.gykj.rollcall.model.DormitoryBean;
import com.gykj.rollcall.model.RollPageBean;
import com.gykj.rollcall.model.api.RollCallApi;
import com.gykj.rollcall.ui.index.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * desc   : 物品借用ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2712:18
 * version: 1.0
 */
public class BorrowViewModel extends BaseViewModel {

    public int pageTotal;
    public BindingCommand borrowrl = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
                //点击了之后  进行弹窗
            uc.showDrawer.set(!uc.showDrawer.get());
        }
    });
    public BindingCommand  search = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.searchDrawer.set(!uc.searchDrawer.get());
        }
    });
/*
    //给RecyclerView添加ObservableList
    public ObservableList<BorrowItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public ItemBinding<BorrowItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.layout_borrow_item);*/
    //RecyclerView多布局写法
    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    public BorrowAdapter adapter = new BorrowAdapter(R.layout.layout_borrow_item);


    public BorrowViewModel(@NonNull Application application) {
        super(application);
    }
    /**
     * 请求网络
     */
    public void getBorrow(int pageindex, int pagesize,boolean isfrshing, Context context){

        Disposable disposable = RollCallApi.getInstance().borrow(pageindex,pagesize)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<BorrowBean>>() {
                    @Override
                    public void accept(BaseEntity<BorrowBean> bean) throws Exception {

                        //获取到的数据
                        pageTotal = bean.getData().getPages();//获取到的总页数
                        uc.pageTotal.set(pageTotal);

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

    /**
     * 修改物品借用
     */
    public void articleborrow(String id, String articleName,String articleNum)
    {
        Disposable disposable = RollCallApi.getInstance().changeborrow(id,articleName,articleNum)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<String>>() {
                    @Override
                    public void accept(BaseEntity<String> bean) throws Exception {

                        ToastUtils.showShort("修改成功");
                    }
                },getErrorConsumer());
        addDisposable(disposable);
    }

    /**
     * 删除某条物品借用
     */

    public  void deleteborrow(int id,int postion)
    {
        Disposable disposable = RollCallApi.getInstance().deleteborrow(id)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<String>>() {
                    @Override
                    public void accept(BaseEntity<String> bean) throws Exception {
                        adapter.remove(postion);
                    }
                },getErrorConsumer());
        addDisposable(disposable);
    }

    /**
     * 归还
     */
    public void returnborrow(int id ,int status,int postion)
    {
        Disposable disposable = RollCallApi.getInstance().returnborrow(id,status)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<String>>() {
                    @Override
                    public void accept(BaseEntity<String> bean) throws Exception {
                        TextView view = (TextView) adapter.getViewByPosition(postion,R.id.tv_status_back);
                        adapter.getData().get(postion).setStatus(status);
                        if (status==0)
                        {
                            view.setText("未归还");
                            view.setTextColor(Color.parseColor("#2ee0c5"));
                             view.setBackgroundResource(R.drawable.icon_no_deal_btn_bg);

                        }else {
                            view.setText("已归还");
                            view.setTextColor(Color.parseColor("#5298fc"));
                            view.setBackgroundResource(R.drawable.icon_deal_btn_bg);
                        }

                    }
                },getErrorConsumer());
        addDisposable(disposable);
    }


    /**
     * 搜素物品
     */
    public void searchborrow(String patten)
    {
        showDialog("搜素中...");
        Disposable disposable = RollCallApi.getInstance().searchborrow(patten)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<BorrowBean.RecordsBean>>() {
                    @Override
                    public void accept(BaseEntity<BorrowBean.RecordsBean> bean) throws Exception {
                        dismissDialog();
                        ToastUtils.showShort("搜素成功");
                       //TextView view = (TextView) adapter.getViewByPosition(postion,R.id.tv_status_back);
                    }
                },getErrorConsumer());
        addDisposable(disposable);
    }

    /**
     * 添加物品借用条数
     */
    public void addborrow(String userNum,String userName,String  userId,String dormitoryId,String articleName,String articleNum)
    {
        //(String userNum,String userName,String  userId,String dormitoryId,String articleName,String articleNum)
        showDialog("加载中...");
        Disposable disposable = RollCallApi.getInstance().addborrow(userNum,userName,userId,dormitoryId,articleName,articleNum)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<BorrowBean.RecordsBean>>() {
                    @Override
                    public void accept(BaseEntity<BorrowBean.RecordsBean> bean) throws Exception {
                        dismissDialog();
                        ToastUtils.showShort("添加成功!");
                        //TextView view = (TextView) adapter.getViewByPosition(postion,R.id.tv_status_back);
                    }
                },getErrorConsumer());
        addDisposable(disposable);
    }
    //获取寝室号
    public void getaddress(DormitoryCallBack dormitoryCallBack)
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
                        dormitoryCallBack.success(dormitoryBeanBaseEntity.getData());
                    }
                },getErrorConsumer());
        addDisposable(disposable);
    }

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //Drawer显示
        public ObservableBoolean showDrawer = new ObservableBoolean(false);

        public ObservableBoolean searchDrawer = new ObservableBoolean(false);

        public ObservableInt pageTotal = new ObservableInt(0);
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
