package com.gykj.rollcall.adapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gykj.mvvmlibrary.base.BaseQAdapter;
import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.base.MVViewHolder;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.LayoutNoticeItemBinding;
import com.gykj.rollcall.model.DormitoryBean;
import com.gykj.rollcall.model.NoticeBean;

/**
 * Data on :2019/4/1 0001
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :设置adapter点击监听事件
 */
public class DormitoryAdapter extends BaseQuickAdapter<DormitoryBean,BaseViewHolder> {
    public DormitoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DormitoryBean item) {
        helper.setText(R.id.tv_dormitory,item.getDormitoryName()+"宿");
        helper.addOnClickListener(R.id.cb_dormitory);
        CheckBox check = helper.getView(R.id.cb_dormitory);
        check.setChecked(item.isCheck());
    }


}
