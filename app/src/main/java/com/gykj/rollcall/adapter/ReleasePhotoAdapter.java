package com.gykj.rollcall.adapter;

import com.gykj.mvvmlibrary.base.BaseQAdapter;
import com.gykj.mvvmlibrary.base.MVViewHolder;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.LayoutImageBinding;

/**
 * Data on :2019/4/11 0011
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public class ReleasePhotoAdapter extends BaseQAdapter<String, LayoutImageBinding, MVViewHolder<LayoutImageBinding>> {
    public ReleasePhotoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(MVViewHolder<LayoutImageBinding> helper, String item) {
        // helper.getDataViewBinding().setViewModel(item);
        helper.addOnClickListener(R.id.tv_delete);
        helper.addOnClickListener(R.id.ev_edit);
    }
}