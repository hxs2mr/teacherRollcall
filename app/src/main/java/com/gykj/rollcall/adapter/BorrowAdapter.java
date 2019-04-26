package com.gykj.rollcall.adapter;

import com.gykj.mvvmlibrary.base.BaseQAdapter;
import com.gykj.mvvmlibrary.base.MVViewHolder;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.LayoutBorrowItemBinding;
import com.gykj.rollcall.databinding.LayoutCallItemBinding;
import com.gykj.rollcall.model.BorrowBean;
import com.gykj.rollcall.model.RollPageBean;

/**
 * Data on :2019/4/4 0004
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :物品借用适配器
 */
public class BorrowAdapter extends BaseQAdapter<BorrowBean.RecordsBean, LayoutBorrowItemBinding, MVViewHolder<LayoutBorrowItemBinding>> {
    public BorrowAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(MVViewHolder<LayoutBorrowItemBinding> helper, BorrowBean.RecordsBean item) {
        helper.getDataViewBinding().setViewModel(item);
        helper.addOnClickListener(R.id.tv_delete);
        helper.addOnClickListener(R.id.tv_edit);
        helper.addOnClickListener(R.id.tv_return);
    }
}
