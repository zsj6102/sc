package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;

import com.colpencil.secondhandcar.Bean.Response.SystemMsg;
import com.colpencil.secondhandcar.Tools.CommonAdapter;
import com.colpencil.secondhandcar.Tools.CommonViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.\
 * 系统消息适配器
 */
public class MsgSystemAdapter extends CommonAdapter<SystemMsg> {

    public MsgSystemAdapter(Context context, List<SystemMsg> list, int layoutId){
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, SystemMsg item, int position) {

    }
}
