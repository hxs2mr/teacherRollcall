package com.gykj.rollcall.ui.borrow;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.ItemViewModel;
import com.gykj.rollcall.entity.BorrowEntity;

/**
 * desc   : 物品借用ItemViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/49:45
 * version: 1.0
 */
public class BorrowItemViewModel extends ItemViewModel<BorrowViewModel> {


    public ObservableField<BorrowEntity> entity = new ObservableField<>();

    public BorrowItemViewModel(@NonNull BorrowViewModel viewModel,BorrowEntity entity) {
        super(viewModel);
        this.entity.set(entity);
    }
}
