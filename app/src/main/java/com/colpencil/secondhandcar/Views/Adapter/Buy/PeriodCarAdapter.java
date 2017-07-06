package com.colpencil.secondhandcar.Views.Adapter.Buy;

import android.content.Context;

import com.colpencil.secondhandcar.Bean.Response.InstallmentCar;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.CommonAdapter;
import com.colpencil.secondhandcar.Tools.CommonViewHolder;
import com.colpencil.secondhandcar.Tools.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 * 分期购车适配器（预约）
 */
public class PeriodCarAdapter extends CommonAdapter<InstallmentCar> {

    public PeriodCarAdapter(Context context, List<InstallmentCar> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, InstallmentCar item, int position) {
        holder.setText(R.id.text_price, StringUtils.doubleFormat(item.getIns_price())+"元");
        holder.setText(R.id.text_number,"月供("+ item.getIns_num()+")期");
    }
}
