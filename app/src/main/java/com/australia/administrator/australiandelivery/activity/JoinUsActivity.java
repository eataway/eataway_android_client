package com.australia.administrator.australiandelivery.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.comm.*;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.view.TopBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JoinUsActivity extends com.australia.administrator.australiandelivery.comm.BaseActivity {
    @Bind(R.id.tp_join_us)
    TopBar tpJoinUs;
    @Bind(R.id.btn_download)
    Button btnDownload;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_join_us;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tpJoinUs.setTbCenterTv(R.string.shang_jia_ru_zhu, R.color.white);
        tpJoinUs.setTbLeftIv(R.drawable.img_back_icon_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_download)
    public void onClick() {
//        Toast.makeText(mContext, "DownLoad...", Toast.LENGTH_SHORT).show();
        try {
            Uri uri = Uri.parse("market://details?id=" + "com.administrator.administrator.eataway");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.android.vending");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (Exception e) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(Contants.ADDRESS_DOWNLOAD);
            intent.setData(content_url);
            startActivity(intent);
        }
    }
}
