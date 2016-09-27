package com.liangmayong.skin;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by LiangMaYong on 2016/9/27.
 */
public class SkinButton extends Button implements OnSkinRefreshListener {

    public static final int SHAPE_TYPE_ROUND = 0;
    public static final int SHAPE_TYPE_RECTANGLE = 1;
    public static final int SHAPE_TYPE_STROKE = 2;

    protected int mWidth;
    protected int mHeight;
    protected Paint mBackgroundPaint;
    protected int mShapeType;
    protected int mRadius;
    protected int mStrokeWidth;
    private int COVER_ALPHA = 48;
    private Paint mPressedPaint;
    private int mPressedColor;
    private Skin.SkinType skinType = Skin.SkinType.defualt;


    public SkinButton(Context context) {
        this(context, null);
    }


    public SkinButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSkinBaseButton(context, attrs);
    }


    public SkinButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSkinBaseButton(context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SkinButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        int color = 0xff333333;
        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinStyleable);
            mShapeType = typedArray.getInt(R.styleable.SkinStyleable_shape_type, SHAPE_TYPE_RECTANGLE);
            mRadius = typedArray.getDimensionPixelSize(R.styleable.SkinStyleable_radius, dip2px(context, 5));
            mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.SkinStyleable_stroke_width, dip2px(context, 2));
            int skin = typedArray.getInt(R.styleable.SkinStyleable_skin_type, skinType.value());
            skinType = Skin.SkinType.valueOf(skin);
            color = typedArray.getColor(R.styleable.SkinStyleable_color, color);
            typedArray.recycle();
        }
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setAlpha(Color.alpha(color));
        mBackgroundPaint.setColor(color);
        mBackgroundPaint.setAntiAlias(true);

        this.setWillNotDraw(false);
        this.setDrawingCacheEnabled(true);
        this.setClickable(true);
        this.eraseOriginalBackgroundColor(color);
        Skin.registerSkinRefresh(this);
        initSkinButton(context, attrs);
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
        onPressedDraw(canvas);
    }


    protected void eraseOriginalBackgroundColor(int color) {
        if (color != Color.TRANSPARENT) {
            this.setBackgroundColor(Color.TRANSPARENT);
        }
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

    private void onPressedDraw(Canvas canvas) {
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
        if (isClickable()) {
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
        }
        return super.onTouchEvent(event);
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
