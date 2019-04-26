package com.gykj.rollcall.ui.analyse.activity;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.entity.RecordItemBean;
import com.gykj.rollcall.ui.setting.ConverseActivity;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * name : HXS
 * e-mail : 1363826037@qq.com
 * descript:签到记录modeview
 * date   : 2019/3/149:15
 * version: 1.0
 */
public class RecordDetailViewModel extends BaseViewModel {

    //返回
    public BindingCommand cancelonclick = new BindingCommand(new BindingAction() {
        @Override
        public void call(){
            finish();
        }
    });

    //语音按钮的点击事件
    public BindingCommand voiceOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(ConverseActivity.class);
        }
    });

    //给Recycleview添加ObservableList
    public ObservableList<RecordItemViewModel> observableList = new ObservableArrayList<>();

    //给RecycleView添加itemBinding
    public ItemBinding<RecordItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.layout_record_item);

    //Adapter
    public BindingRecyclerViewAdapter<RecordItemViewModel> adapter = new BindingRecyclerViewAdapter<>();

    //测试数据
    int number  =  1;

    public RecordDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public  void  testnetwork()
    {
        for (int i = 0; i < 20 ; i ++)
        {
            number = number*(-1);
            RecordItemBean bean = new RecordItemBean(i+"",i+1+"",number);
            RecordItemViewModel itemViewModel = new RecordItemViewModel(this,bean);
            observableList.add(itemViewModel);
        }
    }

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //Drawer显示
        public ObservableBoolean showDrawer = new ObservableBoolean(false);

        public ObservableBoolean clickPosition = new ObservableBoolean(false);
    }
}
