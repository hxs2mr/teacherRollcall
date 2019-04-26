package com.gykj.rollcall.ui.call.fragment;

import android.databinding.Observable;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gykj.mvvmlibrary.base.BaseFragment;
import com.gykj.mvvmlibrary.binding.command.BindingConsumer;
import com.gykj.mvvmlibrary.bus.Messenger;
import com.gykj.mvvmlibrary.utils.DateUtil;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.R;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.adapter.SingleAdapter;
import com.gykj.rollcall.databinding.FragmentSingleBinding;
import com.gykj.rollcall.model.RollPageBean;
import com.gykj.rollcall.widget.SelectTimeDialog;
import com.gykj.rollcall.widget.TimeCallback;

import java.util.Calendar;
import java.util.Date;

import io.reactivex.functions.Consumer;

/**
 * desc   : 单次点名Fragment
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2814:30
 * version: 1.0
 */
public class SingleFragment extends BaseFragment<FragmentSingleBinding,SingleViewModel> {

   private Calendar startDate = Calendar.getInstance();
    //startDate.set(2013,1,1);
   private   Calendar endDate = Calendar.getInstance();
    //endDate.set(2020,1,1);

    private SelectTimeDialog dialog;
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_single;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        //获取全部寝室
        viewModel.dormitory();
        //

        //注册一个带数据回调的消息监听
//参数1：接受人（上下文）
//参数2：定义的token
//参数3：实体的泛型约束
//参数4：执行的回调监听
        Messenger.getDefault().register(this, "sign", RollPageBean.RecordsBean.class, new BindingConsumer<RollPageBean.RecordsBean>() {
            @Override
            public void call(RollPageBean.RecordsBean recordsBean) {
                Log.d("HXS 数据",recordsBean.getId()+"");
            }
        });
        //起始时间
        //正确设置方式 原因：注意事项有说明
        startDate.set(startDate.get(Calendar.YEAR),startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));
        endDate.set(endDate.get(Calendar.YEAR),endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH)+1);
        dialog = new SelectTimeDialog(getContext(), 0, new TimeCallback() {
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
                viewModel.adapter = (SingleAdapter) madapter;

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
                    binding.checkboxAll.setChecked(true);
                }
            }
        });
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.showdate.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
//时间选择器
                TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                            viewModel.obdate.set(DateUtil.getDateTime(date,"yyyy年MM月dd"));
                       //   Toast.makeText(MainActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                       // new ArrayWheelAdapter<>()
                    }
                })
                .setRangDate(startDate,endDate).build();//起始终止年月日设定.build();
                pvTime.show();
            }
        });

        //时间选择
        viewModel.uc.showtime.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                dialog.show();
            }
        });
    }

}
