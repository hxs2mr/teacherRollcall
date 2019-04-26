package com.gykj.rollcall.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.gykj.mvvmlibrary.base.BaseQAdapter;
import com.gykj.mvvmlibrary.base.MVViewHolder;
import com.gykj.rollcall.R;
import com.gykj.rollcall.databinding.LayoutItemStartcallBinding;
import com.gykj.rollcall.databinding.LayoutStartingItemBinding;
import com.gykj.rollcall.entity.StartRecordBean;

/**
 * Data on :2019/4/4 0004
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public class CallIngItemAdapter extends BaseQAdapter<StartRecordBean.DormitoryListBean, LayoutStartingItemBinding, MVViewHolder<LayoutStartingItemBinding>> {
    private int rullcallID;

    public CallIngItemAdapter(int layoutResId,int rullcallid) {
        super(layoutResId);
        rullcallID = rullcallid;
    }

    @Override
    protected void convert(MVViewHolder<LayoutStartingItemBinding> helper, StartRecordBean.DormitoryListBean item) {
        helper.getDataViewBinding().setViewModel(item);

        TextView tv_que = helper.getView(R.id.que_tv);
        TextView tv_total = helper.getView(R.id.tv_total);
        TextView tv_drname = helper.getView(R.id.tv_drname);
        int total = item.getSignNum() + item.getUnSignNum();
        if(item.getSignNum()==total)
        {
            tv_que.setBackgroundResource(R.drawable.start_record_no_shape);
            tv_que.setText("全");
            tv_total.setTextColor(Color.parseColor("#999999"));
            tv_drname.setTextColor(Color.parseColor("#999999"));
        }else {
            tv_que.setText("缺");
            tv_que.setBackgroundResource(R.drawable.start_record_yes_shape);
            tv_total.setTextColor(Color.parseColor("#5298fc"));
            tv_drname.setTextColor(Color.parseColor("#5298fc"));
        }
        tv_total.setText(item.getSignNum()+"/"+total);
        helper.setText(R.id.tv_drname,item.getDormitoryName()+"宿舍");

    }
}
