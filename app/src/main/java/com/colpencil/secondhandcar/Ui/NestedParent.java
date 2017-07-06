package com.colpencil.secondhandcar.Ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/6/22.
 */
public class NestedParent extends ScrollView {
    public NestedParent(Context context) {
        super(context);
    }

    public NestedParent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedParent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY*2);
    }
}
