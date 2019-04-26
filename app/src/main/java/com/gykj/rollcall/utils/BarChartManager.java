package com.gykj.rollcall.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.gykj.mvvmlibrary.utils.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * name : HXS
 * e-mail : 1363826037@qq.com
 * descript:柱状图管理类
 * date   : 2019/3/1215:54
 * version: 1.0
 */
public class BarChartManager {

    private BarChart mBarChart;
    private YAxis leftAxis;
    private  YAxis rightAxis;
    private XAxis xAxis;
    private   Activity mActivity;


     private  Typeface tfLight ;
    public  BarChartManager(BarChart barChart, Activity activity){

        this.mBarChart = barChart;
        this.mActivity = activity;
        leftAxis = mBarChart.getAxisLeft();
        rightAxis = mBarChart.getAxisRight();
        xAxis  = mBarChart.getXAxis();
        tfLight = Typeface.createFromAsset(mActivity.getAssets(), "OpenSans-Light.ttf");
    }
    /**
     * 初始化
     */

    private  void  initLineChart()
    {
        //背景颜色
         mBarChart.setBackgroundColor(Color.parseColor("#ffffff"));

        mBarChart.setPinchZoom(true);
         //网格
         mBarChart.setDrawGridBackground(false);
         //背景阴影
        mBarChart.setDrawBarShadow(false);
     //   mBarChart.setHighlightFullBarEnabled(false);
        mBarChart.setNoDataText("暂无数据!");
        //图表描述
        mBarChart.getDescription().setText("");

        //显示边界
     //   mBarChart.setDrawBorders(false);

        //设置动画效果
         mBarChart.animateY(1000,
                 Easing.EasingOption.Linear);
         mBarChart.animateX(1000,Easing.EasingOption.Linear);


         //图标文字说明
        Legend legend = mBarChart.getLegend();

        legend.setForm(Legend.LegendForm.SQUARE);//图示 标签的形状。   正方形
       // legend.setForm(Legend.LegendForm.CIRCLE);//图例窗体的形状 目前是一条线 ，还有 圆圈 方块等形状
        legend.setTextSize(11f);

        //显示的位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        ////图例左居中
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        //图例的方向为水平
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);//绘制在chart的外侧
        legend.setTypeface(tfLight);

        //XY轴的设置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴设置显示位置在底部
        xAxis.setCenterAxisLabels(true);
       // xAxis.setTextColor(Color.BLUE);//设置X轴字体颜色
        //xAxis.setTextSize(10f);  //设置X轴字体大小
        xAxis.setDrawGridLines(false); //不绘制X轴网格，默认为绘制。
        xAxis.setTypeface(tfLight);
        //y轴设置
        leftAxis.setDrawGridLines(true);
        leftAxis.setTypeface(tfLight);

        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); //保证Y轴从0开始，不然会上移一点
        rightAxis.setAxisMinimum(0f);

        rightAxis.setEnabled(false);
        //将条目放大 可滑动
        mBarChart.invalidate();
   /*     Matrix mMatrix = new Matrix();
        mMatrix.postScale(2f, 1f);  //X轴宽度放大2倍  竖直方向不变
        mBarChart.getViewPortHandler().refresh(mMatrix, mBarChart, false);
        mBarChart.animateY(800);*/
    }


    //展示柱状图单条
    public void showLineBarChart(final List<String> dateList,List<BarEntry> entryList) {
      //   initLineChart(); //首先进项基本数据的初始化
        Description description = mBarChart.getDescription();
        description.setText("");
        description.setTextSize(10f);
        mBarChart.setNoDataText("no data.");
        // 集双指缩放
        mBarChart.setPinchZoom(false);
        mBarChart.animateY(2000);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 2));
        barEntries.add(new BarEntry(2, 3));
        barEntries.add(new BarEntry(3, 1));
        barEntries.add(new BarEntry(4, 1));
        barEntries.add(new BarEntry(5, 2f));
        barEntries.add(new BarEntry(6, 3));

        BarDataSet barDataSet = new BarDataSet(barEntries, "签到率");

        BarData barData = new BarData(barDataSet);
        barData.setDrawValues(true);//是否显示柱子的数值
        barData.setValueTextSize(10f);//柱子上面标注的数值的字体大小
        barData.setBarWidth(0.8f);//每个柱子的宽度
        mBarChart.setData(barData);

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setDrawLabels(true);//是否显示x坐标的数据
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x坐标数据的位置
        xAxis.setDrawGridLines(false);//是否显示网格线中与x轴垂直的网格线
//        xAxis.setLabelCount(6, true);//设置x轴显示的标签的个数
        final List<String> xValue = new ArrayList<>();
        xValue.add("zero");//index = 0 的位置的数据在IndexAxisValueFormatter中时不显示的。
        xValue.add("one");
        xValue.add("two");
        xValue.add("three");
        xValue.add("four");
        xValue.add("five");
        xValue.add("six");
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dateList));//设置x轴标签格式化器

        YAxis rightYAxis = mBarChart.getAxisRight();
        rightYAxis.setDrawGridLines(false);
        rightYAxis.setEnabled(true);//设置右侧的y轴是否显示。包括y轴的那一条线和上面的标签都不显示
        rightYAxis.setDrawLabels(false);//设置y轴右侧的标签是否显示。只是控制y轴处的标签。控制不了那根线。
        rightYAxis.setDrawAxisLine(false);//这个方法就是专门控制坐标轴线的

        YAxis leftYAxis = mBarChart.getAxisLeft();
        leftYAxis.setEnabled(true);
        leftYAxis.setDrawLabels(true);
        leftYAxis.setDrawAxisLine(true);
        leftYAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftYAxis.setDrawGridLines(false);//只有左右y轴标签都设置不显示水平网格线，图形才不会显示网格线
        leftYAxis.setDrawLimitLinesBehindData(true);//设置网格线是在柱子的上层还是下一层（类似Photoshop的层级）
        leftYAxis.setGranularity(1f);//设置最小的间隔，防止出现重复的标签。这个得自己尝试一下就知道了。
        leftYAxis.setAxisMinimum(0);//设置左轴最小值的数值。如果IndexAxisValueFormatter自定义了字符串的话，那么就是从序号为2的字符串开始取值。
        leftYAxis.setSpaceBottom(0);//左轴的最小值默认占有10dp的高度，如果左轴最小值为0，一般会去除0的那部分高度
        //自定义左侧标签的字符串，可以是任何的字符串、中文、英文等
        leftYAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{ "0", "1", "2", "3", "4", "5"}));
    }

//多条柱状图

    public  void showBarChart(final List<String> dateList)
    {

        initLineChart();
        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();

        for (int i = 0; i < dateList.size(); i++) {
            values1.add(new BarEntry(i, (float) (Math.random() * 1000)));
            values2.add(new BarEntry(i, (float) (Math.random() * 1000)));
        }
        BarDataSet set1, set2;

        if (mBarChart.getData() != null && mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) mBarChart.getData().getDataSetByIndex(1);
            set1.setValues(values1);
            set2.setValues(values2);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values1, "已到");
            set1.setColor(Color.rgb(82, 152, 252));
            set2 = new BarDataSet(values2, "未到");
            set2.setColor(Color.rgb(46, 244, 197));

            BarData data = new BarData(set1, set2);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTypeface(tfLight);
            mBarChart.setData(data);
        }

        float groupSpace = 0.05f;
        float barSpace = 0.16f; // x4 DataSet
        float barWidth = 0.3f; // x4 DataSet
        // specify the width each bar should have
        mBarChart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        mBarChart.getXAxis().setAxisMinimum(0);
        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        mBarChart.getXAxis().setAxisMaximum(0 + mBarChart.getBarData().getGroupWidth(groupSpace, barSpace) * dateList.size());
        mBarChart.groupBars(0, groupSpace, barSpace);

        //mBarChart.invalidate();

        xAxis.setValueFormatter(new LargeValueFormatter(){
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
         //        KLog.d("yxxs","value="+value);
                if(value < 0 || value >=dateList.size()){
                    return "";
                }else {
                    return dateList.get((int) value);
                }
            }
        });

    }

}
