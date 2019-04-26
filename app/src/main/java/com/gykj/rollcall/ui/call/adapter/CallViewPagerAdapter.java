package com.gykj.rollcall.ui.call.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * desc   : 点名页面ViewPagerAdapter
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2814:15
 * version: 1.0
 */
public class CallViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    private List<String> mTitleList;

    public CallViewPagerAdapter(FragmentManager fm, List<Fragment> fragments,List<String> titleList) {
        super(fm);
        this.mFragments = fragments;
        this.mTitleList = titleList;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
