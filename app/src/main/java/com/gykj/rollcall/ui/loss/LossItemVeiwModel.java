package com.gykj.rollcall.ui.loss;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.ItemViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.rollcall.entity.LossEntity;

import static com.gykj.mvvmlibrary.entity.Config.LOSS_DETAIL;

/**
 * desc   : 物品报损ItemVeiwModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/29:28
 * version: 1.0
 */
public class LossItemVeiwModel extends ItemViewModel<LossViewModel> {

    //查看详情点击绑定
    public BindingCommand scanOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putSerializable(LOSS_DETAIL,entity.get());
            viewModel.startActivity(LossDetailActivity.class,bundle);
        }
    });

    public ObservableField<LossEntity> entity = new ObservableField<>();

    public LossItemVeiwModel(@NonNull LossViewModel viewModel,LossEntity entity) {
        super(viewModel);
        this.entity.set(entity);
    }
}
