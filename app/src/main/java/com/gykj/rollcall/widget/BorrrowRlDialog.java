package com.gykj.rollcall.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.dialog.widget.base.BaseDialog;
import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.R;
import com.gykj.rollcall.adapter.DormitoryAdapter;
import com.gykj.rollcall.model.DormitoryBean;
import com.gykj.rollcall.ui.borrow.BorrowFragment;
import com.gykj.rollcall.ui.borrow.DormitoryCallBack;

import java.util.List;

/**
 * name : HXS
 * e-mail : 1363826037@qq.com
 * descript:借用登记dialog
 * date   : 2019/3/1214:19
 * version: 1.0
 */
public class BorrrowRlDialog extends BaseDialog<BorrrowRlDialog> implements DormitoryCallBack, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    private Button button_ok;
    private Button button_cancle;
    private EditText  ev_stunum;//学生学号
    private EditText ev_username;
  //  private EditText stu_address;
    private EditText stu_wu;
    private int flage=0;
    private BorrowFragment mFragment;
    private RecyclerView recyclerView;
    private LinearLayoutCompat linear_xia;
    private   DormitoryAdapter mAdapter;//获取寝室
    private int mpostion = -1;
    public BorrrowRlDialog(Context context,BorrowFragment fragment,int mflage) {
        super(context);
        this.mFragment = fragment;
        flage = mflage;
    }

    @Override
    public View onCreateView()
    {
        widthScale(0.5f);//宽度
        View inflate = View.inflate(mContext, R.layout.item_borrowdialog, null);
        button_ok  = inflate.findViewById(R.id.borrow_ok);
        button_cancle = inflate.findViewById(R.id.borrow_cancel);
        ev_stunum = inflate.findViewById(R.id.ev_stunum);
        ev_username =inflate.findViewById(R.id.ev_username);
        //stu_address =inflate.findViewById(R.id.stu_address);
        linear_xia = inflate.findViewById(R.id.linear_xia);
        recyclerView = inflate.findViewById(R.id.recycleview);

        stu_wu=inflate.findViewById(R.id.stu_wu);
        if(flage!=-1)
        {
            ev_stunum.setEnabled(false);
            ev_username.setEnabled(false);
            //stu_address.setEnabled(false);
        }
        return  inflate;
    }

    @Override
    public void setUiBeforShow() {
          mAdapter = new DormitoryAdapter(R.layout.layout_borrow_room_item);
           mAdapter.setOnItemChildClickListener(this);
          StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(7,StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
          recyclerView.setAdapter(mAdapter);
          mFragment.getaddress(this);
            button_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String usernum,String userName,String  userId,String dormitoryId,String articleName,String articleNum)

                if(flage== -1)
                {
                    if (!TextUtils.isEmpty(ev_stunum.getText().toString()))
                    {
                        ToastUtils.showShort("请填写学号!");
                        return;
                    }
                    if (!TextUtils.isEmpty(ev_username.getText().toString())){
                        ToastUtils.showShort("请填写学生姓名!");
                            return;

                    }

                    if (!TextUtils.isEmpty(stu_wu.getText().toString())){
                        ToastUtils.showShort("请填写学生所借用物品和数量!");
                        return;
                    }
                    mFragment.addborrow(ev_stunum.getText().toString(),ev_username.getText().toString(),"1","",stu_wu.getText().toString(),"1");
                }else {
                    mFragment.articleborrow(flage+"",stu_wu.getText().toString(),"1");
                }

                dismiss();
            }
        });

    }
    public void  edit(String username,String usernumber,String useradress,String userwu)
    {
        ev_username.setText(username);
        ev_stunum.setText(usernumber);
      //  stu_address.setText(useradress);
        stu_wu.setText(userwu);

    }

    /**
     * 回去宿舍的回调
     * @param data
     */
    @Override
    public void success(List<DormitoryBean> data) {
        mAdapter.setNewData(data);
    }

    @Override
    public void error(String error) {

    }

    //选择了那间寝室
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        this.mAdapter = (DormitoryAdapter) adapter;
        mpostion=position;
        for (int i =0 ; i < mAdapter.getData().size() ; i++)
        {
            mAdapter.getData().get(i).setCheck(false);
        }
        mAdapter.notifyDataSetChanged();
        mAdapter.getData().get(mpostion).setCheck(true);

    }
}
