package com.wuziqiu.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;


public class DayTextView extends AppCompatTextView {

    public boolean isToday= false;
    private Paint paint = new Paint();

    public DayTextView(Context context) {
        this(context, null);
    }

    public DayTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl();
    }

    private void initControl() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(isToday){
            canvas.translate(getWidth() / 2, getHeight() / 2);
            canvas.drawCircle(0, 0, getWidth()<getHeight()?getWidth()/2:getHeight()/2, paint);
        }
    }
}
