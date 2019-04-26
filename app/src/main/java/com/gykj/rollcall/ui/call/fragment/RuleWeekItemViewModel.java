package com.gykj.rollcall.ui.call.fragment;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.ItemViewModel;
import com.gykj.rollcall.entity.WeekEntity;

/**
 * desc   : 定点点名星期ItemViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/299:22
 * version: 1.0
 */
public class RuleWeekItemViewModel extends ItemViewModel<RuleViewModel> {

    public ObservableField<WeekEntity> entity = new ObservableField<>();

    public RuleWeekItemViewModel(@NonNull RuleViewModel viewModel,WeekEntity entity) {
        super(viewModel);
        this.entity.set(entity);
    }
}
