package com.gykj.rollcall.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.dialog.widget.base.BaseDialog;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.R;
import com.gykj.rollcall.adapter.DormitoryAdapter;
import com.gykj.rollcall.model.DormitoryBean;
import com.gykj.rollcall.ui.borrow.BorrowFragment;
import com.gykj.rollcall.ui.borrow.DormitoryCallBack;
import com.gykj.rollcall.utils.DateUtil;

import java.util.List;

/**
 * name : HXS
 * e-mail : 1363826037@qq.com
 * descript:借用登记dialog
 * date   : 2019/3/1214:19
 * version: 1.0
 */
public class PoliceDialog extends BaseDialog<PoliceDialog>{
    private AppCompatTextView tv_title;
    private AppCompatTextView tv_time;
    private AppCompatButton btn_ok;
    private int postion = -1;
    public PoliceDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        widthScale(0.5f);
        View inflate = View.inflate(mContext, R.layout.layout_policedialog, null);
        tv_title  = inflate.findViewById(R.id.tv_title);
        tv_time  = inflate.findViewById(R.id.tv_time);
        btn_ok  = inflate.findViewById(R.id.btn_ok);
        return  inflate;
    }

    @Override
    public void setUiBeforShow() {
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });
    }

    public void initdata(String title,String time)
    {
        tv_title.setText(title+"使用一键报警，向你请求帮助，情况紧急，请尽快处理!");
        long retime = Long.parseLong(time);
        tv_time.setText("发送时间:"+ DateUtil.timeSpanToDateTime1(retime));
    }

}
