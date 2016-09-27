package com.liangmayong.skin;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
    public static final int SHAPE_TYPE_STROKE = 2;

    protected int mWidth;
    protected int mHeight;
    protected Paint mBackgroundPaint;
    protected int mShapeType;
    protected int mRadius;
    protected int mStrokeWidth;


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
        mRadius = dip2px(context, 8);
        mStrokeWidth = dip2px(context, 1.4f);
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
        if (mRadius > mHeight / 2 || mRadius > mWidth / 2) {
            mRadius = Math.min(mHeight / 2, mWidth / 2);
        }
        if (mShapeType == SHAPE_TYPE_ROUND) {
            mBackgroundPaint.setStyle(Paint.Style.FILL);
            mBackgroundPaint.setStrokeWidth(0);
            canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2,
                    mBackgroundPaint);
        } else if (mShapeType == SHAPE_TYPE_STROKE) {
            mBackgroundPaint.setStyle(Paint.Style.STROKE);
            mBackgroundPaint.setStrokeJoin(Paint.Join.MITER);
            mBackgroundPaint.setStrokeWidth(mStrokeWidth);
            canvas.drawPath(getPath(0, 0, mWidth, mHeight), mBackgroundPaint);
        } else {
            mBackgroundPaint.setStyle(Paint.Style.FILL);
            mBackgroundPaint.setStrokeWidth(0);
            RectF rectF = new RectF();
            rectF.set(0, 0, mWidth, mHeight);
            if (mRadius > mHeight / 2 || mRadius > mWidth / 2) {
                mRadius = Math.min(mHeight / 2, mWidth / 2);
            }
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


    protected Path getPath(int left, int top, int right, int bottom) {
        int halfStrokeWidth = mStrokeWidth / 2;
        left += halfStrokeWidth;
        top += halfStrokeWidth;
        right -= halfStrokeWidth;
        bottom -= halfStrokeWidth;

        Path path = new Path();
        path.moveTo(left + mRadius, top);
        path.lineTo(right - mRadius, top);
        path.arcTo(new RectF(right - mRadius * 2, top, right, top + mRadius * 2),
                -90, 90);
        // path.quadTo(right, top, right, top + topRightRadius);
        path.lineTo(right, bottom - mRadius);
        path.arcTo(new RectF(right - mRadius * 2, bottom - mRadius * 2, right,
                bottom), 0, 90);
        // path.quadTo(right, bottom, right - bottomRightRadius, bottom);
        path.lineTo(left + mRadius, bottom);
        path.arcTo(new RectF(left, bottom - mRadius * 2, left + mRadius * 2,
                bottom), 90, 90);
        // path.quadTo(left, bottom, left, bottom - bottomLeftRadius);
        path.lineTo(left, top + mRadius);
        path.arcTo(new RectF(left, top, left + mRadius * 2, top + mRadius * 2), 180, 90);
        // path.quadTo(left, top, left + topLeftRadius, top);
        path.close();
        return path;
    }

    /**
     * Set the shape type.
     *
     * @param shapeType SHAPE_TYPE_ROUND or SHAPE_TYPE_RECTANGLE
     */
    public void setShapeType(int shapeType) {
        mShapeType = shapeType;
        if (mShapeType == SHAPE_TYPE_STROKE) {
            setTextColor(Skin.get().getColor(skinType));
        } else {
            setTextColor(Skin.get().getTextColor(skinType));
        }
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
        if (mShapeType == SHAPE_TYPE_STROKE) {
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
