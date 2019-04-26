package com.gykj.rollcall.utils;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import java.util.ArrayList;

/**
 * 作者： XS
 * 邮箱：1363826037@qq.com
 * 描述:
 * 创建时间:  2019\1\26 0026 14:38
 */
public class PieChartUtil {
    private static PieChartUtil INSTANCE = new PieChartUtil();
    public PieChart mChart;
    private Context mcontext;
    public static PieChartUtil getInstance() {
        return INSTANCE;
    }

    public void PieChartUtil(PieChart pieChart, Context context,PieData pieData,SpannableString centets) {
        mcontext =  context;
        mChart = pieChart;
        initReportChart(mChart,pieData, centets);
    }
    public static void initReportChart(final PieChart mChart,PieData data ,SpannableString centets ) {
        mChart.setUsePercentValues(true);
        mChart.setExtraOffsets(5,10,5,5);  //设置间距
        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setRotationAngle(90); // 初始旋转角度
        mChart.setRotationEnabled(true); // 可以手动旋转

        mChart.setCenterText(centets);
        mChart.setCenterTextSize(15f);
        mChart.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mChart.setCenterTextColor(Color.parseColor("#707070"));
        mChart.setDrawHoleEnabled(true);//空心猿

        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);

        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(50f);
        // 设置饼图是否接收点击事件，默认为true
        mChart.setTransparentCircleRadius(50f);
        mChart.setTouchEnabled(true);  //设置是否响应点击触摸

        mChart.setDrawCenterText(true);  //设置是否绘制中心区域文字

        mChart.setDrawEntryLabels(false);  //设置是否绘制标签

        mChart.setHighlightPerTapEnabled(true);  //设置是否高亮显示触摸的区域

        //mChart.setData(pieData);  //设置数据
        Legend mLegend = mChart.getLegend(); //设置比例图
        mLegend.setEnabled(false);
        mChart.setDrawMarkerViews(true);  //设置是否绘制标记
        //设置是否显示区域文字内容
        mChart.setDrawSliceText(mChart.isDrawEntryLabelsEnabled());
        mChart.highlightValues(null);
        mChart.setDrawEntryLabels(true);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);  //设置动画效果
        mChart.setData(data);
    }
    public PieData initData( ArrayList entries , ArrayList  colors)
    {
        PieDataSet dataSet =new PieDataSet(entries,"");

        dataSet.setSliceSpace(1f);  //设置不同区域之间的间距

        dataSet.setSelectionShift(8f);

        dataSet.setColors(colors);

        PieData data =new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());

        data.setValueTextSize(12f);

        data.setValueTextColor(Color.parseColor("#ffffff"));

       return data;
    }
}
