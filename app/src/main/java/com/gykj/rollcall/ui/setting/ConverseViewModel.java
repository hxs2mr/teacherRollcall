package com.gykj.rollcall.ui.setting;

import android.app.Application;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;

/**
 * desc   : 语音通话ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2713:24
 * version: 1.0
 */
public class ConverseViewModel extends BaseViewModel {

    //挂断电话绑定
    public BindingCommand hangUpOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    public ConverseViewModel(@NonNull Application application) {
        super(application);
    }
}
