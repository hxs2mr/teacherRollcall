package com.gykj.rollcall.adapter;

import com.gykj.mvvmlibrary.base.BaseQAdapter;
import com.gykj.mvvmlibrary.base.MVViewHolder;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.LayoutRuleWeekItemBinding;
import com.gykj.rollcall.databinding.LayoutSingleRoomItemBinding;
import com.gykj.rollcall.entity.RoomEntity;
import com.gykj.rollcall.entity.WeekEntity;
import com.gykj.rollcall.model.DormitoryBean;

/**
 * Data on :2019/4/4 0004
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public class SingleRuleWeekAdapter extends BaseQAdapter<WeekEntity, LayoutRuleWeekItemBinding, MVViewHolder<LayoutRuleWeekItemBinding>> {
    public SingleRuleWeekAdapter(int layoutResId) {
        super(layoutResId);
    }
    @Override
    protected void convert(MVViewHolder<LayoutRuleWeekItemBinding> helper, WeekEntity item) {
            //databinding直接加载
        helper.addOnClickListener(R.id.week_check);
        helper.getDataViewBinding().setViewModel(item);
        helper.setText(R.id.tv_week,item.getWeekName());
        helper.setChecked(R.id.week_check,item.isCheck());
    }
}
