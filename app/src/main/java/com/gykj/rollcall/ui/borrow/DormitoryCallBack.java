package com.gykj.rollcall.ui.borrow;

import com.gykj.rollcall.model.DormitoryBean;

import java.util.List;

/**
 * Data on :2019/4/9 0009
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public interface DormitoryCallBack {
    void success( List<DormitoryBean> data);
    void  error(String error);
}
