package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;
import android.view.View;

import com.colpencil.secondhandcar.Bean.Response.MsgPeriod;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.OnItemClickListener;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 * 分期购车通知适配器
 */
public class MsgPeriodAdapter extends SuperAdapter<MsgPeriod> {

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public MsgPeriodAdapter(Context context, List<MsgPeriod> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, MsgPeriod item) {
        holder.setText(R.id.text_msg, item.getTitle());
        holder.setText(R.id.text_content, item.getContent());
        holder.setText(R.id.text_time, StringUtils.formatDate(item.getCreate_time()));
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
