package com.gykj.mvvmlibrary.base;

import android.databinding.ViewDataBinding;

/**
 * Data on :2019/4/24 0024
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :去除预加载
 */
public abstract class BaseCancleFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment<V,VM>{

    /** Fragment当前状态是否可见 */
    protected boolean isVisible;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();   //等当前的Fragment可见以后再加载数据，网络请求等
        } else {
            isVisible = false;
        }
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();
}
