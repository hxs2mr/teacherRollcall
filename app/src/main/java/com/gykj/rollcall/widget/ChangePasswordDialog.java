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
 * descript:修改密码
 * date   : 2019/3/1416:04
 * version: 1.0
 */
public class ChangePasswordDialog extends BaseDialog<ChangePasswordDialog> {
    private Button borrow_ok;
    public ChangePasswordDialog(Context context) {
        super(context);
    }
    @Override
    public View onCreateView() {
        widthScale(0.5f);
        View inflate = View.inflate(mContext, R.layout.dialog_changepassword,null);
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
