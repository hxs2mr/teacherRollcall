package com.gykj.rollcall.adapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gykj.mvvmlibrary.base.BaseQAdapter;
import com.gykj.mvvmlibrary.base.MVViewHolder;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.FragmentNoticeBinding;
import com.gykj.rollcall.databinding.LayoutNoticeItemBinding;
import com.gykj.rollcall.entity.MainEntity;
import com.gykj.rollcall.model.NoticeBean;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Data on :2019/4/1 0001
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :设置adapter点击监听事件
 */
public class NoticAdapter extends BaseQAdapter<NoticeBean.RecordsBean, LayoutNoticeItemBinding, MVViewHolder<LayoutNoticeItemBinding>> {
    public NoticAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(MVViewHolder<LayoutNoticeItemBinding> helper, NoticeBean.RecordsBean item) {
        //databinding直接加载
        helper.getDataViewBinding().setViewModel(item);
        helper.addOnClickListener(R.id.tv_delete);
        helper.addOnClickListener(R.id.ev_edit);
    }

}
