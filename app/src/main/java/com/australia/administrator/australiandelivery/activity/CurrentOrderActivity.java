package com.australia.administrator.australiandelivery.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.adapter.CurrentOrderAdapter;
import com.australia.administrator.australiandelivery.bean.CurrentOrderBean;
import com.australia.administrator.australiandelivery.comm.BaseActivity;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.view.TopBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class CurrentOrderActivity extends BaseActivity {
    @Bind(R.id.rv_current_order)
    public RecyclerView rvCurrentOrder;
    @Bind(R.id.tp_current_order)
    TopBar tpCurrentOrder;
    @Bind(R.id.tv_current_order_none)
    public TextView tvCurrentOrderNone;
    private CurrentOrderAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_current_order;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTopBar();
        initRecylerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.getLogin() != null) {
            HttpUtils httpUtils = new HttpUtils(Contants.URL_CURRENT) {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Toast.makeText(CurrentOrderActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        JSONObject o = new JSONObject(response);
                        int status = o.getInt("status");
                        if (status == 0) {
                            Toast.makeText(CurrentOrderActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                        } else if (status == 1) {
                            CurrentOrderBean bean = new Gson().fromJson(response, CurrentOrderBean.class);
                            if (bean!=null && bean.getMsg().size()>0) {
                                rvCurrentOrder.setVisibility(View.VISIBLE);
                                tvCurrentOrderNone.setVisibility(View.GONE);
                                if (adapter == null) {
                                    adapter = new CurrentOrderAdapter(CurrentOrderActivity.this, bean);
                                    rvCurrentOrder.setAdapter(adapter);
                                } else {
                                    adapter.setBean(bean);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        } else if (status == 9) {
                            Toast.makeText(CurrentOrderActivity.this, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                            MyApplication.saveLogin(null);
                            goToActivity(LoginActivity.class);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            httpUtils.addParam("userid", MyApplication.getLogin().getUserId());
            httpUtils.addParam("token", MyApplication.getLogin().getToken());
            httpUtils.clicent();
        }
    }

    private void initRecylerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCurrentOrder.setLayoutManager(manager);
    }

    private void initTopBar() {
        tpCurrentOrder.setTbCenterTv(R.string.dang_qian_ding_dan, R.color.white);
        tpCurrentOrder.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
