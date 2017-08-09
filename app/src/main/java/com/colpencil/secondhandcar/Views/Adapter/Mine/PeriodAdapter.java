package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.Repayment;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.R;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.List;


public class PeriodAdapter extends RecyclerView.Adapter<PeriodAdapter.MyViewHolder> {
    private Context mContext;
    private List<Repayment> list;

    public PeriodAdapter(Context context, List<Repayment> list) {
        this.mContext = context;
        this.list = list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_repayment, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.text_name.setText(list.get(position).getName());
        holder.text_number.setText(list.get(position).getIns_num());
        if (list.get(position).getDays() > 0) {
            holder.text_day.setText("还剩" + list.get(position).getDays() + "天");
        } else if (list.get(position).getDays() == 0) {
            holder.text_day.setText("今日还款");
        } else {
            holder.text_day.setText("欠款" + Math.abs(list.get(position).getDays()) + "天");
        }
        if (list.get(position).isChecked()) {
            holder.ivCheck.setImageResource(R.mipmap.choose_check);
        } else {
            holder.ivCheck.setImageResource(R.mipmap.range);
        }
        holder.ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) == list.get(position)) {
                        if (list.get(i).isChecked()) {
                            list.get(i).setChecked(false);
                        } else {
                            list.get(i).setChecked(true);
                        }
                    } else {
                        list.get(i).setChecked(false);
                    }
                }
                RxMsg msg = new RxMsg();
                RxBus.get().post("rxMsg", msg);
                notifyDataSetChanged();
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) == list.get(position)) {
                        if (list.get(i).isChecked()) {
                            list.get(i).setChecked(false);
                        } else {
                            list.get(i).setChecked(true);
                        }
                    } else {
                        list.get(i).setChecked(false);
                    }
                }
                RxMsg msg = new RxMsg();
                RxBus.get().post("rxMsg", msg);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCheck;
        TextView text_name;
        TextView text_number;
        TextView text_day;
        LinearLayout layout;
        public MyViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout)itemView.findViewById(R.id.layout);
            ivCheck = (ImageView) itemView.findViewById(R.id.checkbox);
            text_name = (TextView) itemView.findViewById(R.id.text_name);
            text_number = (TextView) itemView.findViewById(R.id.text_number);
            text_day = (TextView) itemView.findViewById(R.id.text_day);
        }
    }
}
