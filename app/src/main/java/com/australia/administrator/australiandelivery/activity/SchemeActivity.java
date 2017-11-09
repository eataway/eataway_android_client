package com.australia.administrator.australiandelivery.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.australia.administrator.australiandelivery.R;

/**
 * Project: EatAway
 * Author: ZhangHao
 * Date:    2017/6/26
 */

public class SchemeActivity extends AppCompatActivity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_seheme);
        initData();
    }

    private void initData() {
        Uri uri=getIntent().getData();
        String scheme = uri.getScheme();
    }
}
