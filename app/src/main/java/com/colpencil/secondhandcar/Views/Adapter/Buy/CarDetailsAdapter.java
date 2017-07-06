package com.colpencil.secondhandcar.Views.Adapter.Buy;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Bean.Response.PicList;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.CommonAdapter;
import com.colpencil.secondhandcar.Tools.CommonViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 * 车辆详情适配器
 */
public class CarDetailsAdapter extends CommonAdapter<PicList> {

    private View.OnClickListener clickListener;

    public CarDetailsAdapter(Context context, List<PicList> list, int layoutId){
        super(context, list, layoutId);
    }

    public void setListener(View.OnClickListener listener){
        this.clickListener = listener;
    }

    @Override
    public void convert(CommonViewHolder holder, PicList item, int position) {
        holder.setText(R.id.text_info, item.getRemark());
        ImageView imageView = holder.getView(R.id.img_car);
        if(item.getPic_type() == 0){
            holder.setText(R.id.text_position, "正侧");
        } else if(item.getPic_type() == 1){
            holder.setText(R.id.text_position, "车门");
        } else if(item.getPic_type() == 2){
            holder.setText(R.id.text_position, "后排");
        } else if(item.getPic_type() == 3){
            holder.setText(R.id.text_position, "发动机舱");
        } else if(item.getPic_type() == 4){
            holder.setText(R.id.text_position, "正前");
        } else if(item.getPic_type() == 5){
            holder.setText(R.id.text_position, "前排");
        } else if(item.getPic_type() == 6){
            holder.setText(R.id.text_position, "中控");
        } else if(item.getPic_type() == 7){
            holder.setText(R.id.text_position, "其他");
        }
        if(item.getPic_type() == 7){
            Glide.with(mContext)
                    .load(item.getOther_pic().get(0))
                    .into(imageView);
        } else {
            Glide.with(mContext)
                    .load(item.getPic())
                    .into(imageView);
        }
        imageView.setOnClickListener(clickListener);
    }
}
