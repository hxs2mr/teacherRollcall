package com.gykj.rollcall.ui.analyse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gykj.mvvmlibrary.base.BaseFragment;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.adapter.ReleaseViewPagerAdapter;
import com.gykj.rollcall.databinding.FragmentAnalyseBinding;
import com.gykj.rollcall.databinding.FragmentNoticeBinding;
import com.gykj.rollcall.ui.call.adapter.CallViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * desc   : 点名统计分析Fragment
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2711:23
 * version: 1.0
 */
public class AnalyseFragment extends BaseFragment<FragmentAnalyseBinding,AnalyseViewModel> {

    private ReleaseViewPagerAdapter adapter;
    private String[] tabTitle=new String[]{"进行中","已结束"};//,"共同目标"};
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_analyse;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.initFragments();

        binding.analyseTabLayout.setupWithViewPager(binding.analyseViewPager);
        binding.analyseViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.analyseTabLayout));

        binding.analyseViewPager.setFocusable(true);
        binding.analyseViewPager.setFocusableInTouchMode(true);
        binding.analyseViewPager.requestFocus();

        if(binding.analyseTabLayout.getTabCount()>0)
        {
            binding.analyseTabLayout.removeAllTabs();
        }

        for(int i = 0 ; i < tabTitle.length ;i++)
        {
            binding.analyseTabLayout.addTab(binding.analyseTabLayout.newTab().setText(tabTitle[i]));
        }

        adapter = new ReleaseViewPagerAdapter(getChildFragmentManager(),tabTitle,viewModel.fragments);

        binding.analyseViewPager.setAdapter(adapter);
        binding.analyseViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.analyseTabLayout));
        binding.analyseViewPager.setOffscreenPageLimit(2);
        binding.analyseViewPager.setCurrentItem(0);
        binding.analyseViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //滑动监听加载数据，一次只加载一个标签页
                adapter.getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.analyseTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //在选中的顶部标签时，为viewpager设置currentitem
                binding.analyseViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
