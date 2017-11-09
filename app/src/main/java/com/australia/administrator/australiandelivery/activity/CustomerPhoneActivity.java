package com.australia.administrator.australiandelivery.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.adapter.CustomerPhoneAdapter;
import com.australia.administrator.australiandelivery.comm.BaseActivity;
import com.australia.administrator.australiandelivery.ui.TextDialog;
import com.australia.administrator.australiandelivery.view.TopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class CustomerPhoneActivity extends BaseActivity {
    @Bind(R.id.tp_customer_phone)
    TopBar tpCustomerPhone;
    @Bind(R.id.rv_customer_phone)
    RecyclerView rvCustomerPhone;

    private CustomerPhoneAdapter adapter;
    private List<String> data = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_phone;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTopBar();
        getData();
        initRecyclerView();
    }

    private void initTopBar() {
        tpCustomerPhone.setTbCenterTv(R.string.ke_fu_re_xian, R.color.white);
        tpCustomerPhone.setTbLeftIv(R.drawable.img_back_icon_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tpCustomerPhone.setTbRightIv(R.drawable.icon_attention, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextDialog dialog = new TextDialog(CustomerPhoneActivity.this, R.style.ShareDialog, getString(R.string.zhu_yi), getString(R.string.fu_wu_shi_jian));
                dialog.show();
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCustomerPhone.setLayoutManager(manager);
        adapter = new CustomerPhoneAdapter(this, data);
        rvCustomerPhone.setAdapter(adapter);
    }

    private void getData() {
        data.add("0404 981 299");
        data.add("0452 451 156");
        data.add("0403 537 867");
    }
}
