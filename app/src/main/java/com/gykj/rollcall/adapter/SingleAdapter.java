package com.gykj.rollcall.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gykj.mvvmlibrary.base.BaseQAdapter;
import com.gykj.mvvmlibrary.base.MVViewHolder;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.LayoutNoticeItemBinding;
import com.gykj.rollcall.databinding.LayoutSingleRoomItemBinding;
import com.gykj.rollcall.model.DormitoryBean;

import java.util.List;

/**
 * Data on :2019/4/4 0004
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public class SingleAdapter extends BaseQAdapter<DormitoryBean, LayoutSingleRoomItemBinding, MVViewHolder<LayoutSingleRoomItemBinding>> {
    public SingleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(MVViewHolder<LayoutSingleRoomItemBinding> helper, DormitoryBean item) {
            //databinding直接加载
        helper.addOnClickListener(R.id.checkbox);
        helper.getDataViewBinding().setViewModel(item);
        helper.setText(R.id.single_total_tv,item.getDormitoryName()+"寝室");
        helper.setChecked(R.id.checkbox,item.isCheck());
    }
}
