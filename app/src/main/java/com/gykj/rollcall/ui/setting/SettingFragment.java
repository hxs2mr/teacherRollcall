package com.gykj.rollcall.ui.setting;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gykj.mvvmlibrary.base.BaseFragment;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.FragmentNoticeBinding;
import com.gykj.rollcall.ui.notice.NoticeViewModel;
import com.gykj.rollcall.widget.ChangePasswordDialog;
import com.gykj.rollcall.widget.DataSettDialog;

/**
 * desc   : 设置Fragment
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2711:23
 * version: 1.0
 */
public class SettingFragment extends BaseFragment<FragmentNoticeBinding,SettingViewModel> {

    private ChangePasswordDialog changePasswordDialog;
    private DataSettDialog dataSettDialog;
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_setting;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.uc.showDrawer.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(viewModel.flage==0)
                {//表示资料修改
                        dataSettDialog = new DataSettDialog(getContext());
                        dataSettDialog.show();
                }else {//表示密码修改
                    changePasswordDialog = new ChangePasswordDialog(getContext());
                    changePasswordDialog.show();
                }
            }
        });

    }
}
