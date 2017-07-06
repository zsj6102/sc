package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;

import com.colpencil.secondhandcar.Bean.Response.Repayment;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 */
public class RepaymentsAdapter extends SuperAdapter<Repayment> {

    public RepaymentsAdapter(Context context, List<Repayment> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Repayment item) {
        holder.setText(R.id.text_name, item.getName());
        holder.setText(R.id.text_number, item.getIns_num());
        holder.setText(R.id.text_price, StringUtils.doubleFormat(item.getPrice())+"元");
        holder.setText(R.id.text_time, StringUtils.formatTime(item.getPay_time()));
    }
}
