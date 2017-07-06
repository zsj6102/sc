package com.colpencil.secondhandcar.Views.Adapter.Buy;

import android.content.Context;

import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.CommonAdapter;
import com.colpencil.secondhandcar.Tools.CommonViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/3/27.
 */
public class BuyCarSortAdapter extends CommonAdapter<String> {

    private int currentPosition = 0;

    public BuyCarSortAdapter(Context context, List<String> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, String item, int position) {
        helper.setText(R.id.text_, item);
        //选中
        if(currentPosition == position){
            helper.setTextColor(R.id.text_, mContext.getResources().getColor(R.color.title_color));
            if(position == 0){
                helper.setImageById(R.id.img, R.mipmap.sort_check);
            } else if(position == 1){
                helper.setImageById(R.id.img, R.mipmap.high_price_check);
            } else if(position == 2){
                helper.setImageById(R.id.img, R.mipmap.low_price_check);
            } else if(position == 3){
                helper.setImageById(R.id.img, R.mipmap.release_check);
            } else if(position == 4){
                helper.setImageById(R.id.img, R.mipmap.course_check);
            } else {
                helper.setImageById(R.id.img, R.mipmap.car_age_check);
            }
        }  else { //未选中
            helper.setTextColor(R.id.text_, mContext.getResources().getColor(R.color.text_dark33));
            if(position == 0){
                helper.setImageById(R.id.img, R.mipmap.sort);
            } else if(position == 1){
                helper.setImageById(R.id.img, R.mipmap.high_price);
            } else if(position == 2){
                helper.setImageById(R.id.img, R.mipmap.low_price);
            } else if(position == 3){
                helper.setImageById(R.id.img, R.mipmap.release);
            } else if(position == 4){
                helper.setImageById(R.id.img, R.mipmap.course);
            } else {
                helper.setImageById(R.id.img, R.mipmap.car_age);
            }
        }
    }

    public void setPosition(int position){
        this.currentPosition = position;
        notifyDataSetChanged();
    }
}
