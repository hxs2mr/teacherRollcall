package com.gykj.rollcall.ui.analyse.fragment;

import android.databinding.Observable;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.data.PieEntry;
import com.gykj.mvvmlibrary.base.BaseCancleFragment;
import com.gykj.mvvmlibrary.base.BaseFragment;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.FragmentStartBinding;
import com.gykj.rollcall.entity.StartRecordBean;
import com.gykj.rollcall.model.RollCallIdBean;
import com.gykj.rollcall.utils.PieChartManager;

import java.util.ArrayList;
import java.util.List;

/**
 * desc   : 进行中 Fragment
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/229:30
 * version: 1.0
 */
public class StartFragment extends BaseCancleFragment<FragmentStartBinding,StartViewModel> implements SwipeRefreshLayout.OnRefreshListener{
    private int pageindex= 1;
    private int pagetotal= 0;
    private boolean isRefreshing = true;//当前是刷新还是加载

    //饼状图
    private PieChartManager pieChartManager;
    private boolean isReady = false;
    private boolean isLoaded = false;//防止重复加载
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_start;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        isReady = true;
    }

    @Override
    public void initViewObservable() {

        viewModel.uc.isRefresh.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {

            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

                binding.refreshlayout.setRefreshing(false);

            }
        });

    }
    @Override
    public void onRefresh() {
        isRefreshing = true ;
        viewModel.rollCallIdPage(0,getContext());
    }
    /**
     * 防止预加载
     */
    @Override
    protected void lazyLoad() {
        if (!isReady || !isVisible || isLoaded) {
            return;
        }
        isLoaded = true;
        pageindex = 1;
        isRefreshing = true;
        binding.refreshlayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_orange_dark, android.R.color.holo_red_dark);
        binding.refreshlayout.setOnRefreshListener(this);//刷新效果
        binding.recyclerView.setHasFixedSize(true);
        binding.refreshlayout.setRefreshing(true);//刷新效果
        viewModel.rollCallIdPage(0,getContext());
    }
}
