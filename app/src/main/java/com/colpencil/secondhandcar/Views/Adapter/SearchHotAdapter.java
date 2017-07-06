package com.colpencil.secondhandcar.Views.Adapter;

import android.content.Context;

import com.colpencil.secondhandcar.Bean.Response.HotSearch;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.CommonAdapter;
import com.colpencil.secondhandcar.Tools.CommonViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */
public class SearchHotAdapter extends CommonAdapter<HotSearch> {

    public SearchHotAdapter(Context context, List<HotSearch> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, HotSearch item, int position) {
        helper.setText(R.id.text_hot, item.getKey_name());
    }
}
