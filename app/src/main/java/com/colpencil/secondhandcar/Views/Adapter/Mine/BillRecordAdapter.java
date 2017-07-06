package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;
import android.view.View;

import com.colpencil.secondhandcar.Bean.Response.BillRecord;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.OnItemClickListener;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 */
public class BillRecordAdapter extends SuperAdapter<BillRecord> {

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public BillRecordAdapter(Context context, List<BillRecord> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, BillRecord item) {
        holder.setText(R.id.text_time, StringUtils.formatDate(item.getCreate_time()));
        holder.setText(R.id.text_price, StringUtils.doubleFormat(item.getMoney())+"元");
        holder.setText(R.id.text_number, item.getSn());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(v, layoutPosition);
                }
            }
        });
    }
}
