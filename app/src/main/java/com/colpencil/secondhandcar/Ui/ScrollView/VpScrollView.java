package com.colpencil.secondhandcar.Ui.ScrollView;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/4/7.
 * listView获取焦点把ScrollView顶上去的解决办法
 */
public class VpScrollView extends ScrollView {

    public VpScrollView(Context context){
        super(context);
    }

    public VpScrollView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }
}
