package com.liangmayong.android_skin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liangmayong.skin.Skin;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Skin.editor().setThemeColor(0xff3399ff, 0xffffffff).commit();
    }
}
