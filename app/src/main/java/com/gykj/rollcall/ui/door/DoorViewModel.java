package com.gykj.rollcall.ui.door;

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
import com.gykj.rollcall.adapter.DoorAdapter;
import com.gykj.rollcall.entity.DoorEntity;
import com.gykj.rollcall.ui.borrow.BorrowItemViewModel;
import com.gykj.rollcall.ui.index.MainViewModel;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * desc   : 门径考勤ViewModel
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2712:18
 * version: 1.0
 */
public class DoorViewModel extends BaseViewModel {

/*    //给RecyclerView添加ObservableList
    public ObservableList<DoorItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public ItemBinding<DoorItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.layout_door_item);
    //RecyclerView多布局写法
    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    public final BindingRecyclerViewAdapter<DoorItemViewModel> adapter = new BindingRecyclerViewAdapter<>();
    */

    public DoorAdapter adapter = new DoorAdapter(R.layout.layout_door_item);
    public DoorViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 请求网络
     */
    public void requestNetwork(){



    }

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //Drawer显示
        public ObservableBoolean showDrawer = new ObservableBoolean(false);

        public ObservableBoolean clickPosition = new ObservableBoolean(false);
    }
}
