package com.liangmayong.skin;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by liangmayong on 2016/9/11.
 */
public class SkinStrokeButton extends SkinButton {

    public SkinStrokeButton(Context context) {
        super(context);
        initSkinStrokeButton();
    }

    public SkinStrokeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSkinStrokeButton();
    }

    public SkinStrokeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSkinStrokeButton();
    }

    public SkinStrokeButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initSkinStrokeButton();
    }

    private void initSkinStrokeButton() {
        setShapeType(SHAPE_TYPE_STROKE);
    }
}
