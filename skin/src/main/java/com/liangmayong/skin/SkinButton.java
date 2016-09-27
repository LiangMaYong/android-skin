package com.liangmayong.skin;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by liangmayong on 2016/9/11.
 */
public class SkinButton extends SkinBaseButton {

    private int COVER_ALPHA = 48;
    private Paint mPressedPaint;
    private int mPressedColor;


    public SkinButton(Context context) {
        super(context);
        initSkinButton(context, null);
    }


    public SkinButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSkinButton(context, attrs);
    }


    public SkinButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSkinButton(context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SkinButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initSkinButton(context, attrs);
    }


    protected void initSkinButton(Context context, AttributeSet attrs) {
        mPressedColor = 0x9c000000;

        mPressedPaint = new Paint();
        mPressedPaint.setAntiAlias(true);
        mPressedPaint.setStyle(Paint.Style.FILL);
        mPressedPaint.setColor(mPressedColor);
        mPressedPaint.setAlpha(0);
        mPressedPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShapeType == 0) {
            mPressedPaint.setStyle(Paint.Style.FILL);
            mBackgroundPaint.setStrokeWidth(0);
            canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2.1038f,
                    mPressedPaint);
        } else if (mShapeType == SHAPE_TYPE_STROKE) {
            mPressedPaint.setStyle(Paint.Style.STROKE);
            mPressedPaint.setStrokeJoin(Paint.Join.MITER);
            mPressedPaint.setStrokeWidth(mStrokeWidth);
            canvas.drawPath(getPath(0, 0, mWidth, mHeight), mPressedPaint);
        } else {
            mPressedPaint.setStyle(Paint.Style.FILL);
            mBackgroundPaint.setStrokeWidth(0);
            RectF rectF = new RectF();
            rectF.set(0, 0, mWidth, mHeight);
            canvas.drawRoundRect(rectF, mRadius, mRadius, mPressedPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPressedPaint.setAlpha(COVER_ALPHA);
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mPressedPaint.setAlpha(0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }


    public int getPressedColor() {
        return mPressedColor;
    }


    /**
     * Set the pressed color.
     *
     * @param pressedColor pressed color
     */
    public void setPressedColor(int pressedColor) {
        mPressedPaint.setColor(mPressedColor);
        invalidate();
    }
}
