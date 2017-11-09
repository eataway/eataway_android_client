package com.australia.administrator.australiandelivery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.comm.*;
import com.australia.administrator.australiandelivery.view.TopBar;

import butterknife.Bind;

public class BeiZhuActivity extends com.australia.administrator.australiandelivery.comm.BaseActivity {
    @Bind(R.id.tp_activity_feed_back)
    TopBar tpActivityFeedBack;
    @Bind(R.id.et_activity_feed_back)
    EditText etActivityFeedBack;
    @Bind(R.id.btn_activity_feed_back)
    Button btnActivityFeedBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bei_zhu;
    }

    @Override
    protected void initDate() {
        Intent i = getIntent();
        if (i != null) {
            etActivityFeedBack.setText(i.getStringExtra("note"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tpActivityFeedBack.setTbCenterTv(R.string.taste_notes,R.color.white);
        tpActivityFeedBack.setTbLeftIv(R.drawable.img_back_icon_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnActivityFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("note",etActivityFeedBack.getText().toString());
                setResult(9,i);
                finish();
            }
        });
    }
}
