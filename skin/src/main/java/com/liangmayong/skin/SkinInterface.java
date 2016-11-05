package com.liangmayong.skin;

/**
 * Created by LiangMaYong on 2016/9/28.
 */
public interface SkinInterface extends com.liangmayong.skin.OnSkinRefreshListener {

    int SHAPE_TYPE_ROUND = 0;
    int SHAPE_TYPE_RECTANGLE = 1;
    int SHAPE_TYPE_STROKE = 2;
    int SHAPE_TYPE_OVAL = 3;
    int SHAPE_TYPE_TRANSPARENT = 4;

    void setSkinRefreshListener(OnSkinRefreshListener skinRefreshListener);

    void setSkinType(Skin.SkinType skinType);

    void setSkinColor(int skinColor, int skinTextColor);

    int getRadius();

    void setRadius(int radius);

    void setShapeType(int shapeType);

    int getShapeType();

    void setPressedColor(int pressedColor);

    int getPressedColor();

    void setStrokeWidth(int strokeWidth);

    Skin.SkinType getSkinType();

    int getSkinColor();

    int getSkinTextColor();

    void setBackgroundCoverColor(int color);
}
