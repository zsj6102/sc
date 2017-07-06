package com.colpencil.secondhandcar.Ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.colpencil.secondhandcar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */
public class BrandSideLetterBar extends View {

    private List<String> list;
    private int choose = -1;
    private Paint paint = new Paint();
    private boolean showBg = false;
    private OnLetterChangedListener onLetterChangedListener;
    private TextView overlay;

    public BrandSideLetterBar(Context context) {
        super(context);
    }

    public BrandSideLetterBar(Context context, AttributeSet attr){
        super(context, attr);
    }

    public BrandSideLetterBar(Context context, AttributeSet attr, int defStyle){
        super(context, attr, defStyle);
    }

    /**
     * 加载数据
     * @param list
     */
    public void setBarList(List<String> list){
        this.list = list;
    }

    /**
     * 设置炫富的TextView
     * @param overlay
     */
    public void setOverlay(TextView overlay){
        this.overlay = overlay;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(showBg){
            canvas.drawColor(Color.TRANSPARENT);
        }
        //画索引
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / list.size();
        for(int i = 0; i < list.size(); i++){
            paint.setTextSize(getResources().getDimension(R.dimen.side_letter_bar_letter_size));
            paint.setColor(getResources().getColor(R.color.gray));
            paint.setAntiAlias(true);
            //如果选中，颜色加深
            if(i == choose){
                paint.setColor(getResources().getColor(R.color.gray_deep));
                paint.setFakeBoldText(true); // 加粗
            }
            //计算相应字母的距离居中
            float xPos = width / 2 - paint.measureText(list.get(i)) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(list.get(i), xPos, yPos, paint);
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnLetterChangedListener listener = onLetterChangedListener;
        //相应高度的比例乘以字符数组长度就是我们要的字母
        final int c = (int) (y / getHeight() * list.size());

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBg = true;
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < list.size()) {
                        listener.onLetterChanged(list.get(c));
                        choose = c;
                        invalidate();
                        if (overlay != null){
                            overlay.setVisibility(VISIBLE);
                            overlay.setText(list.get(c));
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < list.size()) {
                        listener.onLetterChanged(list.get(c));
                        choose = c;
                        invalidate();
                        if (overlay != null){
                            overlay.setVisibility(VISIBLE);
                            overlay.setText(list.get(c));
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBg = false;
                choose = -1;
                invalidate();
                if (overlay != null){
                    overlay.setVisibility(GONE);
                }
                break;
        }
        return true;
    }

    /**
     * 设置监听
     * @param onLetterChangedListener
     */
    public void setOnLetterChangedListener(OnLetterChangedListener onLetterChangedListener) {
        this.onLetterChangedListener = onLetterChangedListener;
    }

    public interface OnLetterChangedListener {
        void onLetterChanged(String letter);
    }
}
