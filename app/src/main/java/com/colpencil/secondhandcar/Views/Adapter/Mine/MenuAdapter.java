/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Bean.Response.PersonalGroom;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 *
 */
public class MenuAdapter extends SwipeMenuAdapter<MenuAdapter.DefaultViewHolder> {

    private List<PersonalGroom> collections;

    private OnItemClickListener mOnItemClickListener;

    private Context mContext;

    public MenuAdapter(Context context, List<PersonalGroom> collections) {
        this.collections = collections;
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return collections == null ? 0 : collections.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mine_collection, parent, false);
    }

    @Override
    public MenuAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MenuAdapter.DefaultViewHolder holder, int position) {
        if(collections.get(position).getNew_shelves() == 1){
            holder.iv_tag.setVisibility(View.VISIBLE);
        } else {
            holder.iv_tag.setVisibility(View.GONE);
        }
        Glide.with(mContext)
                .load(collections.get(position).getOriginal())
                .into(holder.iv_car);
        if(collections.get(position).getMarket_enable() == 1){
            holder.iv_beauty.setVisibility(View.GONE);
        } else {
            holder.iv_beauty.setVisibility(View.VISIBLE);
        }
        holder.tvTitle.setText(collections.get(position).getName());
        holder.tv_time.setText(StringUtils.formatDate(collections.get(position).getListed_time()));
        holder.tv_km.setText(collections.get(position).getMileage()+"万公里");
        holder.tv_address.setText(collections.get(position).getCity_name());
        holder.tv_price.setText(collections.get(position).getPrice()+"万");
        if(collections.get(position).getIs_urgent() == 1){
            holder.iv_sale.setVisibility(View.VISIBLE);
        } else {
            holder.iv_sale.setVisibility(View.GONE);
        }
        if(collections.get(position).getInstallment() == 1){
            holder.iv_period.setVisibility(View.VISIBLE);
        } else {
            holder.iv_period.setVisibility(View.GONE);
        }
    }

    class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_tag;
        ImageView iv_car;
        ImageView iv_beauty;
        TextView tvTitle;
        TextView tv_time;
        TextView tv_km;
        TextView tv_address;
        TextView tv_price;
        ImageView iv_sale;
        ImageView iv_period;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            iv_tag = (ImageView) itemView.findViewById(R.id.img_tag);
            iv_car = (ImageView) itemView.findViewById(R.id.img_car);
            iv_beauty = (ImageView) itemView.findViewById(R.id.img_beauty);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.text_age);
            tv_km = (TextView) itemView.findViewById(R.id.text_km);
            tv_address = (TextView) itemView.findViewById(R.id.text_address);
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
