package com.liangmayong.android_skin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liangmayong.skin.Skin;
import com.liangmayong.skin.SkinButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Skin.editor().setThemeColor(0xff3399ff, 0xffffffff).commit();
    }

    public void click(View view) {
        if (((SkinButton) view).getShapeType() == SkinButton.SHAPE_TYPE_STROKE) {
            ((SkinButton) view).setShapeType(SkinButton.SHAPE_TYPE_RECTANGLE);
        } else {
            ((SkinButton) view).setShapeType(SkinButton.SHAPE_TYPE_STROKE);
        }
        startActivity(new Intent(this, Main2Activity.class));
    }

}
