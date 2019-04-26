package com.gykj.rollcall.ui.setting;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

import com.gykj.mvvmlibrary.base.BaseActivity;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.ActivityConverseBinding;
import com.gykj.rollcall.utils.ScreenUtil;


public class ConverseActivity extends BaseActivity<ActivityConverseBinding,ConverseViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_converse;
    }
    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        binding.converseRippleView.setDuration(3000);
        binding.converseRippleView.setStyle(Paint.Style.FILL);
        binding.converseRippleView.setColor(getResources().getColor(R.color.color_cc42d0da));
        binding.converseRippleView.setInterpolator(new DecelerateInterpolator());
        binding.converseRippleView.setMaxRadius(ScreenUtil.dip2px(this,150));
        binding.converseRippleView.setSpeed(600);
        binding.converseRippleView.setInitialRadius(ScreenUtil.dip2px(this,30));
        binding.converseRippleView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.converseRippleView.stopImmediately();
    }
}
