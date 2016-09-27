package com.liangmayong.skin;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by LiangMaYong on 2016/9/27.
 */
public class SkinBaseButton extends Button implements OnSkinRefreshListener {

    public static final int SHAPE_TYPE_ROUND = 0;
    public static final int SHAPE_TYPE_RECTANGLE = 1;

    protected int mWidth;
    protected int mHeight;
    protected Paint mBackgroundPaint;
    protected int mShapeType;
    protected int mRadius;
    protected int mStrokeWidth;
    protected boolean mStrokeEnable = false;


    public SkinBaseButton(Context context) {
        this(context, null);
    }


    public SkinBaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSkinBaseButton(context, attrs);
    }


    public SkinBaseButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSkinBaseButton(context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SkinBaseButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initSkinBaseButton(context, attrs);
    }

    public void setStrokeEnable(boolean strokeEnable) {
        this.mStrokeEnable = strokeEnable;
        if (strokeEnable) {
            mBackgroundPaint.setStyle(Paint.Style.STROKE);
            mBackgroundPaint.setStrokeWidth(mStrokeWidth);
        } else {
            mBackgroundPaint.setStyle(Paint.Style.FILL);
            mBackgroundPaint.setStrokeWidth(dip2px(getContext(), 0));
        }
        invalidate();
    }

    public boolean isStrokeEnable() {
        return mStrokeEnable;
    }

    public void setStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        invalidate();
    }

    /**
     * dip2px
     *
     * @param context context
     * @param dpValue dpValue
     * @return pxValue
     */
    protected int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    protected void initSkinBaseButton(final Context context, final AttributeSet attrs) {
        if (isInEditMode()) return;
        mShapeType = SHAPE_TYPE_RECTANGLE;
        mRadius = dip2px(context, 5);
        mStrokeWidth = dip2px(context, 3);
        int unpressedColor = 0xff333333;

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setAlpha(Color.alpha(unpressedColor));
        mBackgroundPaint.setColor(unpressedColor);
        mBackgroundPaint.setAntiAlias(true);

        this.setWillNotDraw(false);
        this.setDrawingCacheEnabled(true);
        this.setClickable(true);
        this.eraseOriginalBackgroundColor(unpressedColor);
        Skin.registerSkinRefresh(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        Skin.unregisterSkinRefresh(this);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mBackgroundPaint == null) {
            super.onDraw(canvas);
            return;
        }
        if (mShapeType == 0) {
            canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2,
                    mBackgroundPaint);
        } else {
            RectF rectF = new RectF();
            rectF.set(0, 0, mWidth, mHeight);
            canvas.drawRoundRect(rectF, mRadius, mRadius, mBackgroundPaint);
        }
        super.onDraw(canvas);
    }


    protected void eraseOriginalBackgroundColor(int color) {
        if (color != Color.TRANSPARENT) {
            this.setBackgroundColor(Color.TRANSPARENT);
        }
    }


    /**
     * Set the unpressed color.
     *
     * @param color the color of the background
     */
    public void setUnpressedColor(int color) {
        mBackgroundPaint.setAlpha(Color.alpha(color));
        mBackgroundPaint.setColor(color);
        eraseOriginalBackgroundColor(color);
        invalidate();
    }


    public int getShapeType() {
        return mShapeType;
    }


    /**
     * Set the shape type.
     *
     * @param shapeType SHAPE_TYPE_ROUND or SHAPE_TYPE_RECTANGLE
     */
    public void setShapeType(int shapeType) {
        mShapeType = shapeType;
        invalidate();
    }


    public int getRadius() {
        return mRadius;
    }


    /**
     * Set the radius if the shape type is SHAPE_TYPE_ROUND.
     *
     * @param radius by px.
     */
    public void setRadius(int radius) {
        mRadius = radius;
        invalidate();
    }

    private Skin.SkinType skinType = Skin.SkinType.defualt;

    @Override
    public void onRefreshSkin(Skin skin) {
        setUnpressedColor(skin.getColor(skinType));
        if (mStrokeEnable) {
            setTextColor(skin.getColor(skinType));
        } else {
            setTextColor(skin.getTextColor(skinType));
        }
        if (skinRefreshListener != null) {
            skinRefreshListener.onRefreshSkin(skin);
        }
    }

    // skinRefreshListener
    private OnSkinRefreshListener skinRefreshListener = null;

    public void setSkinRefreshListener(OnSkinRefreshListener skinRefreshListener) {
        this.skinRefreshListener = skinRefreshListener;
    }
}
