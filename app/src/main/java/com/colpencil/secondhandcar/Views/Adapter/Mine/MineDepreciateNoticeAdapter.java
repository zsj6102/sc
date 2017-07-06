package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Bean.Response.MineDepreciateNotice;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.OnItemClickListener;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 * 降价通知适配器
 */
public class MineDepreciateNoticeAdapter extends SuperAdapter<MineDepreciateNotice> {

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public MineDepreciateNoticeAdapter(Context context, List<MineDepreciateNotice> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, MineDepreciateNotice item) {
        Glide.with(mContext)
                .load(item.getPic())
                .into((ImageView) holder.getView(R.id.img_car));
        if(item.getMarket_enable() == 1){
            holder.getView(R.id.img_beauty).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.img_beauty).setVisibility(View.VISIBLE);
        }
        holder.setText(R.id.tv_name, item.getName());
        holder.setText(R.id.tv_ma, StringUtils.doubleFormat(item.getPrice())+"万");
        holder.setText(R.id.tv_statue, item.getReduce_price()+"元");
        holder.setText(R.id.text_age, StringUtils.formatDate(item.getCreate_time()));
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
