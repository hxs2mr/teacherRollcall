package com.gykj.rollcall.ui.door;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.ItemViewModel;
import com.gykj.rollcall.entity.DoorEntity;

/**
 * desc   : 门禁考勤ItemViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/169:38
 * version: 1.0
 */
public class DoorItemViewModel extends ItemViewModel<DoorViewModel> {

    public ObservableField<DoorEntity> entity = new ObservableField<>();


    public DoorItemViewModel(@NonNull DoorViewModel viewModel,DoorEntity entity) {
        super(viewModel);
        this.entity.set(entity);
    }
}
