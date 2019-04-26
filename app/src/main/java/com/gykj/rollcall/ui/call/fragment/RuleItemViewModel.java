package com.gykj.rollcall.ui.call.fragment;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.ItemViewModel;
import com.gykj.rollcall.entity.RoomEntity;

/**
 * desc   : 定点点名ItemViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2816:10
 * version: 1.0
 */
public class RuleItemViewModel extends ItemViewModel<RuleViewModel> {


    public ObservableField<RoomEntity> entity = new ObservableField<>();

    public RuleItemViewModel(@NonNull RuleViewModel viewModel, RoomEntity entity) {
        super(viewModel);
        this.entity.set(entity);
    }
}
