package com.colpencil.secondhandcar.Views.Adapter.Buy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.InsList;
import com.colpencil.secondhandcar.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 * 分期购车适配器（支付）
 */
public class PeriodBuyCarAdapter extends BaseAdapter {

    Activity context;
    List<InsList> mDatas;
    private int temp = -1;

    public PeriodBuyCarAdapter(Activity context, List<InsList> mDatas) {
        super();
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_period_buy_car, parent, false);
            holder.tv_price = (TextView) convertView.findViewById(R.id.text_price);
            holder.tv_number = (TextView) convertView.findViewById(R.id.text_number);
            holder.cb_period = (CheckBox) convertView.findViewById(R.id.cb_period);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_price.setText(mDatas.get(position).getIns_price()+"元");
        holder.tv_number.setText(mDatas.get(position).getInstallment());
        holder.cb_period.setVisibility(View.VISIBLE);
        holder.cb_period.setId(position);
        holder.cb_period.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && temp != position){
                    if(temp != -1){
                        CheckBox checkBox = (CheckBox) context.findViewById(temp);
                        if(checkBox != null){
                           checkBox.setChecked(false);
                        }
                    }
                    temp = buttonView.getId();
                } else {
                    temp = -1;
                }
            }
        });
        if(position == temp){
            holder.cb_period.setChecked(true);
        } else {
            holder.cb_period.setChecked(false);
        }
        return convertView;
    }

    public int getSelection(){
        return temp;
    }

    public class ViewHolder{
        TextView tv_price;
        TextView tv_number;
        CheckBox cb_period;
    }
}
