package com.gykj.rollcall.ui.analyse.activity;

import android.os.Bundle;

import com.gykj.mvvmlibrary.base.BaseActivity;
import com.gykj.mvvmlibrary.entity.Config;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.ActivityRecodeBinding;

/**
 * name : HXS
 * e-mail : 1363826037@qq.com
 * descript:个人点名记录 详情
 * date   : 2019/3/149:15
 * version: 1.0
 */
public class RecordDetailActivity extends BaseActivity<ActivityRecodeBinding,RecordDetailViewModel> {

    private Bundle bundle;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_recode;
    }
    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    @Override
    public void initData() {
        bundle  = getIntent().getExtras();
        viewModel.testnetwork();
        String name = bundle.getString(Config.NAME);
        binding.detailNameRecord.setText(name+"历史签到记录");
        binding.detailName.setText(name+"的签到记录");
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
    }
}
