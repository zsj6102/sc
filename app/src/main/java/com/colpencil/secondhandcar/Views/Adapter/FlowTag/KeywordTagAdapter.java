package com.colpencil.secondhandcar.Views.Adapter.FlowTag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.WordType;
import com.colpencil.secondhandcar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/7.
 */
public class KeywordTagAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<WordType> mDataList;

    public KeywordTagAdapter(Context context){
        this.mContext = context;

        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TagViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_keyword_tag, null);
            holder = new TagViewHolder();
            holder.tv_tag = (TextView) convertView.findViewById(R.id.tv_tag);
            convertView.setTag(holder);
        } else {
            holder = (TagViewHolder) convertView.getTag();
        }
        holder.tv_tag.setText(mDataList.get(position).getKeyWord());
        return convertView;
    }

    /**
     * 添加数据
     * @param datas
     */
    public void onlyAddAll(List<WordType> datas) {
        mDataList.clear();
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 删除指定元素
     * @param position
     */
    public void clearPosition(int position) {
        mDataList.remove(position);
        notifyDataSetChanged();
    }

    public class TagViewHolder{
        TextView tv_tag;
    }

}
