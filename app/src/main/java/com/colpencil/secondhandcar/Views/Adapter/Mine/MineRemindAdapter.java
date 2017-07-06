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
 * Created by Administrator on 2017/4/12.
 * 我的降价提醒适配器
 */
public class MineRemindAdapter extends SwipeMenuAdapter<MineRemindAdapter.DefaultViewHolder> {

    private List<ReducePriceCar> mineRemind;

    private OnItemClickListener mOnItemClickListener; //点击

    private Context mContext;

    public MineRemindAdapter(Context context, List<ReducePriceCar> list){
        this.mineRemind = list;
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
        holder.tv_date.setText(StringUtils.formatDate(mineRemind.get(position).getAdd_time())+"添加");
        holder.tv_reduce.setText(mineRemind.get(position).getReduce_price()+"元");
        if(mineRemind.get(position).getNew_shelves() == 1){
            holder.iv_tag.setVisibility(View.VISIBLE);
        } else {
            holder.iv_tag.setVisibility(View.GONE);
        }
        Glide.with(mContext)
                .load(mineRemind.get(position).getPic())
                .into(holder.iv_car);
        holder.tvTitle.setText(mineRemind.get(position).getName());
        holder.tv_time.setText(StringUtils.formatDate(mineRemind.get(position).getListed_time()));
        holder.tv_km.setText(mineRemind.get(position).getMileage()+"万公里");
        holder.tv_price.setText(mineRemind.get(position).getPrice()+"万");
        if(mineRemind.get(position).getIs_urgent() == 1){
            holder.iv_sale.setVisibility(View.VISIBLE);
        } else {
            holder.iv_sale.setVisibility(View.GONE);
        }
        if(mineRemind.get(position).getInstallment() == 1){
            holder.iv_period.setVisibility(View.VISIBLE);
        } else {
            holder.iv_period.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mineRemind == null ? 0 : mineRemind.size();
    }

    class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_date;
        TextView tv_reduce;
        ImageView iv_tag;
        ImageView iv_car;
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
