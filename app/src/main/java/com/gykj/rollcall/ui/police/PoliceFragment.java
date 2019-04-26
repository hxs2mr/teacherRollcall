package com.gykj.rollcall.ui.police;

import android.databinding.Observable;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gykj.mvvmlibrary.base.BaseFragment;
import com.gykj.mvvmlibrary.utils.Contract;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.FragmentPoliceBinding;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Data on :2019/3/28 0028
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :报警记录
 */
public class PoliceFragment extends BaseFragment<FragmentPoliceBinding,PoliceViewModel> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private int pageindex= 1;
    private int pagetotal= 0;
    private boolean isRefreshing = true;//当前是刷新还是加载
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_police;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    //初始化数据
    @Override
    public void initData() {
        pageindex = 1;
        isRefreshing = true;
        binding.refreshlayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_orange_dark, android.R.color.holo_red_dark);
        viewModel.adapter.setOnLoadMoreListener(this,binding.recycleview);

        binding.recycleview.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .color(Color.parseColor("#eeeeee"))
                .sizeResId(R.dimen.divider)
                .marginResId(R.dimen.leftmargin, R.dimen.rightmargin)
                .build());
        //获取规则点名列表
        viewModel.warrpage(pageindex, Contract.PAGE_SIZE,isRefreshing,getContext());

        binding.refreshlayout.setOnRefreshListener(this);//刷新效果
        binding.refreshlayout.setRefreshing(true);//刷新效果

    }


    @Override
    public void initViewObservable() {
        viewModel.uc.changeflage.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                pagetotal =  viewModel.uc.changeflage.get();//获取到数据的总页数
                Log.d("HXS","当前总页数："+pagetotal);
            }
        });

        viewModel.uc.isRefresh.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                binding.refreshlayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        binding.refreshlayout.setRefreshing(true);
        pageindex = 1;
        isRefreshing = true;
        viewModel.warrpage(pageindex,Contract.PAGE_SIZE,isRefreshing,getContext());
    }

    @Override
    public void onLoadMoreRequested() {
        pageindex++;
        if (pageindex > pagetotal) {
            viewModel.adapter.loadMoreEnd();
            return;
        } else {
            isRefreshing = false;
            viewModel.warrpage(pageindex,Contract.PAGE_SIZE,isRefreshing,getContext());
        }

    }
}
