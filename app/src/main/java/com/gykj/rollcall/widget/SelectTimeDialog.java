package com.gykj.rollcall.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.flyco.dialog.widget.base.BaseDialog;
import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.R;
import com.gykj.rollcall.adapter.DormitoryAdapter;
import com.gykj.rollcall.model.DormitoryBean;
import com.gykj.rollcall.ui.borrow.BorrowFragment;
import com.gykj.rollcall.ui.borrow.DormitoryCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * name : HXS
 * e-mail : 1363826037@qq.com
 * descript:点名时间选择
 * date   : 2019/3/1214:19
 * version: 1.0
 */
public class SelectTimeDialog extends BottomBaseDialog<SelectTimeDialog> {
    private WheelView start_h;
    private WheelView start_m;
    private WheelView end_h;
    private WheelView end_m;

    private AppCompatTextView tv_ok;
    private AppCompatTextView tv_cancel;

    private TimeCallback mTimeCallback;
    private String sh, sm,eh,em;
    private int mFlage;
    public SelectTimeDialog(Context context,int flage,TimeCallback timeCallback) {
        super(context);
        mFlage = flage;
        mTimeCallback = timeCallback;
    }
    @Override
    public View onCreateView() {
        View inflate = View.inflate(mContext, R.layout.layout_time_dialog, null);
        start_h = inflate.findViewById(R.id.start_h);
        start_m = inflate.findViewById(R.id.start_m);
        end_h = inflate.findViewById(R.id.end_h);
        end_m = inflate.findViewById(R.id.end_m);
        tv_ok = inflate.findViewById(R.id.tv_ok);

        tv_cancel = inflate.findViewById(R.id.tv_cancel);

        return  inflate;
    }

    @Override
    public void setUiBeforShow() {
        start_h.setCyclic(false);
        start_m.setCyclic(false);
        end_h.setCyclic(false);
        end_m.setCyclic(false);

        final List<String> mOptionsItems = new ArrayList<>();
        mOptionsItems.add("05时");mOptionsItems.add("06时");mOptionsItems.add("07时");mOptionsItems.add("08时");
        mOptionsItems.add("09时");mOptionsItems.add("10时");mOptionsItems.add("11时");mOptionsItems.add("12时");mOptionsItems.add("13时");
        mOptionsItems.add("14时");mOptionsItems.add("15时");mOptionsItems.add("16时");mOptionsItems.add("17时");
        mOptionsItems.add("18时");mOptionsItems.add("19时");mOptionsItems.add("20时");mOptionsItems.add("21时");
        mOptionsItems.add("22时");mOptionsItems.add("23时");mOptionsItems.add("24时");

        final List<String> opmin = new ArrayList<>();
        opmin.add("00分");
        opmin.add("05分");opmin.add("10分");opmin.add("15分");opmin.add("20分");
        opmin.add("25分");opmin.add("30分");opmin.add("35分");opmin.add("40分");opmin.add("45分");
        opmin.add("50分");opmin.add("55分");

        start_h.setAdapter(new ArrayWheelAdapter(mOptionsItems));

        start_m.setAdapter(new ArrayWheelAdapter(opmin));

        end_m.setAdapter(new ArrayWheelAdapter(opmin));

        end_h.setAdapter(new ArrayWheelAdapter(mOptionsItems));
        //选择其实的时
        start_h.setCurrentItem(0);
        end_h.setCurrentItem(1);
        //选择开始分
        start_m.setCurrentItem(0);
        end_m.setCurrentItem(0);
        sh = mOptionsItems.get(0);
        eh = mOptionsItems.get(1);
        sm = opmin.get(0);
        em = opmin.get(0);
        start_h.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                sh = mOptionsItems.get(index);
            }
        });

        //选择结束的时
        end_h.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                eh = mOptionsItems.get(index);
            }
        });




        start_m.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
            public void onItemSelected(int index) {
                    sm = opmin.get(index);
                }
            });

        //选择结束分
        end_m.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                 em = opmin.get(index);
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result1 = sh.replace("时", "");
                String result2 = eh.replace("时", "");
                int start1 =  result1.indexOf("0");
                int start2 =  result2.indexOf("0");
                 if(start1==0)
                    {
                    result1 = result1.substring(1,2);
                    }
                    if(start2==0)
                    {
                    result2 = result2.substring(1,2);
                    }

                 if(Integer.parseInt(result1)>Integer.parseInt(result2))
                    {
                        ToastUtils.showShort("起始时间和结束时间不合法!");
                        return;
                    }

                if(Integer.parseInt(result1)==Integer.parseInt(result2))
                {
                    String resultm1 = sm.replace("分", "");
                    String resultm2 = em.replace("分", "");
                    int startm1 =  resultm1.indexOf("0");
                    int startm2 =  resultm2.indexOf("0");
                    if(startm1==0)
                    {
                        resultm1 = resultm1.substring(1,2);
                    }
                    if(startm2==0)
                    {
                        resultm2 = resultm2.substring(1,2);
                    }

                    if(Integer.parseInt(resultm1)>=Integer.parseInt(resultm2))
                    {
                        ToastUtils.showShort("分钟时间不合法!");
                        return;
                    }
                }

                 if(mFlage ==0 )//0属于单次点名
                {
                    mTimeCallback.single(sh,sm,eh,em);
                }else {  //1：属于多次点名
                     mTimeCallback.rule(sh,sm,eh,em);
                }
                dismiss();
            }
        });
    }
}
