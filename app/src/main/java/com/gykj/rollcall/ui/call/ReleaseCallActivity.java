package com.gykj.rollcall.ui.call;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.gykj.mvvmlibrary.base.BaseActivity;
import com.gykj.mvvmlibrary.bus.Messenger;
import com.gykj.mvvmlibrary.bus.RxBus;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.adapter.ReleaseViewPagerAdapter;
import com.gykj.rollcall.databinding.ActivityReleaseCallBinding;
import com.gykj.rollcall.model.RollPageBean;
import com.gykj.rollcall.ui.call.adapter.CallViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * desc   : 发布点名界面
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2813:55
 * version: 1.0
 */
public class ReleaseCallActivity extends BaseActivity<ActivityReleaseCallBinding,ReleaseCallViewModel> {
    private int flage =0;
    private int Rooltype =0 ;
    private Bundle bundle;
    private RollPageBean.RecordsBean bean;
    private String[] tabTitle=new String[]{"单次点名","定点点名"};//,"共同目标"};
    private ReleaseViewPagerAdapter adapter;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_release_call;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            flage = bundle.getInt("FLAGE");
        }
        viewModel.initFragments();

        binding.callTabLayout.setupWithViewPager(binding.callViewPager);
        binding.callViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.callTabLayout));

        binding.callViewPager.setFocusable(true);
        binding.callViewPager.setFocusableInTouchMode(true);
        binding.callViewPager.requestFocus();

        if(binding.callTabLayout.getTabCount()>0)
        {
            binding.callTabLayout.removeAllTabs();
        }

        for(int i = 0 ; i < tabTitle.length ;i++)
        {
            binding.callTabLayout.addTab(binding.callTabLayout.newTab().setText(tabTitle[i]));
        }

        adapter = new ReleaseViewPagerAdapter(getSupportFragmentManager(),tabTitle,viewModel.fragments);
        binding.callViewPager.setAdapter(adapter);
        binding.callViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.callTabLayout));

        binding.callViewPager.setOffscreenPageLimit(2);

        binding.callViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        binding.callTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //在选中的顶部标签时，为viewpager设置currentitem
                binding.callViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        if(flage==1)//表示编辑
        {
            Rooltype = bundle.getInt("Rooltype");
            bean = (RollPageBean.RecordsBean) bundle.getSerializable("bean");
            if(Rooltype ==0)//单次点名
            {
                binding.callViewPager.setCurrentItem(0);
                //发送一个带数据回调消息
                //参数1：回调的实体
                //参数2：定义的token
               // Messenger.getDefault().send(bean ,  "sign");

                //RxBus.getDefault().post(bean);

            }else {//定点点名
                binding.callViewPager.setCurrentItem(1);
              //Messenger.getDefault().send(bean,"double");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
