package com.liangmayong.android_skin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liangmayong.skin.Skin;
import com.liangmayong.skin.SkinBaseButton;
import com.liangmayong.skin.SkinStrokeButton;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Skin.editor().setThemeColor(0xffff6585, 0xffffffff).commit();
    }

    public void click(View view) {
        Skin.editor().setThemeColor(0xff3399ff, 0xff333333).commit();
        finish();
    }


}
