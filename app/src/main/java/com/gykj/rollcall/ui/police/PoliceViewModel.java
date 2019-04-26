package com.gykj.rollcall.ui.police;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.view.View;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.entity.BaseEntity;
import com.gykj.mvvmlibrary.utils.RxUtils;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.adapter.PoliceAdapter;
import com.gykj.rollcall.model.LossBean;
import com.gykj.rollcall.model.PoliceBean;
import com.gykj.rollcall.model.WarringBean;
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

/**
 * Data on :2019/3/28 0028
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public class PoliceViewModel extends BaseViewModel {

    public PoliceViewModel(@NonNull Application application) {
        super(application);
    }

    public int pageTotal;
    public PoliceAdapter adapter = new PoliceAdapter(R.layout.layout_police_item);
    /**
     * 网络请求
     */
    public void warrpage(int pageindex, int pagesize,boolean isfrshing, Context context)
    {
            //(String userNum,String userName,String  userId,String dormitoryId,String articleName,String articleNum)
            Disposable disposable = RollCallApi.getInstance().warrpage(pageindex,pagesize)
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BaseEntity<PoliceBean>>() {
                        @Override
                        public void accept(BaseEntity<PoliceBean> bean) throws Exception {
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
