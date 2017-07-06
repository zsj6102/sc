package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Bean.Response.Order;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */
public class StorePeriodAdapter extends SuperAdapter<Order> {

    public StorePeriodAdapter(Context context, List<Order> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Order item) {
        holder.setText(R.id.text_number, item.getSn()); //订单编号
        holder.setText(R.id.text_date, StringUtils.formatTime(item.getCreate_time())); //下单时间
        Glide.with(mContext)
                .load(item.getPic())
                .into((ImageView) holder.getView(R.id.img_car));
//        if(item.getInstallment() == 1){
//            holder.getView(R.id.img_sale).setVisibility(View.VISIBLE);
//        } else {
//            holder.getView(R.id.img_sale).setVisibility(View.GONE);
//        }
        holder.setText(R.id.tv_name, item.getName());
        holder.setText(R.id.tv_ma, item.getPrice()+"万");
        holder.setText(R.id.text_year, StringUtils.formatYear(item.getGoods_time()));
        holder.setText(R.id.tv_statue, item.getMileage()+"万公里");
        holder.setText(R.id.text_address, item.getCity_name());
        holder.setText(R.id.text_money, item.getOrder_amount()+"元");
        //支付状态
        if(item.getPay_status() == 0){
            holder.setText(R.id.text_pay_state, "未付款");
        } else if(item.getPay_status() == 1){
            holder.setText(R.id.text_pay_state, "已付款");
        } else if(item.getPay_status() == 2){
            holder.setText(R.id.text_pay_state, "已退款");
        }
        //订单状态
        switch (item.getStatus()) {
            case 0: //待付款
                holder.setText(R.id.text_state, "待付款");
                holder.getView(R.id.rl_state).setVisibility(View.GONE);
                holder.getView(R.id.rl_statue).setVisibility(View.GONE);
                break;
            case 1://待看车
                holder.setText(R.id.text_state, "待看车");
                holder.getView(R.id.rl_state).setVisibility(View.GONE);
                holder.getView(R.id.rl_statue).setVisibility(View.GONE);
                break;
            case 2://商谈中
                holder.setText(R.id.text_state, "商谈中");
                holder.getView(R.id.rl_statue).setVisibility(View.GONE);
                holder.getView(R.id.rl_state).setVisibility(View.GONE);
                break;
            case 3://已完成
                holder.setText(R.id.text_state, "已完成");
                holder.getView(R.id.rl_state).setVisibility(View.GONE);
                holder.getView(R.id.rl_statue).setVisibility(View.GONE);
                break;
            case 4://交易失败
                holder.setText(R.id.text_state, "交易失败");
                holder.getView(R.id.rl_state).setVisibility(View.GONE);
                holder.getView(R.id.rl_statue).setVisibility(View.GONE);
                break;
            case 5://取消订单
                holder.setText(R.id.text_state, "取消订单");
                holder.getView(R.id.rl_state).setVisibility(View.GONE);
                holder.getView(R.id.rl_statue).setVisibility(View.GONE);
                break;
            case 6://转分期
                holder.setText(R.id.text_state, "转分期");
                holder.getView(R.id.rl_state).setVisibility(View.GONE);
                holder.getView(R.id.rl_statue).setVisibility(View.GONE);
                break;
        }
    }
}
