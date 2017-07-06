package com.colpencil.secondhandcar.Views.Adapter.Home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Bean.Response.KeyWord;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.CommonAdapter;
import com.colpencil.secondhandcar.Tools.CommonViewHolder;

import java.util.List;


/**
 * Created by Administrator on 2017/3/23.
 * 首页参数选择
 */

public class HomeParameterAdapter extends CommonAdapter<KeyWord> {

//    private OnRvItemClickListener itemClickListener;

    public HomeParameterAdapter(Context context, List<KeyWord> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder viewHolder, KeyWord item, int position) {
        viewHolder.setText(R.id.text_price, item.getKey_name());
        ImageView imageView = viewHolder.getView(R.id.img_car_tag);
        if(item.getType() == 1){
            imageView.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(item.getPic())
                    .into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }
//        if(position == mDatas.size() - 1){
//            viewHolder.setTextColor(R.id.text_price, mContext.getResources().getColor(R.color.text_red));
//        } else {
//            viewHolder.setTextColor(R.id.text_price, mContext.getResources().getColor(R.color.text_dark99));
//        }
//        viewHolder.set(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(itemClickListener != null){
//                    itemClickListener.onItemClick(viewHolder.getItemView(), position, item);
//                }
//            }
//        });
    }

//    @Override
//    protected void onBindData(final EasyRVHolder viewHolder, final int position, final KeyWord item) {
//        viewHolder.setText(R.id.text_price, item.getKey_name());
//        ImageView imageView = viewHolder.getView(R.id.img_car_tag);
//        if(item.getType() == 1){
//            imageView.setVisibility(View.VISIBLE);
//            Glide.with(mContext)
//                    .load(item.getPic())
//                    .into(imageView);
//        } else {
//            imageView.setVisibility(View.GONE);
//        }
//        if(position == mList.size() - 1){
//            viewHolder.setTextColor(R.id.text_price, mContext.getResources().getColor(R.color.text_red));
//        } else {
//            viewHolder.setTextColor(R.id.text_price, mContext.getResources().getColor(R.color.text_dark99));
//        }
//        viewHolder.setOnItemViewClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(itemClickListener != null){
//                    itemClickListener.onItemClick(viewHolder.getItemView(), position, item);
//                }
//            }
//        });
//    }

//    public void setOnitemclicklistener(OnRvItemClickListener itemClickListener){
//        this.itemClickListener = itemClickListener;
//    }
}
