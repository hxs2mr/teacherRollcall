package com.gykj.rollcall.ui.loss;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.entity.BaseEntity;
import com.gykj.mvvmlibrary.utils.RxUtils;
import com.gykj.rollcall.R;
import com.gykj.rollcall.adapter.LossAdapter;
import com.gykj.rollcall.model.LossBean;
import com.gykj.rollcall.model.api.RollCallApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * desc   : 物品报损ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2712:18
 * version: 1.0
 */
public class LossViewModel extends BaseViewModel {
/*    //给RecyclerView添加ObservableList
    public ObservableList<LossItemVeiwModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public ItemBinding<LossItemVeiwModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.layout_loss_item);
    //RecyclerView多布局写法
    //给RecyclerView添加Adpter，请使
    用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    public final BindingRecyclerViewAdapter<LossItemVeiwModel> adapter = new BindingRecyclerViewAdapter<>();*/
    public int pageTotal;
    public LossAdapter adapter  = new LossAdapter(R.layout.layout_loss_item);
    public LossViewModel(@NonNull Application application) {
        super(application);
    }
    /**
     * 请求网络
         */
    public void getloss(int pageindex, int pagesize,boolean isfrshing, Context context){
            //(String userNum,String userName,String  userId,String dormitoryId,String articleName,String articleNum)
            Disposable disposable = RollCallApi.getInstance().loss(pageindex,pagesize)
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BaseEntity<LossBean>>() {
                        @Override
                        public void accept(BaseEntity<LossBean> bean) throws Exception {

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
                        }
                    },getErrorConsumer());
            addDisposable(disposable);
    }

    /**
     * 修改处理状态
     */
    public  void reportloss(int id ,int status,int postion)
    {
        //(String userNum,String userName,String  userId,String dormitoryId,String articleName,String articleNum)
        Disposable disposable = RollCallApi.getInstance().reportloss(id,status)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<LossBean>>() {
                    @Override
                    public void accept(BaseEntity<LossBean> bean) throws Exception {

                        adapter.getData().get(postion).setStatus(1);
                        adapter.notifyItemChanged(postion);
                    }
                },getErrorConsumer());
        addDisposable(disposable);
    }

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //Drawer显示
        public ObservableBoolean showDrawer = new ObservableBoolean(false);

        public ObservableBoolean clickPosition = new ObservableBoolean(false);
        public ObservableBoolean isRefresh = new ObservableBoolean(false);

        public ObservableInt changeflage = new ObservableInt(0);

    }
    @Override
    public void onDestroy() {
        if(isAttached()){
            cancel();
        }
        super.onDestroy();
    }
}
