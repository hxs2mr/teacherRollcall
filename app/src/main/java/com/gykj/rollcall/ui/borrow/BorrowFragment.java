package com.gykj.rollcall.ui.borrow;

import android.databinding.Observable;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.base.BaseDialog;
import com.gykj.mvvmlibrary.base.BaseFragment;
import com.gykj.mvvmlibrary.utils.Contract;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.adapter.BorrowAdapter;
import com.gykj.rollcall.adapter.CallAdapter;
import com.gykj.rollcall.databinding.FragmentBorrowBinding;
import com.gykj.rollcall.databinding.FragmentNoticeBinding;
import com.gykj.rollcall.model.DormitoryBean;
import com.gykj.rollcall.ui.notice.NoticeViewModel;
import com.gykj.rollcall.widget.BorrrowRlDialog;

import java.util.List;

/**
 * desc   : 物品借用Fragment
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2711:23
 * version: 1.0
 */
public class BorrowFragment extends BaseFragment<FragmentBorrowBinding,BorrowViewModel> implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener, SwipeRefreshLayout.OnRefreshListener {

    private BorrrowRlDialog borrrowRlDialog;
    private int pageindex= 1;
    private int pagetotal= 0;
    private boolean isRefreshing = true;//当前是刷新还是加载
    private String patten;
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_borrow;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        borrrowRlDialog = new BorrrowRlDialog(getContext(),this,-1);
        pageindex = 1;
        isRefreshing = true;
        binding.refreshlayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_orange_dark, android.R.color.holo_red_dark);
        viewModel.adapter.setOnLoadMoreListener(this,binding.recycleview);
        viewModel.adapter.setOnItemChildClickListener(this);
        binding.refreshlayout.setOnRefreshListener(this);//刷新效果
        //获取规则点名列表
        viewModel.getBorrow(pageindex, Contract.PAGE_SIZE,isRefreshing,getContext());
        binding.refreshlayout.setRefreshing(true);//刷新效果


        binding.evSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    if (!TextUtils.isEmpty( binding.evSearch.getText().toString()))
                    {
                        viewModel.searchborrow( binding.evSearch.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void initViewObservable() {
        /**
         * 显示dialog
         */
        viewModel.uc.showDrawer.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                borrrowRlDialog = new BorrrowRlDialog(getContext(),BorrowFragment.this,-1);
                borrrowRlDialog.show();
            }
        });

        viewModel.uc.searchDrawer.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (!TextUtils.isEmpty( binding.evSearch.getText().toString()))
                {
                    viewModel.searchborrow( binding.evSearch.getText().toString());
                } else {
                    ToastUtils.showShort("请输入搜索条件!");
                }
            }
        });

        viewModel.uc.pageTotal.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                pagetotal =  viewModel.uc.pageTotal.get();//获取到数据的总页数
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
        viewModel.adapter = (BorrowAdapter) adapter;
        switch (view.getId())
        {

            case R.id.tv_return: //归还
                String title =  viewModel.adapter.getData().get(position).getStatus()==1? "未归还":"归还";
                int status   = viewModel.adapter.getData().get(position).getStatus()==0?1:0;
                show_return("是否修改该条物品借用为:"+title+"?",viewModel.adapter.getData().get(position).getId(),status,position);
                break;
            case R.id.tv_delete: //删除
                show_bind("是否删除该条借用记录?",viewModel.adapter.getData().get(position).getId(),position);
                break;
            case R.id.tv_edit:  //编辑
                borrrowRlDialog = new BorrrowRlDialog(getContext(),this,  viewModel.adapter.getData().get(position).getId());
                borrrowRlDialog.show();
                    borrrowRlDialog.edit( viewModel.adapter.getData().get(position).getUserName(),
                            viewModel.adapter.getData().get(position).getUserNum(),
                            viewModel.adapter.getData().get(position).getDormitoryId()+"宿舍",
                            viewModel.adapter.getData().get(position).getArticleName()+"/"+viewModel.adapter.getData().get(position).getArticleNum()
                            );
                break;
        }
    }

    /**
     * 修改物品是否归还
     */
    private void show_return(String title,int id,int status,int postion)
    {

        final NormalDialog dialog = new NormalDialog(getContext());
        dialog.isTitleShow(false)//
                .bgColor(Color.parseColor("#ffffff"))//
                .cornerRadius(5)//
                .title("修改")
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
                        viewModel.returnborrow(id,status,postion);
                        dialog.dismiss();
                    }
                });
    }
    private void show_bind(String title,int id,int postion)
    {
        final NormalDialog dialog = new NormalDialog(getContext());
        dialog.isTitleShow(false)//
                .bgColor(Color.parseColor("#ffffff"))//
                .cornerRadius(5)//
                .title("删除")
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
                        viewModel.deleteborrow(id,postion);
                        dialog.dismiss();
                    }
                });
    }

    //添加借用登记
    //String userNum,String userName,String  userId,String dormitoryId,String articleName,String articleNum)
    public void addborrow(String usernum,String userName,String  userId,String dormitoryId,String articleName,String articleNum)
    {
        viewModel.addborrow(usernum,userName,userId,dormitoryId,articleName,articleNum);
    }
    //修改物品借用
    public void articleborrow(String id,String articleName,String articleNum)
    {
        viewModel.articleborrow(id,articleName,articleNum);
    }

    //获取所有寝室号
    public  void getaddress(DormitoryCallBack dormitoryCallBack)
    {
         viewModel.getaddress(dormitoryCallBack);
    }

    @Override
    public void onLoadMoreRequested() {
        pageindex++;
        if (pageindex > pagetotal) {
            viewModel.adapter.loadMoreEnd();
            return;
        } else {
            isRefreshing = false;
            viewModel.getBorrow(pageindex,Contract.PAGE_SIZE,isRefreshing,getContext());
        }
    }

    @Override
    public void onRefresh() {
        pageindex = 1;
        isRefreshing = true;
        viewModel.getBorrow(pageindex,Contract.PAGE_SIZE,isRefreshing,getContext());
    }
}
