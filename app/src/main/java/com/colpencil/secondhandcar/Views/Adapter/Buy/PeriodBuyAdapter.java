package com.colpencil.secondhandcar.Views.Adapter.Buy;

import android.content.Context;

import com.colpencil.secondhandcar.Bean.Response.PeriodBuy;
import com.colpencil.secondhandcar.Tools.CommonAdapter;
import com.colpencil.secondhandcar.Tools.CommonViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 * 分期购车支付适配器
 */
public class PeriodBuyAdapter extends CommonAdapter<PeriodBuy> {

    public PeriodBuyAdapter(Context context, List<PeriodBuy> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, PeriodBuy item, int position) {

    }
}
