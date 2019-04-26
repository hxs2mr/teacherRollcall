package com.gykj.rollcall.adapter;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.gykj.mvvmlibrary.base.BaseQAdapter;
import com.gykj.mvvmlibrary.base.MVViewHolder;
import com.gykj.mvvmlibrary.utils.DateUtil;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.LayoutCallItemBinding;
import com.gykj.rollcall.databinding.LayoutItemStartcallBinding;
import com.gykj.rollcall.databinding.LayoutSingleRoomItemBinding;
import com.gykj.rollcall.databinding.LayoutStartingItemBinding;
import com.gykj.rollcall.entity.StartRecordBean;
import com.gykj.rollcall.model.DormitoryBean;
import com.gykj.rollcall.utils.PieChartManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Data on :2019/4/4 0004
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public class CallIngAdapter extends BaseQAdapter<StartRecordBean, LayoutItemStartcallBinding, MVViewHolder<LayoutItemStartcallBinding>> {
     private  CountDownTimer mCountDownTimer;
    public CallIngAdapter(int layoutResId)   {
        super(layoutResId);
    }

    @Override
    protected void convert(MVViewHolder<LayoutItemStartcallBinding> helper, StartRecordBean item) {

        Log.d("HXS","第几个:"+helper.getAdapterPosition()+"     "+helper.getLayoutPosition());

        TextView tv_time=  helper.getView(R.id.start_tv_time);
        TextView tv_date=  helper.getView(R.id.tv_date);
       // long getstarttime= DateUtil.getTimeStamp("yyyy年MM月dd HH时mm分",item.getRollCallRule().getSingleStartDate()+" "+item.getRollCallRule().getStartTime());
        tv_date.setText("点名时间:"+item.getRollCallRule().getSingleStartDate()+" "+item.getRollCallRule().getStartTime()+"~"+item.getRollCallRule().getSingleEndDate()+" "+item.getRollCallRule().getEndTime());
        long getendtime = DateUtil.getTimeStamp("yyyy年MM月dd HH时mm分",item.getRollCallRule().getSingleEndDate()+" "+item.getRollCallRule().getEndTime());
        long newtime = System.currentTimeMillis();

        if(mCountDownTimer!=null)
        {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = new CountDownTimer(getendtime-newtime,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String str = "剩余" + (millisUntilFinished / 1000) + "秒";
                long   time=millisUntilFinished/1000;//总秒数
                int s= (int) (time%60);//秒
                int m= (int) (time/60);//分
                int h=(int) (time/3600);//时
                tv_time.setText( String.format(Locale.CHINA,"%02d时%02d分%02d秒",h,m,s));
            }
            @Override
            public void onFinish() {
               Log.d("HXS","结束点名第几个:"+helper.getAdapterPosition());
               tv_time.setText("点名结束");
            }
        };
        mCountDownTimer.start();
        helper.getDataViewBinding().setViewModel(item);

        PieChart   pieChart = helper.getView(R.id.end_total_piechart);
        //饼状图
        PieChartManager pieChartManager   = new PieChartManager(pieChart);

        List<PieEntry> yvals = new ArrayList<>();

        yvals.add(new PieEntry(item.getRollCall().getSignNum(), "已到"));

        yvals.add(new PieEntry(item.getRollCall().getUnsignNum(), "未到"));

        //设置每份的颜色
        List<Integer> colors = new ArrayList<>();

        colors.add(Color.parseColor("#5298fc"));

        colors.add(Color.parseColor("#2ee0c5"));

        int total =    item.getRollCall().getSignNum()+item.getRollCall().getUnsignNum();
        pieChartManager.showRingPieChartCenter(yvals,colors,"总人数:"+total);

        RecyclerView recyclerViewComment = helper.getView(R.id.start_recycleview_ing);

        List<StartRecordBean.DormitoryListBean> comments = item.getDormitoryList();

        if (comments != null && comments.size() > 0){

            CallIngItemAdapter  adapter = new CallIngItemAdapter(R.layout.layout_starting_item,1);

            adapter.setEnableLoadMore(false);

            adapter.setNewData(comments);

            recyclerViewComment.setAdapter(adapter);

        }else {
            CallIngItemAdapter  adapter1 = new CallIngItemAdapter(R.layout.layout_item_startcall, 1);

            adapter1.setEnableLoadMore(false);

            recyclerViewComment.setAdapter(adapter1);
        }

    }
}
