package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Bean.Response.SubscribeGood;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.CommonAdapter;
import com.colpencil.secondhandcar.Tools.CommonViewHolder;
import com.colpencil.secondhandcar.Tools.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 * 我的订阅车辆信息适配器
 */
public class MineSubscribeCarAdapter extends CommonAdapter<SubscribeGood> {

    public MineSubscribeCarAdapter(Context context, List<SubscribeGood> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, SubscribeGood item, int position) {
        if(item.getNew_shelves() == 1){
            holder.getView(R.id.img_tag).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.img_tag).setVisibility(View.GONE);
        }
        Glide.with(mContext)
                .load(item.getOriginal())
                .into((ImageView) holder.getView(R.id.img_car));

        holder.setText(R.id.tv_name, item.getName());
        if(item.getIs_urgent() == 1){
            holder.getView(R.id.text_urgent).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.text_urgent).setVisibility(View.GONE);
        }
        if(item.getInstallment() == 1){
            holder.getView(R.id.text_period).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.text_period).setVisibility(View.GONE);
        }
        holder.setText(R.id.tv_ma, item.getPrice()+"万");
        holder.setText(R.id.text_year, StringUtils.formatYear(item.getListed_time()));
        holder.setText(R.id.tv_statue, item.getMileage()+"万公里");
    }
}
