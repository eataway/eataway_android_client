package com.australia.administrator.australiandelivery.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.comm.*;
import com.australia.administrator.australiandelivery.view.TopBar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutUsActivity extends com.australia.administrator.australiandelivery.comm.BaseActivity {
    private final String VERSION = "v1.0.6";

    @Bind(R.id.tb_about_us)
    TopBar tbAboutUs;
    @Bind(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tbAboutUs.setTbCenterTv(R.string.person_aboutus, R.color.white);
        tbAboutUs.setTbLeftIv(R.drawable.img_back_icon_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvVersion.setText(getString(R.string.ban_ben_hao) + VERSION);
    }
}
