package com.liangmayong.skin;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by LiangMaYong on 2016/9/27.
 */
public class SkinSquareLayout extends SkinLinearLayout {


    public SkinSquareLayout(Context context) {
        super(context);
    }

    public SkinSquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SkinSquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SkinSquareLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        // Children are just made to fill our space.
        int childWidthSize = getMeasuredWidth();

        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
