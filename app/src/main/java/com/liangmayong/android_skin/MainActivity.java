package com.liangmayong.android_skin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liangmayong.skin.Skin;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Skin.editor().setThemeColor(0xffff6585, 0xff333333).commit();
    }

    boolean c = false;

    public void click(View view) {
        if (c) {
            c = false;
            Skin.editor().setThemeColor(0xffff6585, 0xff333333).setDangerColor(0xffff6585,0xffffffff).setPrimaryColor(0xff3399ff,0xffffffff).setSuccessColor(0xff18a28b,0xffffffff).commit();
        } else {
            c = true;
            Skin.editor().reset().commit();
        }
    }

}
