package com.gykj.rollcall.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.R;
import com.gykj.rollcall.utils.MonthUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * name : HXS
 * e-mail : 1363826037@qq.com
 * descript:点名时间选择
 * date   : 2019/3/1214:19
 * version: 1.0
 */
public class SelectRuleTimeDialog extends BottomBaseDialog<SelectRuleTimeDialog> {
    private WheelView wl_year;
    private WheelView wl_month;
    private WheelView wl_day;
    private WheelView wl_endmonth;
    private WheelView wl_endday;

    private AppCompatTextView tv_ok;
    private AppCompatTextView tv_cancel;

    private TimeCallback mTimeCallback;
    private String m, d,em,ed;
    private Calendar yserdate = Calendar.getInstance();
    private int mFlage;
    public SelectRuleTimeDialog(Context context, int flage, TimeCallback timeCallback) {
        super(context);
        mFlage = flage;
        mTimeCallback = timeCallback;
    }
    @Override
    public View onCreateView() {
        View inflate = View.inflate(mContext, R.layout.layout_ruletime_dialog, null);
        wl_year = inflate.findViewById(R.id.wl_year);
        wl_month = inflate.findViewById(R.id.wl_month);
        wl_day = inflate.findViewById(R.id.wl_day);
        wl_endmonth = inflate.findViewById(R.id.wl_endmonth);
        wl_endday = inflate.findViewById(R.id.wl_endday);
        tv_ok = inflate.findViewById(R.id.tv_ok);
        tv_cancel = inflate.findViewById(R.id.tv_cancel);
        return  inflate;
    }

    @Override
    public void setUiBeforShow() {
        wl_year.setCyclic(false);
        wl_month.setCyclic(false);
        wl_day.setCyclic(false);
        wl_endmonth.setCyclic(false);
        wl_endday.setCyclic(false);

        final List<String> YearItems = new ArrayList<>();
        int year = yserdate.get(Calendar.YEAR);
        int month = yserdate.get(Calendar.MONTH);
        int day = yserdate.get(Calendar.DAY_OF_MONTH);
        YearItems.add(year+"年");

        final List<String> monthitems = new ArrayList<>();

        for (int i  = month;i <= 12 ; i++)
        {
            if(i<10)
            {
                monthitems.add("0"+i+"月");
            }else {
                monthitems.add(i+"月");
            }
        }

        final List<String> dayitems = new ArrayList<>();

        for ( int day_i = 1 ; day_i <=MonthUtil.getMonthOfDay(year,month);day_i++)
        {
            if(day_i<10)
            {
                dayitems.add("0"+day_i+"日");
            }else {
                dayitems.add(day_i+"日");
            }
        }

        wl_year.setAdapter(new ArrayWheelAdapter(YearItems));

        wl_month.setAdapter(new ArrayWheelAdapter(monthitems));
        wl_endmonth.setAdapter(new ArrayWheelAdapter(monthitems));

        wl_day.setAdapter(new ArrayWheelAdapter(dayitems));
        wl_endday.setAdapter(new ArrayWheelAdapter(dayitems));

        //选择其实的时
        wl_month.setCurrentItem(0);
        wl_endmonth.setCurrentItem(0);
        //选择开始分
        wl_day.setCurrentItem(day-1);
        wl_endday.setCurrentItem(day-1);

        m = monthitems.get(0);
        d = dayitems.get(day-1);
        em = monthitems.get(0);
        ed = dayitems.get(day-1);


        wl_month.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                m = monthitems.get(index);
                final List<String> dayitems = new ArrayList<>();

                for ( int day_i = day ; day_i <=MonthUtil.getMonthOfDay(year,index+1);day_i++)
                {
                    if(day_i<10)
                    {
                        dayitems.add("0"+day_i+"日");
                    }else {
                        dayitems.add(day_i+"日");
                    }
                }

                wl_day.setAdapter(new ArrayWheelAdapter(dayitems));
                wl_day.setCurrentItem(day-1);
            }
        });

        //选择结束的时
        wl_endmonth.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                em = monthitems.get(index);
                final List<String> dayitems = new ArrayList<>();

                for ( int day_i = day ; day_i <=MonthUtil.getMonthOfDay(year,index+1);day_i++)
                {
                    if(day_i<10)
                    {
                        dayitems.add("0"+day_i+"日");
                    }else {
                        dayitems.add(day_i+"日");
                    }
                }
                wl_endday.setAdapter(new ArrayWheelAdapter(dayitems));
                wl_endday.setCurrentItem(day-1);
            }
        });

//选择结束的时
        wl_day.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                d = dayitems.get(index);
            }
        });

        wl_endday.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {

                ed = dayitems.get(index);
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

                String result1 = m.replace("月", "");
                String result2 = em.replace("月", "");
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
                        ToastUtils.showShort("起始日期和结束日期不合法!");
                        return;
                    }
                if(Integer.parseInt(result1)==Integer.parseInt(result2))
                {
                    String resultm1 = d.replace("日", "");
                    String resultm2 = ed.replace("日", "");
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
                        ToastUtils.showShort("日期不合法!");
                        return;
                    }
                }

                 if(mFlage ==0 )//0属于单次点名
                {
                    mTimeCallback.single(m,d,em,ed);
                }else {  //1：属于多次点名
                     mTimeCallback.rule(m,d,em,ed);
                }
                dismiss();
            }
        });
    }
}
