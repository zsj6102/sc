package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Bean.Response.ReducePriceCar;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */
public class SoldRemindAdapter extends SwipeMenuAdapter<SoldRemindAdapter.DefaultViewHolder> {

    private List<ReducePriceCar> reduces;

    private OnItemClickListener mOnItemClickListener;

    private Context mContext;

    public SoldRemindAdapter(Context context, List<ReducePriceCar> list){
        this.reduces = list;
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_depreciate_remind, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {
        holder.tv_date.setText(StringUtils.formatDate(reduces.get(position).getAdd_time())+"添加");
        holder.tv_reduce.setText(reduces.get(position).getReduce_price()+"元");
        if(reduces.get(position).getNew_shelves() == 1){
            holder.iv_tag.setVisibility(View.VISIBLE);
        } else {
            holder.iv_tag.setVisibility(View.GONE);
        }
        holder.iv_beauty.setVisibility(View.VISIBLE);
        Glide.with(mContext)
                .load(reduces.get(position).getPic())
                .into(holder.iv_car);
        holder.tvTitle.setText(reduces.get(position).getName());
        holder.tv_time.setText(StringUtils.formatDate(reduces.get(position).getListed_time()));
        holder.tv_km.setText(reduces.get(position).getMileage()+"万公里");
        holder.tv_price.setText(reduces.get(position).getPrice()+"万");
        if(reduces.get(position).getIs_urgent() == 1){
            holder.iv_sale.setVisibility(View.VISIBLE);
        } else {
            holder.iv_sale.setVisibility(View.GONE);
        }
        if(reduces.get(position).getInstallment() == 1){
            holder.iv_period.setVisibility(View.VISIBLE);
        } else {
            holder.iv_period.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return reduces == null ? 0 : reduces.size();
    }

    class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_date;
        TextView tv_reduce;
        ImageView iv_tag;
        ImageView iv_car;
        ImageView iv_beauty;
        TextView tvTitle;
        TextView tv_time;
        TextView tv_km;
        TextView tv_price;
        ImageView iv_sale;
        ImageView iv_period;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_date = (TextView) itemView.findViewById(R.id.text_time);
            tv_reduce = (TextView) itemView.findViewById(R.id.text_depreciate);
            iv_tag = (ImageView) itemView.findViewById(R.id.img_tag);
            iv_car = (ImageView) itemView.findViewById(R.id.img_car);
            iv_beauty = (ImageView) itemView.findViewById(R.id.img_beauty);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.text_age);
            tv_km = (TextView) itemView.findViewById(R.id.text_km);
            tv_price = (TextView) itemView.findViewById(R.id.tv_ma);
            iv_sale = (ImageView) itemView.findViewById(R.id.tv_statue);
            iv_period = (ImageView) itemView.findViewById(R.id.tv_period);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
