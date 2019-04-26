package com.gykj.rollcall.ui.call.fragment;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gykj.mvvmlibrary.base.BaseFragment;
import com.gykj.mvvmlibrary.binding.command.BindingConsumer;
import com.gykj.mvvmlibrary.bus.Messenger;
import com.gykj.mvvmlibrary.utils.DateUtil;
import com.gykj.rollcall.R;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.adapter.RuleAdapter;
import com.gykj.rollcall.adapter.SingleAdapter;
import com.gykj.rollcall.adapter.SingleRuleWeekAdapter;
import com.gykj.rollcall.databinding.FragmentRuleBinding;
import com.gykj.rollcall.model.RollPageBean;
import com.gykj.rollcall.widget.SelectRuleTimeDialog;
import com.gykj.rollcall.widget.SelectTimeDialog;
import com.gykj.rollcall.widget.TimeCallback;

import java.util.Date;

/**
 * desc   : 定时点名Fragment
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2814:30
 * version: 1.0
 */
public class RuleFragment extends BaseFragment<FragmentRuleBinding,RuleViewModel> {
    private SelectRuleTimeDialog dialog;

    private SelectTimeDialog selectTimeDialog;


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_rule;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.requestNetwork();
        //注册一个带数据回调的消息监听
//参数1：接受人（上下文）
//参数2：定义的token
//参数3：实体的泛型约束
//参数4：执行的回调监听
        Messenger.getDefault().register(this, "double", RollPageBean.RecordsBean.class, new BindingConsumer<RollPageBean.RecordsBean>() {
            @Override
            public void call(RollPageBean.RecordsBean recordsBean) {
                Log.d("HXS 数据",recordsBean.getId()+"");
            }
        });
        dialog = new SelectRuleTimeDialog(getContext(), 1, new TimeCallback() {
            @Override
            public void single(String start_h, String start_m, String end_h, String end_m) {
            }
            @Override
            public void rule(String start_month, String start_day, String end_month, String end_day) {
                //输入单次点名的
                viewModel.obdate.set(start_month+start_day+"--"+end_month+end_day);
            }
        });

        selectTimeDialog = new SelectTimeDialog(getContext(), 0, new TimeCallback() {
            @Override
            public void single(String start_h, String start_m, String end_h, String end_m) {
                viewModel.obtime.set(start_h+start_m+"--"+end_h+end_m);
            }
            @Override
            public void rule(String start_h, String start_m, String end_h, String end_m) {
            }
        });
        //选择寝室
        viewModel.adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter madapter, View view, int position) {
                viewModel.adapter = (RuleAdapter) madapter;
                boolean check = viewModel.adapter.getData().get(position).isCheck();
                viewModel.adapter.getData().get(position).setCheck(!check);
                viewModel.adapter.notifyItemChanged(position);

                int number=0;
                for (int i =0 ; i < viewModel.adapter.getData().size();i++)
                {
                    if(viewModel.adapter.getData().get(i).isCheck())
                    {number++;}
                }
                if(number == viewModel.adapter.getData().size())
                {
                    binding.checkallRoom.setChecked(true);
                }
            }
        });

        //选择星期
        viewModel.weekAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter madapter, View view, int position) {
                viewModel.weekAdapter = (SingleRuleWeekAdapter) madapter;
                boolean check = viewModel.weekAdapter.getData().get(position).isCheck();
                viewModel.weekAdapter.getData().get(position).setCheck(!check);
                viewModel.weekAdapter.notifyItemChanged(position);
                int number=0;
                for (int i =0 ; i < viewModel.weekAdapter.getData().size();i++)
                {
                    if(viewModel.weekAdapter.getData().get(i).isCheck())
                    {number++;}
                }
                if(number == viewModel.weekAdapter.getData().size())
                {
                    binding.checkallWeek.setChecked(true);
                }
            }
        });

    }
    @Override
    public void initViewObservable() {
        viewModel.uc.showdate.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                dialog.show();
            }
        });

        //时间选择
        viewModel.uc.showtime.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                selectTimeDialog.show();
            }
        });
    }
}