package com.gykj.mvvmlibrary.binding.viewadapter.spinner;


/**
 * desc   :下拉Spinner控件的键值对, 实现该接口,返回key,value值, 在xml绑定List<IKeyAndValue>
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/249:27
 * version: 1.0
 */
public interface IKeyAndValue {
    String getKey();

    String getValue();
}
