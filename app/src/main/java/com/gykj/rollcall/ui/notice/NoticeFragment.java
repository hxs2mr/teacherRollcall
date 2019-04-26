package com.gykj.rollcall.ui.notice;

import android.databinding.Observable;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.gykj.mvvmlibrary.base.BaseFragment;
import com.gykj.mvvmlibrary.entity.Config;
import com.gykj.mvvmlibrary.utils.Contract;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.adapter.NoticAdapter;
import com.gykj.rollcall.databinding.FragmentNoticeBinding;

import io.reactivex.disposables.Disposable;
import me.tatarka.bindingcollectionadapter2.BindingListViewAdapter;

/**
 * desc   : 通知通告Fragment
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2711:23
 * version: 1.0
 */
public class NoticeFragment extends BaseFragment<FragmentNoticeBinding,NoticeViewModel> implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private int pageindex= 1;
    private int pagetotal= 0;
    private boolean isRefreshing = true;//当前是刷新还是加载

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_notice;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    @Override
    public void initData() {
        pageindex = 1;
        isRefreshing = true;
        binding.refreshlayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_orange_dark, android.R.color.holo_red_dark);
        viewModel.adapter.setOnLoadMoreListener(this,binding.recycleview);
        viewModel.adapter.setOnItemChildClickListener(this);
        viewModel.adapter.setOnItemClickListener(this);
        binding.refreshlayout.setOnRefreshListener(this);//刷新效果
        binding.refreshlayout.setRefreshing(true);//刷新效果
        viewModel.requestnotice(pageindex, Contract.PAGE_SIZE,isRefreshing,getContext());
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

    private void show_bind(String title,int id,int postion)
    {
        final NormalDialog dialog = new NormalDialog(getContext());
        dialog.isTitleShow(false)//
                .bgColor(Color.parseColor("#ffffff"))//
                .cornerRadius(5)//
                .content(title)//
                .contentTextSize(18)
                .contentGravity(Gravity.CENTER)//
                .contentTextColor(Color.parseColor("#333333"))//
                .dividerColor(Color.parseColor("#222222"))//
                .btnTextSize(16.5f, 16.5f)//
                .btnText("取消","确定")
                .btnTextColor(Color.parseColor("#cdcdcd"), Color.parseColor("#707070"))//
                .widthScale(0.3f)//
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        viewModel.deletnotice(id,postion);
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        viewModel.adapter = (NoticAdapter) adapter;
        switch (view.getId())
        {
            case R.id.ev_edit://编辑通知
                Bundle bundle = new Bundle();
                bundle.putInt("EditFlage",1);//EditFlage 0:表示发布通知 1：表示编辑
                bundle.putInt("id",viewModel.adapter.getData().get(position).getId());
                bundle.putString(Contract.TITLE,viewModel.adapter.getData().get(position).getNoticeTitle());
                bundle.putString(Contract.IMG,viewModel.adapter.getData().get(position).getNoticeImgUrl());
                bundle.putString(Contract.CONTENT,viewModel.adapter.getData().get(position).getNoticeDesc());
                startActivity(ReleaseActivity.class,bundle);
                break;
            case R.id.tv_delete://删除通知
                show_bind("是否删除该条通知?",viewModel.adapter.getData().get(position).getId(),position);
                break;
        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        viewModel.adapter = (NoticAdapter) adapter;
        Bundle bundle = new Bundle();
        bundle.putSerializable(Config.NOTICE_DETAIL,viewModel.adapter.getData().get(position));
        viewModel.startActivity(NoticeDetailActivity.class,bundle);
    }

    @Override
    public void onLoadMoreRequested() {
        pageindex++;
        if (pageindex > pagetotal) {
            viewModel.adapter.loadMoreEnd();
            return;
        } else {
            isRefreshing = false;
            viewModel.requestnotice(pageindex,Contract.PAGE_SIZE,isRefreshing,getContext());
        }
    }
    @Override
    public void onRefresh() {
        pageindex = 1;
        isRefreshing = true;
        viewModel.requestnotice(pageindex,Contract.PAGE_SIZE,isRefreshing,getContext());
    }



}
