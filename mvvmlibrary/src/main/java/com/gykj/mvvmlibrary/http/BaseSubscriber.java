package com.gykj.mvvmlibrary.http;

import android.content.Context;
import android.widget.Toast;

import com.gykj.mvvmlibrary.utils.KLog;
import com.gykj.mvvmlibrary.utils.ToastUtils;

import io.reactivex.observers.DisposableObserver;
/**
 * desc   : 该类仅供参考，实际业务Code, 根据需求来定义，
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/249:27
 * version: 1.0
 */
public abstract class BaseSubscriber<T> extends DisposableObserver<T> {
    public abstract void onResult(T t);

    private Context context;
    private boolean isNeedCahe;

    public BaseSubscriber(Context context) {
        this.context = context;
    }

    @Override
    public void onError(Throwable e) {
        KLog.e(e.getMessage());
        // todo error somthing

        if (e instanceof ResponseThrowable) {
            onError((ResponseThrowable) e);
        } else {
            onError(new ResponseThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        Toast.makeText(context, "http is start", Toast.LENGTH_SHORT).show();
        // todo some common as show loadding  and check netWork is NetworkAvailable
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtil.isNetworkAvailable(context)) {
            Toast.makeText(context, "无网络，读取缓存数据", Toast.LENGTH_SHORT).show();
            onComplete();
        }

    }

    @Override
    public void onComplete() {

        Toast.makeText(context, "http is Complete", Toast.LENGTH_SHORT).show();
        // todo some common as  dismiss loadding
    }


    public abstract void onError(ResponseThrowable e);

    @Override
    public void onNext(Object o) {
        BaseResponse baseResponse = (BaseResponse) o;
        if (baseResponse.getCode() == 0) {
            onResult((T) baseResponse.getData());
        } else if (baseResponse.getCode() == 330) {
            ToastUtils.showShort(baseResponse.getMsg());
        } else if (baseResponse.getCode() == 503) {
            KLog.e(baseResponse.getMsg());
        } else {
            ToastUtils.showShort("操作失败！错误代码:" + baseResponse.getCode());
        }
    }
}
