package com.liangmayong.skin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * SkinRippleButton
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class SkinRippleButton extends SkinButton {

    private int mRoundRadius;
    private int mRippleColor;
    private int mRippleDuration;
    private int mRippleRadius;
    private float pointX, pointY;
    private boolean isDown = false;

    private Paint mRipplePaint;
    private RectF mRectF;
    private Path mPath;
    private Timer mTimer;
    private TimerTask mTask;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_DRAW_COMPLETE) {
                invalidate();
            }
        }
    };

    private int mRippleAlpha;
    // private final static int HALF_ALPHA = 127;
    private final static int RIPPLR_ALPHA = 47;
    private final static int MSG_DRAW_COMPLETE = 101;

    public SkinRippleButton(Context context) {
        super(context);
    }

    public SkinRippleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        init(context);
    }

    public SkinRippleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        init(context);
    }

    protected void init(final Context context) {
        mRippleColor = 0xffeeeeee;
        mRippleAlpha = RIPPLR_ALPHA;
        mRippleDuration = 800;
        mRoundRadius = dip2px(getContext(), 2);
        mRipplePaint = new Paint();
        mRipplePaint.setColor(mRippleColor);
        mRipplePaint.setAlpha(mRippleAlpha);
        mRipplePaint.setStyle(Paint.Style.FILL);
        mRipplePaint.setAntiAlias(true);
        mPath = new Path();
        mRectF = new RectF();
        pointY = pointX = -1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode()) {
            return;
        }
        if (mRipplePaint == null) {
            return;
        }
        if (isDown) {
            drawFillCircle(canvas);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            pointX = event.getX();
            pointY = event.getY();
            isDown = true;
            onStartDrawRipple();
        } else {
            isDown = false;
        }
        return super.onTouchEvent(event);
    }

    /**
     * Draw ripple effect
     */
    private void drawFillCircle(Canvas canvas) {
        if (canvas != null && pointX >= 0 && pointY >= 0) {
            int rbX = canvas.getWidth();
            int rbY = canvas.getHeight();
            float x_max = Math.max(pointX, Math.abs(rbX - pointX));
            float y_max = Math.max(pointY, Math.abs(rbY - pointY));
            float longDis = (float) Math.sqrt(x_max * x_max + y_max * y_max);
            if (mRippleRadius > longDis) {
                onCompleteDrawRipple();
                return;
            }
            final float drawSpeed = longDis / mRippleDuration * 35;
            mRippleRadius += drawSpeed;

            canvas.save();
            // canvas.translate(0, 0);//保持原点
            mPath.reset();
            canvas.clipPath(mPath);
            if (mShapeType == 0) {
                mPath.addCircle(rbX / 2, rbY / 2, mWidth / 2, Path.Direction.CCW);
            } else {
                mRectF.set(0, 0, mWidth, mHeight);
                mPath.addRoundRect(mRectF, mRoundRadius, mRoundRadius, Path.Direction.CCW);
            }
            canvas.clipPath(mPath, Region.Op.REPLACE);
            canvas.drawCircle(pointX, pointY, mRippleRadius, mRipplePaint);
            canvas.restore();
        }
    }

    /**
     * Start draw ripple effect
     */
    private void onStartDrawRipple() {
        onCompleteDrawRipple();
        mTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(MSG_DRAW_COMPLETE);
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask, 0, 30);
    }

    /**
     * Stop draw ripple effect
     */
    private void onCompleteDrawRipple() {
        mHandler.removeMessages(MSG_DRAW_COMPLETE);
        if (mTimer != null) {
            if (mTask != null) {
                mTask.cancel();
            }
            mTimer.cancel();
        }
        mRippleRadius = 0;
    }

    public int getRoundRadius() {
        return mRoundRadius;
    }

    public void setRoundRadius(int roundRadius) {
        mRoundRadius = roundRadius;
        invalidate();
    }

    public int getRippleColor() {
        return mRippleColor;
    }

    public void setRippleColor(int rippleColor) {
        mRippleColor = rippleColor;
    }

    public int getRippleDuration() {
        return mRippleDuration;
    }

    public void setRippleDuration(int rippleDuration) {
        mRippleDuration = rippleDuration;
    }

    public int getRippleRadius() {
        return mRippleRadius;
    }

    public void setRippleRadius(int rippleRadius) {
        mRippleRadius = rippleRadius;
    }

    public int getRippleAlpha() {
        return mRippleAlpha;
    }

    public void setRippleAlpha(int rippleAlpha) {
        mRippleAlpha = rippleAlpha;
    }
}
