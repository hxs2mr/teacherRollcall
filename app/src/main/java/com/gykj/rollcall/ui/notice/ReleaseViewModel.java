package com.gykj.rollcall.ui.notice;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gykj.mvvmlibrary.base.BaseViewModel;
import com.gykj.mvvmlibrary.binding.command.BindingAction;
import com.gykj.mvvmlibrary.binding.command.BindingCommand;
import com.gykj.mvvmlibrary.entity.BaseEntity;
import com.gykj.mvvmlibrary.utils.RxUtils;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.R;
import com.gykj.rollcall.model.NoticeAddBean;
import com.gykj.rollcall.model.api.RollCallApi;
import com.gykj.rollcall.ui.index.MainViewModel;
import com.gykj.rollcall.ui.notice.image.ImageItemViewModel;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.BindingListViewAdapter;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import retrofit2.http.PUT;


/**
 * desc   :
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2711:52
 * version: 1.0
 */
public class ReleaseViewModel extends BaseViewModel {


    public ObservableField<String> title = new ObservableField<>("");
    public ObservableField<String> content = new ObservableField<>("");

    //图片recycleview
    public ObservableList<ImageItemViewModel> observableList = new ObservableArrayList<>();

    public ItemBinding<ImageItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.layout_image);

    public BindingRecyclerViewAdapter<ImageItemViewModel> adapter = new BindingRecyclerViewAdapter<>();

    //返回按钮点击绑定
    public BindingCommand cancleOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    //选择照片按钮的点击事件
    public BindingCommand selectOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.selectPhoto.set(!uc.selectPhoto.get());
        }
    });

    //确定提交按钮

    public BindingCommand uploadOnclickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
         uc.uploadb.set(!uc.uploadb.get());
        }
    });



    //用户名的绑定
    public ObservableField<String> imageSize = new ObservableField<>( observableList.size()+"/4");

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //选择图片
        public ObservableBoolean selectPhoto = new ObservableBoolean(false);
        //是否点击确定
        public ObservableBoolean uploadb = new ObservableBoolean(false);

    }
    public ReleaseViewModel(@NonNull Application application) {
        super(application);
    }


    public void initEdit(String mtitle,String mcontent ,String img)
    {
        title.set(mtitle);
        content.set(mcontent);
        String[]  img_data = img.split(",");//图片的地址
    }

    public void  upload(String img)
    {
        Disposable disposable = RollCallApi.getInstance().addnotice(title.get(),content.get(),img)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<Boolean>>() {
                    @Override
                    public void accept(BaseEntity<Boolean> noticeAddBeanBaseEntity) {
                            //添加成功
                        Log.d("HXS",""+noticeAddBeanBaseEntity);
                        ToastUtils.showShort("发布成功!");
                        finish();
                    }
                },getErrorConsumer());
        addDisposable(disposable);
    }


    /**修改通知**/
    public void changenotice(int id ,String title,String img,String content)
    {  Disposable disposable = RollCallApi.getInstance().changenotice(id,title,img,content)
            .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<BaseEntity<String>>() {
                @Override
                public void accept(BaseEntity<String> result) throws Exception {
                    ToastUtils.showShort("修改成功!");
                    finish();
                }
            },getErrorConsumer());
        addDisposable(disposable);

    }


    /**
     * 选择图片加载
     */
    public void initImgData(List<LocalMedia> selectList)
    {
        for(int i =0 ; i < selectList.size();i++)
        {
            ImageItemViewModel imageItemViewModel = new ImageItemViewModel(this,selectList.get(i).getPath(),i);
            observableList.add(imageItemViewModel);
        }

        imageSize.set(observableList.size()+"/4");
    }
    @Override
    public void onDestroy() {
        if(isAttached()){
            cancel();
        }
        super.onDestroy();
    }

}
