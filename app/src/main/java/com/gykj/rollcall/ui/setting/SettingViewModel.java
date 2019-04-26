package com.gykj.rollcall.ui.setting;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;

/**
 * desc   : 设置ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2712:18
 * version: 1.0
 */
public class SettingViewModel extends BaseViewModel {

    public   int flage=0;
    //退出登录绑定
    public BindingCommand logoutOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });

    //资料修改绑定
    public BindingCommand dataOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            flage =0 ;
            uc.showDrawer.set(!uc.showDrawer.get());
        }
    });

    //密码修改绑定
    public BindingCommand passwordOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            flage =1;
            uc.showDrawer.set(!uc.showDrawer.get());
        }
    });


    public SettingViewModel(@NonNull Application application) {
        super(application);
    }
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //Drawer显示
        public ObservableBoolean showDrawer = new ObservableBoolean(false);

        public ObservableBoolean clickPosition = new ObservableBoolean(false);
    }


}
