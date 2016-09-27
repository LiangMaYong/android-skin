package com.liangmayong.skin;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by liangmayong on 2016/9/11.
 */
public class SkinLinearLayout extends LinearLayout implements OnSkinRefreshListener {

    private Skin.SkinType skinType = Skin.SkinType.defualt;
    // skinRefreshListener
    private OnSkinRefreshListener skinRefreshListener = null;

    public void setSkinRefreshListener(OnSkinRefreshListener skinRefreshListener) {
        this.skinRefreshListener = skinRefreshListener;
    }

    public SkinLinearLayout(Context context) {
        super(context);
        initView(context, null);
    }

    public SkinLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SkinLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SkinLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
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

    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinStyleable);
            int skin = typedArray.getInt(R.styleable.SkinStyleable_skin_type, skinType.value());
            skinType = Skin.SkinType.valueOf(skin);
            typedArray.recycle();
        }
        Skin.registerSkinRefresh(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        Skin.unregisterSkinRefresh(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void onRefreshSkin(Skin skin) {
        setBackgroundColor(skin.getColor(skinType));
        if (skinRefreshListener != null) {
            skinRefreshListener.onRefreshSkin(skin);
        }
    }
}
