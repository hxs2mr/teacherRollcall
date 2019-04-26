package com.gykj.rollcall.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.TimeUtils;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.gykj.rollcall.R;

import java.util.Calendar;
import java.util.List;

/**
 * name : HXS
 * e-mail : 1363826037@qq.com
 * descript:折线图工具类
 * date   : 2019/3/1217:29
 * version: 1.0
 */
public class LineChartManager {


    private static Typeface tfLight;
    private  static  Activity mActivity;
    /**
     * 初始化图表
     *
     * @param chart 原始图表
     * @return 初始化后的图表
     */
    public static LineChart initChart(LineChart chart, Activity activity) {
        mActivity= activity;
        tfLight = Typeface.createFromAsset(mActivity.getAssets(), "OpenSans-Light.ttf");
        //背景颜色
        chart.setBackgroundColor(Color.parseColor("#ffffff"));
        chart.setBackgroundResource(R.drawable.linechart_shape);
        // 不显示数据描述
        chart.getDescription().setEnabled(false);
        // 没有数据的时候，显示“暂无数据”
        chart.setNoDataText("暂无数据");
        // 不显示表格颜色
        chart.setDrawGridBackground(false);
        // 不可以缩放
        chart.setScaleEnabled(true);
        // 不显示y轴右边的值
        chart.getAxisRight().setEnabled(false);

        //设置是否可以触摸
        chart.setTouchEnabled(true);
        chart.setDragDecelerationFrictionCoef(0.9f);
        chart.setDragEnabled(true);

    /*    Description description = new Description();
        description.setText("");
        chart.setDescription(description);*/

        // 不显示图例
        Legend legend = chart.getLegend();
        legend.setDrawInside(false);
        legend.setTypeface(tfLight);

/*
        //设置背景颜色
        mLineChart.setBackgroundColor(ColorAndImgUtils.CHART_BACKGROUND_COLOR);
        //设置一页最大显示个数为6，超出部分就滑动
        float ratio = (float) xValueList.size()/(float) 6;
        //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
        mLineChart.zoom(ratio,1f,0,0);*/
        // 向左偏移15dp，抵消y轴向右偏移的30dp
      //  chart.setExtraLeftOffset(15);

        XAxis xAxis = chart.getXAxis();
        // 不显示x轴
        xAxis.setDrawAxisLine(true);

        xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签
        // 设置x轴数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
       // xAxis.setTextSize(12);
        xAxis.setTypeface(tfLight);


        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        // 设置x轴数据偏移量
      //  xAxis.setYOffset(-12);
        YAxis yAxis = chart.getAxisLeft();
        // 不显示y轴
        yAxis.setDrawAxisLine(true);
        // 设置y轴数据的位置
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 不从y轴发出横向直线
        yAxis.setDrawGridLines(false);
        yAxis.setTypeface(tfLight);
     //   yAxis.setTextSize(12);
        // 设置y轴数据偏移量
       // yAxis.setXOffset(30);
       // yAxis.setYOffset(-30);
        yAxis.setAxisMinimum(0);
        yAxis.setSpaceTop(35f);
        //Matrix matrix = new Matrix();
        // x轴缩放1.5倍
        //matrix.postScale(1.5f, 1f);
        // 在图表动画显示之前进行缩放
        //chart.getViewPortHandler().refresh(matrix, chart, false);
        // x轴执行动画
        //chart.animateX(2000);
        chart.invalidate();
        return chart;
    }

    /**
     * 设置图表数据
     *
     * @param chart  图表
     * @param values 数据
     */
    public static void setChartData(LineChart chart, List<Entry> values) {
        LineDataSet lineDataSet;
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            lineDataSet = new LineDataSet(values, "");
            // 设置曲线颜色
            lineDataSet.setColor(Color.parseColor("#5298fc"));
            lineDataSet.setLineWidth(2);
            // 设置平滑曲线
            lineDataSet.setMode(LineDataSet.Mode.LINEAR);
            // 不显示坐标点的小圆点
            lineDataSet.setDrawCircles(true);
            // 不显示坐标点的数据
            lineDataSet.setDrawValues(true);
            // 不显示定位线
            lineDataSet.setHighlightEnabled(false);
            //设置填充
            //设置允许填充
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillAlpha(50);
            //设置背景渐变
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
                //设置渐变
                Drawable drawable = ContextCompat.getDrawable(mActivity, R.drawable.linchart_shap_gradient);
                lineDataSet.setFillDrawable(drawable);
            }else {
                lineDataSet.setFillColor(Color.parseColor("#ac5298fc"));
            }

            LineData data = new LineData(lineDataSet);
            chart.setData(data);
            chart.invalidate();
        }
    }

    /**
     * 更新图表
     *
     * @param chart     图表
     * @param values    数据
     */
    public static void notifyDataSetChanged(LineChart chart, List<Entry> values,String[] data) {
        chart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xValuesProcess(data)[(int) value];
            }
        });

        //设置多少个X上的标签数据
        chart.getXAxis().setLabelCount(values.size()+1,false);
        chart.getAxisLeft().setLabelCount(values.size()+1,false);

        setChartData(chart, values);
    }

    /**
     * x轴数据处理
     *
     * @return x轴数据
     */
    private static String[] xValuesProcess(String[] data) {
        String[] week = data;
        return week;
    }
}
