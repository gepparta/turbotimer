
package com.awg.turbotimer;
/*
 * Klassse wird im moment nicht verwendet
 * vlt. später
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

public class Timer extends View {
    private String mDigitString;
    private Paint mDigitPaint;
    private GradientDrawable mBGGrad;
    private float mWidth;
    private float mHeight;
    private float mDigitX;
    private float mDigitY;

    public Timer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public Timer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public Timer(Context context) {
        super(context);
        initialize();
    }

    private void initialize()
    {
        mBGGrad = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {
                        0xFF000000, 0xFFAAAAAA, 0xFF000000
                });

        mDigitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDigitPaint.setColor(Color.WHITE);
        mDigitPaint.setTextSize(100);
        mDigitPaint.setTextAlign(Align.CENTER);
        mDigitString = "00";
    }

    /*
     * (non-Javadoc)
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBGGrad.draw(canvas);
        if (null != mDigitString)
            canvas.drawText(mDigitString, mDigitX, mDigitY, mDigitPaint);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View#onSizeChanged(int, int, int, int)
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mBGGrad.setBounds(0, 0, w, h);
        mDigitPaint.setTextSize(h);

        mDigitX = mWidth / 2;
        setDigitYValues();
    }

    public void setCurrentDigit(String digit)
    {

        mDigitString = digit;

        setDigitYValues();
        invalidate();
    }

    private void setDigitYValues()
    {
        if (null != mDigitString)
        {
            Rect bounds = new Rect();
            mDigitPaint.getTextBounds(mDigitString, 0, mDigitString.length(), bounds);

            int textHeight = Math.abs(bounds.height());

            mDigitY = mHeight - ((mHeight - textHeight) / 2);
        }
    }

}
