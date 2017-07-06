package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;

import com.colpencil.secondhandcar.Bean.Response.FriendRecommend;
import com.colpencil.secondhandcar.Tools.CommonAdapter;
import com.colpencil.secondhandcar.Tools.CommonViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/7.
 */
public class PersonalGroomAdapter extends CommonAdapter<FriendRecommend> {

    public PersonalGroomAdapter(Context context, List<FriendRecommend> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, FriendRecommend item, int position) {

    }
}
