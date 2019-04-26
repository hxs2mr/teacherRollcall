package com.gykj.rollcall.adapter;

import com.gykj.mvvmlibrary.base.BaseQAdapter;
import com.gykj.mvvmlibrary.base.MVViewHolder;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.LayoutLossItemBinding;
import com.gykj.rollcall.model.LossBean;

/**
 * Data on :2019/4/4 0004
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :点名规则适配器
 */
public class LossAdapter extends BaseQAdapter<LossBean.RecordsBean, LayoutLossItemBinding, MVViewHolder<LayoutLossItemBinding>> {
    public LossAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(MVViewHolder<LayoutLossItemBinding> helper, LossBean.RecordsBean item) {
        helper.getDataViewBinding().setViewModel(item);
        helper.addOnClickListener(R.id.releate_detail);
        helper.addOnClickListener(R.id.iv_change);
    }
}
