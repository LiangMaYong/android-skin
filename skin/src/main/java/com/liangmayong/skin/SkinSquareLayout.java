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
import android.widget.LinearLayout;

/**
 * Created by LiangMaYong on 2016/9/27.
 */
public class SkinSquareLayout extends LinearLayout implements OnSkinRefreshListener {

    public static final int SHAPE_TYPE_ROUND = 0;
    public static final int SHAPE_TYPE_RECTANGLE = 1;
    public static final int SHAPE_TYPE_STROKE = 2;
    public static final int SHAPE_TYPE_OVAL = 3;
    public static final int SHAPE_TYPE_TRANSPARENT = 4;

    protected int mWidth;
    protected int mHeight;
    protected Paint mBackgroundPaint;
    protected int mShapeType;
    protected int mRadius;
    protected int mStrokeWidth;
    private int mPressedAlpha = 50;
    private Paint mPressedPaint;
    private int mPressedColor;
    private int mSkinColor;
    private int mSkinTextColor;
    private boolean isSetSkinColor = false;
    private boolean isSetSkinTextColor = false;
    private Skin.SkinType skinType = Skin.SkinType.defualt;


    public SkinSquareLayout(Context context) {
        this(context, null);
    }


    public SkinSquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBG(context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SkinSquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBG(context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SkinSquareLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initBG(context, attrs);
    }

    public void setStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        // Children are just made to fill our space.
        int childWidthSize = getMeasuredWidth();

        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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

    protected void initBG(final Context context, final AttributeSet attrs) {
        if (isInEditMode()) return;
        int color = 0xff333333;
        mPressedColor = 0xff333333;
        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinStyleable);
            mShapeType = typedArray.getInt(R.styleable.SkinStyleable_shape_type, SHAPE_TYPE_RECTANGLE);
            mRadius = typedArray.getDimensionPixelSize(R.styleable.SkinStyleable_radius, 0);
            mPressedColor = typedArray.getColor(R.styleable.SkinStyleable_pressed_color, mPressedColor);
            mPressedAlpha = typedArray.getInteger(R.styleable.SkinStyleable_pressed_alpha, mPressedAlpha);
            mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.SkinStyleable_stroke_width, dip2px(context, 2));
            int skin = typedArray.getInt(R.styleable.SkinStyleable_skin_type, skinType.value());
            skinType = Skin.SkinType.valueOf(skin);
            if (typedArray.hasValue(R.styleable.SkinStyleable_skin_color)) {
                mSkinColor = typedArray.getColor(R.styleable.SkinStyleable_skin_color, Skin.get().getColor(skinType));
                isSetSkinColor = true;
            }
            if (typedArray.hasValue(R.styleable.SkinStyleable_skin_text_color)) {
                mSkinTextColor = typedArray.getColor(R.styleable.SkinStyleable_skin_text_color, Skin.get().getTextColor(skinType));
                isSetSkinTextColor = true;
            }
            typedArray.recycle();
        }
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setAlpha(Color.alpha(color));
        mBackgroundPaint.setColor(color);

        this.setWillNotDraw(false);
        this.setDrawingCacheEnabled(true);
        this.eraseOriginalBackgroundColor(color);
        Skin.registerSkinRefresh(this);
        initPR(context, attrs);
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
            canvas.drawCircle(mWidth / 2, mHeight / 2, Math.min(mHeight / 2, mWidth / 2),
                    mBackgroundPaint);
        } else if (mShapeType == SHAPE_TYPE_OVAL) {
            mBackgroundPaint.setStyle(Paint.Style.FILL);
            mBackgroundPaint.setStrokeWidth(0);
            RectF rectF = new RectF();
            rectF.set(0, 0, mWidth, mHeight);
            canvas.drawOval(rectF, mBackgroundPaint);
        } else if (mShapeType == SHAPE_TYPE_TRANSPARENT) {

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
        onPressedDraw(canvas);
        super.onDraw(canvas);
    }


    protected void eraseOriginalBackgroundColor(int color) {
        if (color != Color.TRANSPARENT) {
            this.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    protected void initPR(Context context, AttributeSet attrs) {
        mPressedPaint = new Paint();
        mPressedPaint.setAntiAlias(true);
        mPressedPaint.setStyle(Paint.Style.FILL);
        mPressedPaint.setColor(mPressedColor);
        mPressedPaint.setAlpha(0);
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
        if (mShapeType == SHAPE_TYPE_ROUND) {
            canvas.drawCircle(mWidth / 2, mHeight / 2, Math.min(mHeight / 2.1038f, mWidth / 2.1038f),
                    mPressedPaint);
        } else if (mShapeType == SHAPE_TYPE_OVAL) {
            RectF rectF = new RectF();
            rectF.set(0, 0, mWidth, mHeight);
            canvas.drawOval(rectF, mPressedPaint);
        } else if (mShapeType == SHAPE_TYPE_TRANSPARENT) {

        } else {
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
                    mPressedPaint.setAlpha(mPressedAlpha);
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
        if (isSetSkinColor) {
            setUnpressedColor(mSkinColor);
        } else {
            setUnpressedColor(skin.getColor(skinType));
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