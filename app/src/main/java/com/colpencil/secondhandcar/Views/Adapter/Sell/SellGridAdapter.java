package com.colpencil.secondhandcar.Views.Adapter.Sell;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.CommonAdapter;
import com.colpencil.secondhandcar.Tools.CommonViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */
public class SellGridAdapter extends CommonAdapter<String> {

    private OnClickListener clickListener;

    public void setListener(OnClickListener clickListener){
        this.clickListener = clickListener;
    }

    public SellGridAdapter(Context context, List<String> list, int layoutId){
       super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, String item, final int position) {
        ImageView imageView = holder.getView(R.id.iv_p);
        Glide.with(mContext)
                .load(item)
                .into(imageView);
        holder.getView(R.id.iv_p_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null){
                    clickListener.click(position);
                }
            }
        });
    }

    public interface OnClickListener{
        void click(int position);
    }
}
