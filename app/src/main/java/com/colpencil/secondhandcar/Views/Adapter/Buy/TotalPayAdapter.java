package com.colpencil.secondhandcar.Views.Adapter.Buy;

import android.content.Context;

import com.colpencil.secondhandcar.Bean.Response.TotalPay;
import com.colpencil.secondhandcar.Tools.CommonAdapter;
import com.colpencil.secondhandcar.Tools.CommonViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 * 累计支付详情适配器
 */
public class TotalPayAdapter extends CommonAdapter<TotalPay> {

    public TotalPayAdapter(Context context, List<TotalPay> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, TotalPay item, int position) {

    }
}
