package com.colpencil.secondhandcar.Views.Adapter.ChooseCar;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Bean.Response.CarResult;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/7.
 */
public class KeywordResultAdapter extends SuperAdapter<CarResult> {

    private ClickListener clickListener;
    private OnItemClickListener onItemClickListener;

    public KeywordResultAdapter(Context context, List<CarResult> list, int layoutId){
        super(context, list, layoutId);
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, CarResult item) {
        if(item.getNew_shelves() == 1){
            holder.getView(R.id.img_tag).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.img_tag).setVisibility(View.GONE);
        }
        Glide.with(mContext)
                .load(item.getOriginal())
                .into((ImageView) holder.getView(R.id.img_car));
        holder.setText(R.id.tv_name, item.getName());
        holder.setText(R.id.text_age, StringUtils.formatYear(item.getListed_time()));
        holder.setText(R.id.text_km, StringUtils.doubleFormat(item.getMileage())+"万公里");
        holder.setText(R.id.tv_ma, StringUtils.doubleFormat(item.getPrice())+"万");
        if(item.getIs_urgent() == 1){
            holder.getView(R.id.tv_statue).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.tv_statue).setVisibility(View.GONE);
        }
        if(item.getInstallment() == 1){
            holder.getView(R.id.img_period).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.img_period).setVisibility(View.GONE);
        }
        holder.getView(R.id.img_love).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null){
                    clickListener.onClick(layoutPosition);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.itemClick(layoutPosition);
                }
            }
        });
        if(layoutPosition == mData.size() - 1){
            holder.getView(R.id.text_no).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.text_no).setVisibility(View.GONE);
        }
    }

    public interface ClickListener{
        void onClick(int position);
    }

    public interface OnItemClickListener{
        void itemClick(int position);
    }
}
