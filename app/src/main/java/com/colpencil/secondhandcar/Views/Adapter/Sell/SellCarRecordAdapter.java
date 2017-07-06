package com.colpencil.secondhandcar.Views.Adapter.Sell;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Bean.Response.SellCarRecord;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.colpencil.secondhandcar.Views.Activities.Buy.CarDetailsActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.MineBespokeActivity;
import com.colpencil.secondhandcar.Views.Activities.Sell.ChangeGoodActivity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 * 修改卖车列表适配器
 */
public class SellCarRecordAdapter extends SuperAdapter<SellCarRecord> {

    private int type = 0;
    private ClickListener upClickListener;
    private ClickListener downClickListener;
    private Intent intent;

    public SellCarRecordAdapter(Context context, List<SellCarRecord> list, int layoutId, int type){
        super(context, list, layoutId);
        this.type = type;
    }

    public void setUpListener(ClickListener listener){
        this.upClickListener = listener;
    }

    public void setDownListener(ClickListener listener){
        this.downClickListener = listener;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, final SellCarRecord item) {
        if(item.getNew_shelves() == 1){
            holder.getView(R.id.img_tag).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.img_tag).setVisibility(View.GONE);
        }
        Glide.with(mContext)
                .load(item.getOriginal())
                .into((ImageView)holder.getView(R.id.img_car));
        holder.setText(R.id.tv_name, item.getName());
        holder.setText(R.id.tv_ma, item.getPrice()+"万");
        holder.setText(R.id.text_year, StringUtils.formatYear(item.getListed_time()));
        holder.setText(R.id.tv_statue, item.getMileage()+"万公里");
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
        LinearLayout layout = holder.getView(R.id.layout_info);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(mContext, CarDetailsActivity.class);
                intent.putExtra("carId", item.getGoods_id());
                mContext.startActivity(intent);
            }
        });
        //是否可以修改
        if(type == 0){
            holder.getView(R.id.layout_undercarriage).setVisibility(View.VISIBLE);
            holder.getView(R.id.layout_change).setVisibility(View.GONE);
            holder.getView(R.id.text_look).setVisibility(View.GONE);
            holder.getView(R.id.layout_undercarriage).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(downClickListener != null){
                        downClickListener.click(layoutPosition);
                    }
                }
            });
        } else if(type == 1){
            holder.getView(R.id.layout_change).setVisibility(View.VISIBLE);
            holder.getView(R.id.layout_undercarriage).setVisibility(View.GONE);
            holder.getView(R.id.text_look).setVisibility(View.GONE);
            holder.getView(R.id.layout_up).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(upClickListener != null){
                        upClickListener.click(layoutPosition);
                    }
                }
            });
            //修改商品
            holder.getView(R.id.layout_change).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    RxBusMsg msg = new RxBusMsg();
//                    msg.setCarType(1);
//                    msg.setChange(1);
//                    msg.setCarId(item.getGoods_id());
//                    RxBus.get().post("rxBusMsg", msg);
//                    ColpencilFrame.getInstance().finishActivity(SellCarRecordActivity.class);
                    intent = new Intent(mContext, ChangeGoodActivity.class);
                    intent.putExtra("goods_id", item.getGoods_id());
                    mContext.startActivity(intent);
                }
            });
        } else if(type == 2){
            holder.getView(R.id.layout_change).setVisibility(View.GONE);
            holder.getView(R.id.layout_undercarriage).setVisibility(View.GONE);
            holder.getView(R.id.text_look).setVisibility(View.GONE);
            holder.getView(R.id.view).setVisibility(View.GONE);
            holder.getView(R.id.text_look).setVisibility(View.GONE);
            holder.getView(R.id.rl).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent  = new Intent(mContext, MineBespokeActivity.class);
                    intent.putExtra("position", 4);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public interface ClickListener{
        void click(int position);
    }
}
