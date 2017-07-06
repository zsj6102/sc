package com.colpencil.secondhandcar.Views.Adapter.Sell;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.colpencil.secondhandcar.Bean.Response.Installment;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.CommonAdapter;
import com.colpencil.secondhandcar.Tools.CommonViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */
public class SellPeriodNumAdapter extends CommonAdapter<Installment> {

    private CheckBoxListener checkBoxListener;

    public void setCheckBoxListener(CheckBoxListener listener){
        this.checkBoxListener = listener;
    }

    public SellPeriodNumAdapter(Context context, List<Installment> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, Installment item, final int position) {
        holder.setText(R.id.text_period, item.getName());
        ((CheckBox)holder.getView(R.id.cb_period)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxListener != null && isChecked){
                    checkBoxListener.checked(position);
                }
            }
        });
        ((CheckBox) holder.getView(R.id.cb_period)).setChecked(item.isIxChecked());
    }

    public interface CheckBoxListener{
        void checked(int position);
    }
}
