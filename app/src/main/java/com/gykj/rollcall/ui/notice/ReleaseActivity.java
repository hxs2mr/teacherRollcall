package com.gykj.rollcall.ui.notice;

import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.gykj.mvvmlibrary.base.BaseActivity;
import com.gykj.mvvmlibrary.utils.Contract;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.ActivityReleaseBinding;
import com.gykj.rollcall.BR;
import com.gykj.rollcall.oss.OssUpload;
import com.gykj.rollcall.oss.UploadCallBack;
import com.gykj.rollcall.ui.index.MainActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.BindingListViewAdapter;

/**
 * desc   : 发布通告界面
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2711:54
 * version: 1.0
 */
public class ReleaseActivity extends BaseActivity<ActivityReleaseBinding,ReleaseViewModel> implements UploadCallBack {

    private List<LocalMedia> mediaList = new ArrayList<>();
    private String mPhotoPath;

    private Bundle bundle;

    //标记是发布通知还是修改通知  0 表示发布  1表示修改
    private int IsFlage=0;
    private String title;
    private String content;
    private String UrlImg;
    private int ID;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_release;
    }
    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        bundle = getIntent().getExtras();
        if (bundle != null)
        {
            initbund(bundle);
        }

        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if(sdCardExist){
            mPhotoPath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"gykj/";
        }else {
            mPhotoPath = getFilesDir().getAbsolutePath() + File.separator+"gykj/";
        }
        File f = new File(mPhotoPath);
        if(!f.exists()){
            f.mkdir();
        }
    }

    @Override
    public void initViewObservable() {
        /**
         * 确认提交
         */
        viewModel.uc.uploadb.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (TextUtils.isEmpty(viewModel.title.get())) {
                    ToastUtils.showShort("请输入通知标题！");
                    return;
                }
                if (TextUtils.isEmpty(viewModel.content.get())) {
                    ToastUtils.showShort("请输入通知的内容！");
                    return;
                }
                if(IsFlage==0)
                {
                    //发布通知
                    viewModel.upload("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1633600873,148835269&fm=26&gp=0.jpg");
                }else {
                    //修改通知
                    viewModel.changenotice(ID,viewModel.title.get(),"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1633600873,148835269&fm=26&gp=0.jpg",viewModel.content.get());
                }

            }
        });

        viewModel.uc.selectPhoto.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                PictureSelector.create(ReleaseActivity.this)
                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(R.style.picture_white_style)//主题样式(不设置为默认样式) 也可参考demo   m   jfde  <e></e>/styles下 例如：R.style.picture.white.style
                        .maxSelectNum(4)// 最大图片选择数量 int
                        .minSelectNum(1)// 最 小选择数量 int
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.MULTIPLE )// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .previewImage(true)// 是否可预览图片 true or false
                        .isCamera(true)// 是否显示拍照按钮 true or false
                        .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                        .enableCrop(false)// 是否裁剪 true or false
                        .compress(true)// 是否压缩 true or false
                        .compressSavePath(mPhotoPath)//压缩图片保存地址
                        .openClickSound(true)// 是否开启点击声音 true or false
                        .selectionMedia(mediaList)// 是否传入已选图片 List<LocalMedia> list
                        .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        .isDragFrame(false)// 是否可拖动裁剪框(固定)
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:

                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    mediaList = selectList;
                    //显示图片
                    viewModel.initImgData(selectList);

                    //上传图片
                    //OssUpload.getInstance().ASYuploadIMG(selectList.get(0).getPath(),this);

                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
    }


    private  void initbund(Bundle bundle)
    {
        IsFlage = bundle.getInt(Contract.EditFlage);
        if(IsFlage == 1)//表示编辑通知
        {
            title = bundle.getString(Contract.TITLE);
            content = bundle.getString(Contract.CONTENT);
            UrlImg = bundle.getString(Contract.IMG);

            viewModel.initEdit(title,content,UrlImg);
        }
    }

    /**
     * 图片上传成功之后的回调
     * @param success 图片的地址
     */
    @Override
    public void success(String success) {

    }

    @Override
    public void error(String error) {

    }
}
