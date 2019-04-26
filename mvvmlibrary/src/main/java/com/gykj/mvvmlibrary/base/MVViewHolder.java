package com.gykj.mvvmlibrary.base;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


/**
 * Data on :2019/4/1 0001
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :使用Databing+BaseReyclviewHelper
 */
public class MVViewHolder<T extends ViewDataBinding >extends BaseViewHolder {

    T binding= null;

    public MVViewHolder(T binding) {
        super(binding.getRoot());
        binding.getRoot().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.binding = binding;
    }

    public BaseViewHolder setQAdapter(BaseQuickAdapter adapter)
    {
       super.setAdapter(adapter);
       return  this;
    }
    public T getDataViewBinding()
    {
        return  this.binding;
    }
}
