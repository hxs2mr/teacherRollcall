package com.gykj.rollcall.ui.notice;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.rollcall.entity.LossEntity;
import com.gykj.rollcall.entity.MainEntity;
import com.gykj.rollcall.model.NoticeBean;

/**
 * desc   : 通知详情ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/315:23
 * version: 1.0
 */
public class NoticeDetailViewModel extends BaseViewModel {

    public ObservableField<NoticeBean.RecordsBean> entity = new ObservableField<>();

    //返回按钮点击事件绑定
    public BindingCommand cancleOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });


    public NoticeDetailViewModel(@NonNull Application application) {
        super(application);
    }

    
    public void setNoticeDetail(NoticeBean.RecordsBean entity){
        this.entity.set(entity);
    }
}
