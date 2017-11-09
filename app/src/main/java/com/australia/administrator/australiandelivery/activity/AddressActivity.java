package com.australia.administrator.australiandelivery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.adapter.AddressListAdapter;
import com.australia.administrator.australiandelivery.bean.AddressBean1;
import com.australia.administrator.australiandelivery.comm.Login;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.view.TopBar;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Project: EatAway
 * Author: ZhangHao
 * Date:    2017/6/26
 */

public class AddressActivity extends AppCompatActivity {
    @Bind(R.id.rv_address)
    RecyclerView rvAddress;
    @Bind(R.id.tb_address)
    TopBar tbAddress;
    @Bind(R.id.rl_add_address)
    RelativeLayout rlAddAddress;
    @Bind(R.id.iv_add_address_left)
    ImageView ivAddAddressLeft;
    private Login login = MyApplication.getLogin();
    private String type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        tbAddress.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tbAddress.setBackGround(R.color.actionBar);
        tbAddress.setTbCenterTv(R.string.please_choos_address,R.color.app_bg_topbar);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAddress.setLayoutManager(manager);
        rlAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this,AddressReleaseActiviyty.class));
            }
        });
    }

    private void initData() {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_GETADDRESS) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(AddressActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                AddressBean1 bean=new Gson().fromJson(response,AddressBean1.class);
                rvAddress.setAdapter(new AddressListAdapter(AddressActivity.this,bean,type,AddressActivity.this));
            }
        };
        httpUtils.addParam("userid", login.getUserId()).addParams("token", login.getToken());
        httpUtils.clicent();
    }
}
