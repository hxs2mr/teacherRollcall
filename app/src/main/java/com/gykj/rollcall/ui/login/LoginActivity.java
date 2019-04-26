package com.gykj.rollcall.ui.login;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.gykj.mvvmlibrary.base.BaseActivity;
import com.gykj.mvvmlibrary.entity.callrealm.CallRealm;
import com.gykj.mvvmlibrary.utils.Contract;
import com.gykj.mvvmlibrary.utils.KLog;
import com.gykj.rollcall.R;
import com.gykj.rollcall.callback.CallTimerBack;
import com.gykj.rollcall.databinding.ActivityLoginBinding;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.utils.BASE64;
import com.gykj.rollcall.utils.TimerUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * desc   : 登录界面
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2412:28
 * version: 1.0
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    private Disposable subscribe;
    private Realm mRealm;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }
    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public LoginViewModel initViewModel() {
        return ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @Override
    public void initData() {
        Contract.Authorization = "Basic "+ BASE64.getBASE64("test:test");
//        RealmResults<CityRealm> all = Realm.getDefaultInstance().where(CityRealm.class).findAll();
//        if(null == all || all.size() == 0){
//            try {
//                ParseXmlUtils.parseXml(getAssets().open("city.xml"));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        RxPermissions rxPermissions = new RxPermissions(this);
        subscribe = rxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            KLog.d("lanzhu", permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            KLog.d("lanzhu", permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            KLog.d("lanzhu", permission.name + " is denied.");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        KLog.d("lanzhu", "权限请求崩溃了"+throwable.getMessage());
                    }
                });

    }
    @Override
    protected void onDestroy() {
        if(null != subscribe && subscribe.isDisposed()){
            subscribe.dispose();
        }
        super.onDestroy();
    }
}
