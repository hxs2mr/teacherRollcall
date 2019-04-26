package com.gykj.rollcall.ui.analyse.fragment;

import android.databinding.Observable;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.gykj.mvvmlibrary.base.BaseCancleFragment;
import com.gykj.mvvmlibrary.base.BaseFragment;
import com.gykj.mvvmlibrary.utils.KLog;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.FragmentEndBinding;
import com.gykj.rollcall.utils.BarChartManager;
import com.gykj.rollcall.utils.PieChartManager;
import com.gykj.rollcall.utils.PieChartUtil;

import java.util.ArrayList;
import java.util.List;

import static com.gykj.rollcall.utils.LineChartManager.initChart;
import static com.gykj.rollcall.utils.LineChartManager.notifyDataSetChanged;

/**
 * desc   : 已结束Fragment
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/229:31
 * version: 1.0
 */
public class EndFragment extends BaseCancleFragment<FragmentEndBinding,EndViewModel> {

    private ArrayList colors = new ArrayList<>();

    //历史签到人数
    private BarChartManager barChartManager;

    //签到率TOP10
    private BarChartManager barChartManager_dao;

    //饼状图
    private PieChartManager pieChartManager;

    private boolean isReady = false;
    private boolean isLoaded = false;//防止重复加载
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_end;
    }

    @Override
    public int initVariableId()  {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        isReady = true;
    }
    /**
     * 防止预加载
     */
    @Override
    protected void lazyLoad() {
        if (!isReady || !isVisible || isLoaded) {
            return;
        }
        isLoaded = true;
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        barChartManager = new BarChartManager(binding.endTotalChart,getActivity());
        final List<String> dateList = new ArrayList<>();
        dateList.add("2019-1-1");
        dateList.add("2019-1-2");
        dateList.add("2019-1-3");
        dateList.add("2019-1-4");
        dateList.add("2019-1-5");
        dateList.add("2019-1-6");
        barChartManager.showBarChart(dateList);

        //测试折现图
        initChart(binding.endTotalLinechart,getActivity());


        //签到率柱状图
        barChartManager_dao = new BarChartManager(binding.endDaoBarchart,getActivity());
        barChartManager_dao.showLineBarChart(dateList,viewModel.entryList);


        viewModel.userUnSignTop();
        viewModel.dormitoryUnSignNumTop();
        viewModel.rollHistory("2019-04-24","2019-04-26");

    }

    @Override
    public void initViewObservable() {
        viewModel.uc.isPie.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                showhodlePieChart();
            }
        });
        viewModel.uc.isUserLineChart.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                notifyDataSetChanged(binding.endTotalLinechart,viewModel.userlineEntrys,viewModel.userlineName);
            }
        });

    }
    private void showhodlePieChart() {
        //设置每份的颜色
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#5298fc"));
        colors.add(Color.parseColor("#2ee0c5"));
        colors.add(Color.parseColor("#8378ea"));
        colors.add(Color.parseColor("#e7bcf3"));
        colors.add(Color.parseColor("#fb7293"));
        colors.add(Color.parseColor("#ff9f7f"));
        colors.add(Color.parseColor("#ffdb5c"));
        colors.add(Color.parseColor("#9fe6b8"));
        colors.add(Color.parseColor("#32c5e9"));
        colors.add(Color.parseColor("#37a2da"));
        //测试饼状图
        pieChartManager = new PieChartManager(binding.endTotalPiechart);
        pieChartManager.showRingPieChart(viewModel.PieEntrys,colors);
    }
}
