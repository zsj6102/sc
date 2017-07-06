package com.colpencil.secondhandcar.Views.Adapter.Home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Bean.Response.FriendRecommend;
import com.colpencil.secondhandcar.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/23.
 */
public class RecommendAdapter extends SuperAdapter<FriendRecommend> {

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public RecommendAdapter(Context context, List<FriendRecommend> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, FriendRecommend item) {
        if(item.getNew_shelves() == 1){
            holder.getView(R.id.img_tag).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.img_tag).setVisibility(View.GONE);
        }
        ImageView imageView = holder.getView(R.id.img_car);
        Glide.with(mContext)
                .load(item.getOriginal())
                .into(imageView);
        holder.setText(R.id.text_name, item.getName());
        holder.setText(R.id.tv_ma, item.getPrice()+"万");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(item.getListed_time());
        holder.setText(R.id.text_age,sdf.format(date)+"    "+item.getMileage()+"万公里");
        holder.setText(R.id.tv_statue, item.getCity_name());
        if(item.getIs_urgent() == 1){
            ((ImageView) holder.getView(R.id.img_sale)).setVisibility(View.VISIBLE);
        } else {
            ((ImageView) holder.getView(R.id.img_sale)).setVisibility(View.GONE);
        }

        if(item.getInstallment() == 1){
            ((ImageView) holder.getView(R.id.img_period)).setVisibility(View.VISIBLE);
        } else {
            ((ImageView) holder.getView(R.id.img_period)).setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.itemClick(layoutPosition);
                }
            }
        });
    }

    public interface OnItemClickListener{
        void itemClick(int position);
    }
}
