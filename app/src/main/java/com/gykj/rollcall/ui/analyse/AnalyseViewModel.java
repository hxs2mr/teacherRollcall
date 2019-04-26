package com.gykj.rollcall.ui.analyse;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.rollcall.ui.analyse.fragment.EndFragment;
import com.gykj.rollcall.ui.analyse.fragment.StartFragment;
import com.gykj.rollcall.ui.call.fragment.RuleFragment;
import com.gykj.rollcall.ui.call.fragment.SingleFragment;
import com.gykj.rollcall.utils.FragmentFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * desc   : 点名统计分析ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2712:18
 * version: 1.0
 */
public class AnalyseViewModel extends BaseViewModel {

    public List<Fragment> fragments;


    //初始化Tab fragment
    public void initFragments(){
        fragments = new ArrayList<>();
        fragments.add(new StartFragment());
        fragments.add(new EndFragment());
    }


    public AnalyseViewModel(@NonNull Application application) {
        super(application);
    }
}
