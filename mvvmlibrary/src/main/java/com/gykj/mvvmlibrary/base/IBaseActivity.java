package com.gykj.mvvmlibrary.base;

/**
 * desc   : 基类Activity V 层
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/249:42
 * version: 1.0
 */
public interface IBaseActivity {

    /**
     * 初始化界面传递参数
     */
    void initParam();
    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();
}
