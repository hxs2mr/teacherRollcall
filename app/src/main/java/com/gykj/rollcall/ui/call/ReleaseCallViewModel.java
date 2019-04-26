package com.gykj.rollcall.ui.call;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.rollcall.ui.call.fragment.RuleFragment;
import com.gykj.rollcall.ui.call.fragment.SingleFragment;
import com.gykj.rollcall.utils.FragmentFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * desc   : 发布点名ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2813:56
 * version: 1.0
 */
public class ReleaseCallViewModel extends BaseViewModel {

    public List<Fragment> fragments;

    //返回按钮点击绑定
    public BindingCommand cancleOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    //初始化Tab fragment
    public void initFragments(){
        fragments = new ArrayList<>();
        fragments.add(new SingleFragment());
        fragments.add(new RuleFragment());
    }

    public ReleaseCallViewModel(@NonNull Application application) {
        super(application);
    }
}
