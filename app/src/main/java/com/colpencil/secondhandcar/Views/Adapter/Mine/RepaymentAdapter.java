package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.colpencil.secondhandcar.Bean.Response.Repayment;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 */
public class RepaymentAdapter extends SuperAdapter<Repayment> {
    private List<Repayment> list;
    public RepaymentAdapter(Context context, List<Repayment> list, int layoutId){
        super(context, list, layoutId);
        this.list = list;
    }

    @Override
    public void onBind(final SuperViewHolder holder, int viewType, int layoutPosition, final Repayment item) {
        holder.setText(R.id.text_name, item.getName());
        holder.setText(R.id.text_number, item.getIns_num());
        if(item.getDays() > 0){
            holder.setText(R.id.text_day, "还剩"+item.getDays()+"天");
        } else if(item.getDays() == 0){
            holder.setText(R.id.text_day, "今日还款");
        } else {
            holder.setText(R.id.text_day, "欠款"+Math.abs(item.getDays())+"天");
        }
        holder.setText(R.id.text_price, StringUtils.doubleFormat(item.getPrice())+"元");
        ((CheckBox)holder.getView(R.id.checkbox)).setChecked(item.isChecked());
        holder.getView(R.id.layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxMsg msg = new RxMsg();
                int position = 0;
                for(int i = 0; i < list.size();i++){
                    if(item ==list.get(i)){
                        position = i;
                    }
                }
                msg.setTypeId(position);
                RxBus.get().post("rxMsg", msg);
//                notifyDataSetChanged();
            }
        });
    }
}
