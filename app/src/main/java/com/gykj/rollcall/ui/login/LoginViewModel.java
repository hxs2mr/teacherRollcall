package com.gykj.rollcall.ui.login;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.mvvmlibrary.entity.BaseEntity;
import com.gykj.mvvmlibrary.entity.callrealm.CallRealm;
import com.gykj.mvvmlibrary.entity.callrealm.CallRealmManager;
import com.gykj.mvvmlibrary.entity.callrealm.IAddRealmListener;
import com.gykj.mvvmlibrary.utils.Contract;
import com.gykj.mvvmlibrary.utils.DateUtil;
import com.gykj.mvvmlibrary.utils.RxUtils;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.entity.TokenEntity;
import com.gykj.rollcall.model.api.RollCallApi;
import com.gykj.rollcall.ui.index.MainActivity;
import com.gykj.rollcall.utils.SharedPreferencedUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * desc   : 登录ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2412:29
 * version: 1.0
 */
public class LoginViewModel extends BaseViewModel {

    //用户名的绑定
    public ObservableField<String> userName = new ObservableField<>("admin");
    //密码的绑定
    public ObservableField<String> password = new ObservableField<>("123456");

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    //登录按钮的点击事件
    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            login();
        }
    });
    /**
     * 网络模拟一个登陆操作
     **/
    private void login(){
        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }
        showDialog("努力加载中...");
      //  startActivity(MainActivity.class);
       // startActivity(MainActivity.class);
        Disposable subscribe = RollCallApi.getInstance().login(userName.get(), password.get())
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TokenEntity>() {
                    @Override
                        public void accept(TokenEntity tokenEntityBaseEntity) throws Exception {
                                    dismissDialog();
                                    //进入DemoActivity页面
                                    SharedPreferencedUtils.setStr(Contract.USER_ID,tokenEntityBaseEntity.getUser_id()+"");
                                    Log.d("HXS","获取到的数据:"+tokenEntityBaseEntity.getUser_id());
                                    //改变header中的 Authorization
                                    Contract.Authorization = "Bearer "+tokenEntityBaseEntity.getAccess_token();
                                    System.out.println("HXS:"+Contract.Authorization);
                                    articleborrow(123456, Contract.Authorization);
                        }
                },getErrorConsumer());
        addDisposable(subscribe);

    }

    private void articleborrow(int id,String header)
    {
        Disposable subscribe =RollCallApi.getInstance().articleborrow(id,header)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<Integer>>() {
                    @Override
                    public void accept(BaseEntity<Integer> bindid) throws Exception {
                        System.out.println("***bindid :**"+bindid.getData().intValue());
                        SharedPreferencedUtils.setStr(Contract.buildingId,bindid.getData().intValue()+"");
                        startActivity(MainActivity.class);
                        finish();
                    }
                }, getErrorConsumer());
        addDisposable(subscribe);
    }

    @Override
    public void onDestroy() {
        if(isAttached()){
            cancel();
        }
        super.onDestroy();
    }

}
