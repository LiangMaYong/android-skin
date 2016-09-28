package com.liangmayong.android_skin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liangmayong.skin.Skin;
import com.liangmayong.skin.SkinButton;
import com.liangmayong.skin.SkinInterface;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Skin.editor().setThemeColor(0xffff6585, 0xff333333).commit();
    }

    public void click(View view) {
        if (((SkinInterface) view).getShapeType() == SkinButton.SHAPE_TYPE_STROKE) {
            ((SkinInterface) view).setShapeType(SkinButton.SHAPE_TYPE_RECTANGLE);
        } else {
            ((SkinInterface) view).setShapeType(SkinButton.SHAPE_TYPE_STROKE);
        }
        startActivity(new Intent(this, Main2Activity.class));
    }

}
