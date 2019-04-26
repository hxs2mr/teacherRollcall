package com.gykj.rollcall.ui.loss;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.rollcall.entity.LossEntity;
import com.gykj.rollcall.model.LossBean;

/**
 * desc   : 报损详情ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/211:14
 * version: 1.0
 */
public class LossDetailViewModel extends BaseViewModel {

    //返回按钮点击绑定
    public BindingCommand cancleOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    public ObservableField<LossBean.RecordsBean> entity = new ObservableField<>();

    public LossDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void setLossDetail(LossBean.RecordsBean entity){
        this.entity.set(entity);
    }
}
