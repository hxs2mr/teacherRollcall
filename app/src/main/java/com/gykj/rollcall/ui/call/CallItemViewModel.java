package com.gykj.rollcall.ui.call;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.ItemViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.mvvmlibrary.binding.command.BindingConsumer;
import com.gykj.mvvmlibrary.utils.KLog;
import com.gykj.rollcall.entity.CallEntity;


/**
 * desc   : 点名列表ItemViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/289:22
 * version: 1.0
 */
public class CallItemViewModel extends ItemViewModel<CallViewModel> {

    public ObservableField<CallEntity> entity = new ObservableField<>();

    //条目的修改事件
    public BindingCommand modifyClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            KLog.d("lanzhu","modifyClick");
        }
    });

    //条目的删除事件
    public BindingCommand deleteClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            KLog.d("lanzhu","deleteClick");
        }
    });

    public BindingCommand<Boolean> onCheckedChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean isChecked) {
            entity.get().setEnable(isChecked);
        }
    });

    public CallItemViewModel(@NonNull CallViewModel viewModel,CallEntity entity) {
        super(viewModel);
        this.entity.set(entity);
    }
}
