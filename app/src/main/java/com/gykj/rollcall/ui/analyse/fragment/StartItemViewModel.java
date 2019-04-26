package com.gykj.rollcall.ui.analyse.fragment;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.ItemViewModel;
import com.gykj.rollcall.entity.StartRecordBean;

/**
 * name : HXS
 * e-mail : 1363826037@qq.com
 * descript:类
 * date   : 2019/3/1510:46
 * version: 1.0
 */
public class StartItemViewModel extends ItemViewModel<StartViewModel> {


    public ObservableField<StartRecordBean> beans = new ObservableField<>();

    public StartItemViewModel(@NonNull StartViewModel viewModel,StartRecordBean bean) {
        super(viewModel);
        this.beans.set(bean);
    }
}
