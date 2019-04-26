package com.gykj.rollcall.ui.call;

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
import android.widget.CompoundButton;
import android.widget.Switch;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.gykj.mvvmlibrary.base.BaseFragment;
import com.gykj.mvvmlibrary.entity.callrealm.CallRealmManager;
import com.gykj.mvvmlibrary.entity.callrealm.IAddRealmListener;
import com.gykj.mvvmlibrary.utils.Contract;
import com.gykj.mvvmlibrary.utils.DateUtil;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.adapter.CallAdapter;
import com.gykj.rollcall.adapter.NoticAdapter;
import com.gykj.rollcall.databinding.FragmentCallBinding;
import com.gykj.rollcall.databinding.FragmentNoticeBinding;
import com.gykj.rollcall.ui.notice.NoticeViewModel;
import com.gykj.rollcall.ui.notice.ReleaseActivity;
import com.gykj.rollcall.utils.TimerUtil;

/**
 * desc   : 人员点名Fragment
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2711:23
 * version: 1.0
 */
public class CallFragment extends BaseFragment<FragmentCallBinding,CallViewModel> implements BaseQuickAdapter.OnItemChildClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private int pageindex= 1;
    private int pagetotal= 0;
    private boolean isRefreshing = true;//当前是刷新还是加载
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_call;
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
        //获取规则点名列表
        viewModel.rollpage(pageindex, Contract.PAGE_SIZE,isRefreshing,getContext());
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
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        viewModel.adapter = (CallAdapter) adapter;
        Switch mswch = (Switch) viewModel.adapter.getViewByPosition(binding.recycleview,position,R.id.call_switch);
        switch (view.getId())
        {
            case R.id.ev_edit://编辑通知
      /*          Bundle bundle = new Bundle();
                bundle.putInt("FLAGE",1);//0表示  发布 1表示编辑
                bundle.putInt("Rooltype",viewModel.adapter.getData().get(position).getRollType());//0表示  发布 1表示编辑
                bundle.putSerializable("bean",viewModel.adapter.getData().get(position));//0表示  发布 1表示编辑
                startActivity(ReleaseCallActivity.class,bundle) ;*/

                viewModel.changeroll(0,2,"14:30","15:00","2019-04-04","2019-04-04","","1,2");
                break;
            case R.id.tv_delete://删除通知
                show_bind("是否删除该条点名?",viewModel.adapter.getData().get(position).getId(),position);
                break;
            case R.id.call_switch:
                int status = 0 ;
                long getendtime = DateUtil.getTimeStamp("yyyy年MM月dd HH时mm分",viewModel.adapter.getData().get(position).getSingleEndDate()+" "+viewModel.adapter.getData().get(position).getEndTime())/1000;
                long newtime = DateUtil.getCurrentTimeStamp()/1000;
                if(mswch.isChecked()){
                    //说明该条点名规则已经超过时间   不能开启此点名规则
                    if(newtime>=getendtime)
                    {
                        show_bind("该条点名已过期 是否删去?",viewModel.adapter.getData().get(position).getId(),position);
                        mswch.setChecked(false);
                        return;
                    }else {
                        Log.d("HXS","开");
                        status = 1;
                        viewModel.changestatus(viewModel.adapter.getData().get(position).getId(),1,viewModel.adapter.getData().get(position).getSingleStartDate(),
                                viewModel.adapter.getData().get(position).getStartTime(), getendtime);
                     }

                }else {
                    status = 0 ;
                    viewModel.changestatus(viewModel.adapter.getData().get(position).getId(),0,"","",0l);
                    Log.d("HXS","关");
                }
                viewModel.changeRealmStatus(viewModel.adapter.getData().get(position).getId(),status);
                break;
        }

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
                         viewModel.deleteroll(id,postion);
                         dialog.dismiss();
                    }
                });
    }

    @Override
    public void onRefresh() {
        pageindex = 1;
        isRefreshing = true;
        viewModel.rollpage(pageindex,Contract.PAGE_SIZE,isRefreshing,getContext());
    }

    @Override
    public void onLoadMoreRequested() {
        pageindex++;
        if (pageindex > pagetotal) {
            viewModel.adapter.loadMoreEnd();
            return;
        } else {
            isRefreshing = false;
            viewModel.rollpage(pageindex,Contract.PAGE_SIZE,isRefreshing,getContext());
        }
    }
}
