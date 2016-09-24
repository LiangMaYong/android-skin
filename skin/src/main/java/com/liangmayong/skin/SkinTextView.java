package com.liangmayong.skin;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by liangmayong on 2016/9/11.
 */
public class SkinTextView extends TextView implements OnSkinRefreshListener {

    private Skin.SkinType skinType = Skin.SkinType.defualt;
    // skinRefreshListener
    private OnSkinRefreshListener skinRefreshListener = null;
    // private
    private boolean contrary = false;

    /**
     * setTextColorContrary
     *
     * @param contrary contrary
     */
    public void setTextColorContrary(boolean contrary) {
        this.contrary = contrary;
    }

    /**
     * isTextColorContrary
     *
     * @return contrary
     */
    public boolean isTextColorContrary() {
        return contrary;
    }

    public void setSkinRefreshListener(OnSkinRefreshListener skinRefreshListener) {
        this.skinRefreshListener = skinRefreshListener;
    }

    public SkinTextView(Context context) {
        super(context);
        initView();
    }

    public SkinTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SkinTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SkinTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
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

    @Override
    protected void onDetachedFromWindow() {
        Skin.unregisterSkinRefresh(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void onRefreshSkin(Skin skin) {
        if (contrary) {
            setTextColor(skin.getTextColor(skinType));
        } else {
            setTextColor(skin.getColor(skinType));
        }
        if (skinRefreshListener != null) {
            skinRefreshListener.onRefreshSkin(skin);
        }
    }
}
