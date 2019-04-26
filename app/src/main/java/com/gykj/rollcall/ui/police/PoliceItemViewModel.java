package com.gykj.rollcall.ui.police;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.ItemViewModel;

/**
 * Data on :2019/3/28 0028
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :item_lyout对应的某一条数据
 */
public class PoliceItemViewModel extends ItemViewModel<PoliceViewModel> {

    public ObservableField<String> field =  new ObservableField<>();
    public PoliceItemViewModel(@NonNull PoliceViewModel viewModel,String data) {
        super(viewModel);
        this.field.set(data);
    }
}
