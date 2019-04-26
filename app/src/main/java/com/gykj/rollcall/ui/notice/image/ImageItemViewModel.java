package com.gykj.rollcall.ui.notice.image;

import android.annotation.SuppressLint;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.ItemViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.ui.notice.NoticeViewModel;
import com.gykj.rollcall.ui.notice.ReleaseViewModel;

import me.tatarka.bindingcollectionadapter2.OnItemBind;

/**
 * Data on :2019/3/29 0029
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :图片item的基类
 */
public class ImageItemViewModel extends ItemViewModel<ReleaseViewModel> {

    //去除该张图片
    public BindingCommand OnClickClose = new BindingCommand(new BindingAction() {
        @SuppressLint("ResourceType")
        @Override
        public void call() {
          //   ToastUtils.showShortSafe(viewModel.observableList.indexOf(this));
        }
    });

    private  int mPostion;
    public ObservableField<String> field = new ObservableField<>();
    public ImageItemViewModel(@NonNull ReleaseViewModel viewModel,String data,int postion) {
        super(viewModel);
        this.field.set(data);
        this.mPostion = postion;
    }

    public ObservableField<String> getField() {
        return field;
    }


}
