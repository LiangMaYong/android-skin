package com.liangmayong.skin;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by liangmayong on 2016/9/11.
 */
public class SkinButton extends Button implements OnSkinRefreshListener {

    private Drawable[] drawables = new Drawable[2];
    private int radius = 0;
    private int defualt_text_color = 0xffffffff;
    private int defualt_bg_nor_deta = 0x00111111;
    private int defualt_bg_pre_deta = 0x55111111;
    private Skin.SkinType skinType = Skin.SkinType.defualt;
    private boolean strokeEnabled = false;
    // skinRefreshListener
    private OnSkinRefreshListener skinRefreshListener = null;

    public void setSkinRefreshListener(OnSkinRefreshListener skinRefreshListener) {
        this.skinRefreshListener = skinRefreshListener;
    }

    public SkinButton(Context context) {
        super(context);
        initView();
    }

    /**
     * setSkinType
     *
     * @param skinType skinType
     */
    public void setSkinType(Skin.SkinType skinType) {
        if (this.skinType != skinType) {
            this.skinType = skinType;
            onRefreshSkin(Skin.get());
        }
    }

    public SkinButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SkinButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SkinButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    @SuppressWarnings("deprecation")
    private void initView() {
        radius = dip2px(getContext(), 5);
        drawables[0] = new RadiusDrawable(radius, true, Skin.get().getColor(skinType) - defualt_bg_nor_deta);
        drawables[1] = new RadiusDrawable(radius, true, Skin.get().getColor(skinType) - defualt_bg_pre_deta);
        super.setTextColor(defualt_text_color);
        setBackgroundDrawable(drawables[0]);
        Skin.registerSkinRefresh(this);
    }

    /**
     * dip2px
     *
     * @param context context
     * @param dpValue dpValue
     * @return pxValue
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * setTextColor
     *
     * @param textColor
     */
    @Override
    public void setTextColor(int textColor) {
        this.defualt_text_color = textColor;
        super.setTextColor(textColor);
    }

    @Override
    protected void onDetachedFromWindow() {
        Skin.unregisterSkinRefresh(this);
        super.onDetachedFromWindow();
    }

    /**
     * setRadius
     *
     * @param radiusPx radiusPx
     */
    public void setRadius(int radiusPx) {
        this.radius = radiusPx;
        onRefreshSkin(Skin.get());
    }

    /**
     * setRadiusDip
     *
     * @param radiusDip radiusDip
     */
    public void setRadiusDip(int radiusDip) {
        this.radius = dip2px(getContext(), radiusDip);
        onRefreshSkin(Skin.get());
    }

    /**
     * setStrokeEnabled
     *
     * @param strokeEnabled strokeEnabled
     */
    public void setStrokeEnabled(boolean strokeEnabled) {
        this.strokeEnabled = strokeEnabled;
        onRefreshSkin(Skin.get());
    }

    @SuppressLint("ClickableViewAccessibility")
    @SuppressWarnings("deprecation")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (drawables[1] != null) {
                    setBackgroundDrawable(drawables[1]);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (drawables[0] != null) {
                    setBackgroundDrawable(drawables[0]);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onRefreshSkin(Skin skin) {
        if (!strokeEnabled) {
            ((RadiusDrawable) drawables[0]).setColor(skin.getColor(skinType) - defualt_bg_nor_deta);
            ((RadiusDrawable) drawables[1]).setColor(skin.getColor(skinType) - defualt_bg_pre_deta);
            ((RadiusDrawable) drawables[0]).setStrokeWidth(0);
            ((RadiusDrawable) drawables[1]).setStrokeWidth(0);
            ((RadiusDrawable) drawables[0]).setRadius(radius);
            ((RadiusDrawable) drawables[1]).setRadius(radius);
            setTextColor(skin.getTextColor(skinType));
        } else {
            ((RadiusDrawable) drawables[0]).setColor(0x00ffffff);
            ((RadiusDrawable) drawables[1]).setColor(0x00ffffff);
            ((RadiusDrawable) drawables[0]).setStrokeColor(skin.getColor(skinType) - defualt_bg_nor_deta);
            ((RadiusDrawable) drawables[1]).setStrokeColor(skin.getColor(skinType) - defualt_bg_pre_deta);
            ((RadiusDrawable) drawables[0]).setStrokeWidth(dip2px(getContext(), 2));
            ((RadiusDrawable) drawables[1]).setStrokeWidth(dip2px(getContext(), 2));
            ((RadiusDrawable) drawables[0]).setRadius(radius);
            ((RadiusDrawable) drawables[1]).setRadius(radius);
            setTextColor(skin.getColor(skinType));
        }
        if (skinRefreshListener != null) {
            skinRefreshListener.onRefreshSkin(skin);
        }
        invalidate();
    }

    private static class RadiusDrawable extends Drawable {


        private int topLeftRadius;
        private int topRightRadius;
        private int bottomLeftRadius;
        private int bottomRightRadius;

        private int left;
        private int top;
        private int right;
        private int bottom;


        @SuppressWarnings("unused")
        private int width;
        @SuppressWarnings("unused")
        private int height;


        @SuppressWarnings("unused")
        private int centerX;
        @SuppressWarnings("unused")
        private int centerY;


        private final Paint paint;
        private int color;
        private final boolean isStroke;
        private int strokeWidth = 0;
        private int strokeColor;


        private Path path;

        public RadiusDrawable(int radius, boolean isStroke, int color) {
            this.topLeftRadius =
                    this.topRightRadius = this.bottomLeftRadius = this.bottomRightRadius = radius;


            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            this.isStroke = isStroke;
            this.color = color;
        }


        public void setStrokeWidth(int width) {
            strokeWidth = width;
            setBounds(left, top, right, bottom);
        }


        public void setStrokeColor(int strokeColor) {
            this.strokeColor = strokeColor;
        }


        public void setColor(int color) {
            this.color = color;
        }


        public void setRadius(int radius) {
            this.topLeftRadius = this.topRightRadius = this.bottomLeftRadius = this.bottomRightRadius = radius;
        }


        @Override
        public void setBounds(int left, int top, int right, int bottom) {
            super.setBounds(left, top, right, bottom);


            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            int maxRadius = Math.abs((bottom - top) / 2);
            if (topLeftRadius > maxRadius)
                topLeftRadius = maxRadius;
            if (topRightRadius > maxRadius)
                topRightRadius = maxRadius;
            if (bottomLeftRadius > maxRadius)
                bottomLeftRadius = maxRadius;
            if (bottomRightRadius > maxRadius)
                bottomRightRadius = maxRadius;

            if (isStroke) {
                int halfStrokeWidth = strokeWidth / 2;
                left += halfStrokeWidth;
                top += halfStrokeWidth;
                right -= halfStrokeWidth;
                bottom -= halfStrokeWidth;
            }

            path = new Path();
            path.moveTo(left + topLeftRadius, top);
            path.lineTo(right - topRightRadius, top);
            path.arcTo(new RectF(right - topRightRadius * 2, top, right, top + topRightRadius * 2),
                    -90, 90);
            // path.quadTo(right, top, right, top + topRightRadius);
            path.lineTo(right, bottom - bottomRightRadius);
            path.arcTo(new RectF(right - bottomRightRadius * 2, bottom - bottomRightRadius * 2, right,
                    bottom), 0, 90);
            // path.quadTo(right, bottom, right - bottomRightRadius, bottom);
            path.lineTo(left + bottomLeftRadius, bottom);
            path.arcTo(new RectF(left, bottom - bottomLeftRadius * 2, left + bottomLeftRadius * 2,
                    bottom), 90, 90);
            // path.quadTo(left, bottom, left, bottom - bottomLeftRadius);
            path.lineTo(left, top + topLeftRadius);
            path.arcTo(new RectF(left, top, left + topLeftRadius * 2, top + topLeftRadius * 2), 180, 90);
            // path.quadTo(left, top, left + topLeftRadius, top);
            path.close();
        }


        @Override
        public void draw(Canvas canvas) {
            if (color != 0) {
                paint.setColor(color);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawPath(path, paint);
            }


            if (strokeWidth > 0) {
                paint.setColor(strokeColor);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeJoin(Paint.Join.MITER);
                paint.setStrokeWidth(strokeWidth);
                canvas.drawPath(path, paint);
            }
        }


        @Override
        public void setAlpha(int alpha) {


        }


        @Override
        public void setColorFilter(ColorFilter cf) {


        }


        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }


    }

}
