package com.gykj.rollcall.adapter;

import com.gykj.mvvmlibrary.base.BaseQAdapter;
import com.gykj.mvvmlibrary.base.MVViewHolder;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.LayoutRuleRoomItemBinding;
import com.gykj.rollcall.databinding.LayoutSingleRoomItemBinding;
import com.gykj.rollcall.model.DormitoryBean;

/**
 * Data on :2019/4/4 0004
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public class RuleAdapter extends BaseQAdapter<DormitoryBean, LayoutRuleRoomItemBinding, MVViewHolder<LayoutRuleRoomItemBinding>> {
    public RuleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(MVViewHolder<LayoutRuleRoomItemBinding> helper, DormitoryBean item) {
            //databinding直接加载
        helper.addOnClickListener(R.id.room_check);
        helper.getDataViewBinding().setViewModel(item);
        helper.setText(R.id.tv_room,item.getDormitoryName()+"寝室");
        helper.setChecked(R.id.room_check,item.isCheck());
    }
}
