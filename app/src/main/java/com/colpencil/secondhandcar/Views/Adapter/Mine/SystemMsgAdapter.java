package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;
import android.view.View;

import com.colpencil.secondhandcar.Bean.Response.MessageInfo;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.OnItemClickListener;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */
public class SystemMsgAdapter extends SuperAdapter<MessageInfo> {

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public SystemMsgAdapter(Context context, List<MessageInfo> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, MessageInfo item) {
        if(item.getHurry() == 1){
            holder.getView(R.id.img_urgent).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.img_urgent).setVisibility(View.GONE);
        }
        holder.setText(R.id.text_msg, item.getTitle());
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
