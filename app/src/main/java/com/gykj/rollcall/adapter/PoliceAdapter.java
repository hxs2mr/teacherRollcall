package com.gykj.rollcall.adapter;

import com.gykj.mvvmlibrary.base.BaseQAdapter;
import com.gykj.mvvmlibrary.base.MVViewHolder;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.LayoutLossItemBinding;
import com.gykj.rollcall.databinding.LayoutPoliceItemBinding;
import com.gykj.rollcall.model.LossBean;
import com.gykj.rollcall.model.PoliceBean;
import com.gykj.rollcall.model.WarringBean;

/**
 * Data on :2019/4/4 0004
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :点名规则适配器
 */
public class PoliceAdapter extends BaseQAdapter<PoliceBean.RecordsBean, LayoutPoliceItemBinding, MVViewHolder<LayoutPoliceItemBinding>> {
    public PoliceAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(MVViewHolder<LayoutPoliceItemBinding> helper,PoliceBean.RecordsBean item) {
        helper.getDataViewBinding().setViewModel(item);
        helper.addOnClickListener(R.id.releate_detail);
        helper.addOnClickListener(R.id.iv_change);
    }
}
