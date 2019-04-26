package com.gykj.rollcall.ui.call;

import android.app.Application;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.base.ItemViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.mvvmlibrary.bus.event.SingleLiveEvent;

/**
 * desc   : 发布点名TabViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2814:17
 * version: 1.0
 */
public class ReleaseCallTabViewModel extends ItemViewModel {
    public String text;
    public SingleLiveEvent<String> clickEvent = new SingleLiveEvent();

    public ReleaseCallTabViewModel(@NonNull BaseViewModel viewModel, String text) {
        super(viewModel);
        this.text = text;
    }

    public BindingCommand onItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //点击之后将逻辑转到adapter中处理
            clickEvent.setValue(text);
        }
    });
}
