package com.liangmayong.skin;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by LiangMaYong on 2016/9/24.
 */
public class SkinRelativeLayout extends RelativeLayout implements OnSkinRefreshListener {

    private Skin.SkinType skinType = Skin.SkinType.defualt;
    // skinRefreshListener
    private OnSkinRefreshListener skinRefreshListener = null;

    public void setSkinRefreshListener(OnSkinRefreshListener skinRefreshListener) {
        this.skinRefreshListener = skinRefreshListener;
    }

    public SkinRelativeLayout(Context context) {
        super(context);
        initView(context, null);
    }

    public SkinRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SkinRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SkinRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
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

    private void initView() {
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
