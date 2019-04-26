package com.gykj.rollcall.adapter;

import android.support.annotation.Nullable;
import android.widget.Switch;

import com.gykj.mvvmlibrary.base.BaseQAdapter;
import com.gykj.mvvmlibrary.base.MVViewHolder;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.LayoutCallItemBinding;
import com.gykj.rollcall.databinding.LayoutSingleRoomItemBinding;
import com.gykj.rollcall.model.DormitoryBean;
import com.gykj.rollcall.model.RollPageBean;

import java.util.List;

/**
 * Data on :2019/4/4 0004
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :点名规则适配器
 */
public class CallAdapter extends BaseQAdapter<RollPageBean.RecordsBean, LayoutCallItemBinding, MVViewHolder<LayoutCallItemBinding>> {
    public CallAdapter(int layoutResId) {
        super(layoutResId);
    }
    @Override
    protected void convert(MVViewHolder<LayoutCallItemBinding> helper, RollPageBean.RecordsBean item) {
        helper.getDataViewBinding().setViewModel(item);

        Switch view  = helper.getView(R.id.call_switch);
        helper.addOnClickListener(R.id.tv_delete);
        helper.addOnClickListener(R.id.ev_edit);
        helper.addOnClickListener(R.id.call_switch);
        if(item.getStatus()==0)
        {
            view.setChecked(false);
        }else {
            view.setChecked(true);
        }
    }
}
