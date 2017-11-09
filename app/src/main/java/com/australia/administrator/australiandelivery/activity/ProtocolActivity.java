package com.australia.administrator.australiandelivery.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.australia.administrator.australiandelivery.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProtocolActivity extends AppCompatActivity {
    @Bind(R.id.iv_back_protocol)
    ImageView ivBackProtocol;
    @Bind(R.id.wv_protocol)
    WebView wvProtocol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        ButterKnife.bind(this);
        ivBackProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        wvProtocol.loadUrl("https://eataway.com.au/terms-and-conditions/");
    }
}
