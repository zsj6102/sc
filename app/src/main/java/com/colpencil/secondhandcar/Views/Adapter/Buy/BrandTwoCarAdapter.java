package com.colpencil.secondhandcar.Views.Adapter.Buy;

import android.content.Context;

import com.colpencil.secondhandcar.Bean.Response.BrandCar;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.CommonAdapter;
import com.colpencil.secondhandcar.Tools.CommonViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */
public class BrandTwoCarAdapter extends CommonAdapter<BrandCar> {

    public BrandTwoCarAdapter(Context context, List<BrandCar> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, BrandCar item, int position) {
        helper.setText(R.id.text_classify_name, item.getCat_name());
    }
}
