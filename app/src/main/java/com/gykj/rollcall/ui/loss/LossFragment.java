package com.gykj.rollcall.ui.loss;

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
import com.gykj.mvvmlibrary.utils.Contract;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.adapter.LossAdapter;
import com.gykj.rollcall.databinding.FragmentLossBinding;

import static com.gykj.mvvmlibrary.entity.Config.LOSS_DETAIL;

/**
 * desc   : 物品报损Fragment
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2711:23
 * version: 1.0
 */
public class LossFragment extends BaseFragment<FragmentLossBinding,LossViewModel> implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener {
    private int pageindex= 1;
    private int pagetotal= 0;
    private boolean isRefreshing = true;//当前是刷新还是加载
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_loss;
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
        binding.refreshlayout.setOnRefreshListener(this);//刷新效果
        binding.refreshlayout.setRefreshing(true);//刷新效果
        //获取规则点名列表
        viewModel.getloss(pageindex, Contract.PAGE_SIZE,isRefreshing,getContext());
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
    public void onLoadMoreRequested() {
        pageindex++;
        if (pageindex > pagetotal) {
            viewModel.adapter.loadMoreEnd();
            return;
        } else {
            isRefreshing = false;
            viewModel.getloss(pageindex,Contract.PAGE_SIZE,isRefreshing,getContext());
        }
    }

    @Override
    public void onRefresh() {
        pageindex = 1;
        isRefreshing = true;
        binding.refreshlayout.setRefreshing(true);
        viewModel.getloss(pageindex,Contract.PAGE_SIZE,isRefreshing,getContext());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        viewModel.adapter= (LossAdapter) adapter;

    switch (view.getId())
    {
        case R.id.releate_detail://报损详情
            Bundle bundle = new Bundle();
            bundle.putSerializable(LOSS_DETAIL,viewModel.adapter.getData().get(position));
            viewModel.startActivity(LossDetailActivity.class,bundle);
            break;

        case R.id.iv_change://修改报损的状态

            int status = viewModel.adapter.getData().get(position).getStatus();
            if(status==0)
            {
                show_bind("是否处理该条报损？",viewModel.adapter.getData().get(position).getId(),1,position);
            }else {
                ToastUtils.showShort("已处理!");
            }

            break;

        }

    }

    private void show_bind(String title,int id,int  status,int postion)
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
                        viewModel.reportloss(id,status,postion);
                        dialog.dismiss();
                    }
                });
    }

}
