package com.gykj.rollcall.widget;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.flyco.dialog.widget.base.BaseDialog;
import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.gykj.rollcall.R;

/**
 * name : HXS
 * e-mail : 1363826037@qq.com
 * descript:资料修改
 * date   : 2019/3/1416:52
 * version: 1.0
 */
public class DataSettDialog extends BaseDialog<DataSettDialog> {

    Button borrow_ok;

    public DataSettDialog(Context context) {
        super(context);
    }
    @Override
    public View onCreateView() {
        widthScale(0.5f);//宽度
        View inflate = View.inflate(mContext, R.layout.dialog_datasetting,null);
        borrow_ok = inflate.findViewById(R.id.borrow_ok);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        borrow_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }
}
