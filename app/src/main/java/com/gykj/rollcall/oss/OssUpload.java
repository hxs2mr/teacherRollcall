package com.gykj.rollcall.oss;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.gykj.mvvmlibrary.entity.Config;
import com.gykj.mvvmlibrary.utils.Contract;
import com.gykj.rollcall.app.RollCallApplication;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import retrofit2.http.PUT;

/**
 * Data on :2019/3/28 0028
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on : 阿里OsS图片上传基类
 */
public class OssUpload {

    private static OssUpload ossUpload;


    private OSS oss ;

    //使用OSSAuthCredentialsProvider。token过期可以及时更新。
    private OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(Contract.STS_URL);

    //上传的路径
    private String uploadPath;
    private UploadCallBack mUploadCallBack;
    private OssUpload(){
        uploadtimeout();
        oss = new OSSClient(RollCallApplication.getInstance(), Contract.OSS_BASE,credentialProvider);
    }

    /**
     * 线程安全懒汉单利
     * @return
     */
    public static synchronized OssUpload getInstance()
    {
        if(ossUpload == null)
        {
            ossUpload = new OssUpload();
        }
        return  ossUpload;
    }

    /**
     * 上传超时 时间设置
     */
    public void uploadtimeout()
    {
        // 配置类如果不设置，会有默认配置。
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个。
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。
    }

    /**
     * 同步 上传图片
     * @param filePaths  图片地址
     */
    public  void uploadIMG(List<String> filePaths){
        /**
         * 阿里云上文件名称
         */
        for(String uploadFilePath :filePaths)
        {
            File uploadFile = new File(uploadFilePath);
            String uuid = UUID.randomUUID().toString().replace("-", "");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String dateString = sdf.format(new Date());
            String fileName = uploadFile.getName();
            String endName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

            //uploadPath上传
            String objectKey = uploadPath + File.separator + uuid + dateString + "." + endName;
            PutObjectRequest put = new PutObjectRequest(Contract.BUKET_NAME,objectKey,uploadFile.getPath());
            try {
                PutObjectResult putResult = oss.putObject(put);
            } catch (ClientException e) {
                e.printStackTrace();
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 异步上传
     */

    public void ASYuploadIMG(String filePaths, UploadCallBack uploadCallBack)
    {
        this.mUploadCallBack = uploadCallBack;


        File uploadFile = new File(filePaths);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = sdf.format(new Date());
        String fileName = uploadFile.getName();
        String endName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        //uploadPath上传
        String objectKey = uploadPath + File.separator + uuid + dateString + "." + endName;
        PutObjectRequest put = new PutObjectRequest(Contract.BUKET_NAME,objectKey,uploadFile.getPath());
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

              String imurl =    getOSSFileUrl(request);
                //上传成功 回调
               // mUploadCallBack.success();
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                //上传失败  回调
            }
        });
    }
    private String getOSSFileUrl(PutObjectRequest putObjectRequest) {
        return "http://" + Contract.BUKET_NAME + "." + Contract.OSS_BASE + "/" + putObjectRequest.getObjectKey();
    }

}
