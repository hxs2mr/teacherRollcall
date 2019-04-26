package com.gykj.rollcall.ui.analyse.fragment;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.ItemViewModel;
import com.gykj.rollcall.entity.StartRecordBean;

/**
 * name : HXS
 * e-mail : 1363826037@qq.com
 * descript:类
 * date   : 2019/3/1510:53
 * version: 1.0
 */
public class StartItemEndViewModel extends ItemViewModel<StartViewModel> {

    public ObservableField<StartRecordBean> bean = new ObservableField<>();
    public StartItemEndViewModel(@NonNull StartViewModel viewModel,StartRecordBean bean) {
        super(viewModel);
        this.bean.set(bean);
    }
}
