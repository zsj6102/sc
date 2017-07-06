package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colpencil.secondhandcar.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 * 我的订阅标签适配器
 */
public class MineSubscribeTagAdapter extends RecyclerView.Adapter<MineSubscribeTagAdapter.ViewHolder> {

    private Context context;
    private List<String> list;

    public MineSubscribeTagAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_child_subscribe_tag, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_tag.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_tag;

        public ViewHolder(View itemView){
            super(itemView);
            tv_tag = (TextView) itemView.findViewById(R.id.text_tag);
        }
    }
}
