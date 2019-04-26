package com.gykj.rollcall.ui.analyse.activity;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.ItemViewModel;
import com.gykj.rollcall.entity.RecordItemBean;

/**
 * name : HXS
 * e-mail : 1363826037@qq.com
 * descript:类
 * date   : 2019/3/149:45
 * version: 1.0
 */
public class RecordItemViewModel extends ItemViewModel<RecordDetailViewModel> {
    //record_item_tv.


    public ObservableField<RecordItemBean> ecity = new ObservableField<>();
    public RecordItemViewModel(@NonNull RecordDetailViewModel viewModel, RecordItemBean itemBean) {
        super(viewModel);
        this.ecity.set(itemBean);
    }
}
