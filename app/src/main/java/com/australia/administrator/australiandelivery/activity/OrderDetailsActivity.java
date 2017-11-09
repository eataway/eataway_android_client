package com.australia.administrator.australiandelivery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.adapter.OrderDetailsAdapter;
import com.australia.administrator.australiandelivery.bean.OrderBean;
import com.australia.administrator.australiandelivery.utils.ContactUtils;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.view.TopBar;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class OrderDetailsActivity extends com.australia.administrator.australiandelivery.comm.BaseActivity {
    @Bind(R.id.tp_ordre_details)
    TopBar tpOrdreDetails;
    @Bind(R.id.rv_activity_order_details)
    RecyclerView rvActivityOrderDetails;
    @Bind(R.id.btn_activity_order_details_reviews)
    Button btnActivityOrderDetailsReviews;
    @Bind(R.id.btn_activity_order_details_buyagain)
    Button btnActivityOrderDetailsBuyagain;
    @Bind(R.id.rl_buttons)
    RelativeLayout rlButtons;

    private OrderDetailsAdapter adapter;
    private String orderId;
    private OrderBean bean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initDate() {
        final Intent i = getIntent();
        orderId = i.getStringExtra("orderid");
        HttpUtils httpUtils = new HttpUtils(Contants.URL_ORDER_ORDERDETAILS) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(OrderDetailsActivity.this, getResources().getString(R.string.please_check_your_network_connection), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("order", response);
                bean = new Gson().fromJson(response, OrderBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 1) {
                        if (bean.getMsg() != null) {
                            rlButtons.setVisibility(View.VISIBLE);
                            if (bean.getMsg().getState().equals("1")) { //未接单
                                rlButtons.setVisibility(View.GONE);
                            }else if (bean.getMsg().getState().equals("2")) {   //已接单
                                rlButtons.setVisibility(View.GONE);
                            }else if (bean.getMsg().getState().equals("3")) {   //待确认
                                rlButtons.setVisibility(View.VISIBLE);
                                btnActivityOrderDetailsReviews.setVisibility(View.GONE);
                                btnActivityOrderDetailsBuyagain.setText(getResources().getString(R.string.order_contact_url));
                                btnActivityOrderDetailsReviews.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ContactUtils.ContactUS(OrderDetailsActivity.this, bean.getMsg().getShopphone());
                                    }
                                });
                            }else if (bean.getMsg().getState().equals("4")) {   //已完成
                                rlButtons.setVisibility(View.VISIBLE);
                                btnActivityOrderDetailsReviews.setVisibility(View.VISIBLE);
                                btnActivityOrderDetailsBuyagain.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(OrderDetailsActivity.this, GoodsListActivity1.class);
                                        intent.putExtra("id", bean.getMsg().getShopid());
                                        startActivity(intent);
                                    }
                                });
                                btnActivityOrderDetailsReviews.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(OrderDetailsActivity.this, GoodsListActivity1.class);
                                        intent.putExtra("shopid", bean.getMsg().getShopid());
                                        intent.putExtra("orderid", bean.getMsg().getOrderid());
                                        intent.putExtra("shopname", bean.getMsg().getShopname());
                                        intent.putExtra("shopicon", bean.getMsg().getShophead());
                                        intent.putExtra("shoppic", bean.getMsg().getShopphoto());
                                        startActivity(intent);
                                    }
                                });
                            }else {                                             //已评价
                                rlButtons.setVisibility(View.VISIBLE);
                                btnActivityOrderDetailsReviews.setText(getResources().getString(R.string.order_myreviews));
                                btnActivityOrderDetailsReviews.setTextColor(getResources().getColor(R.color.text_gray));
                                btnActivityOrderDetailsReviews.setClickable(false);
                                btnActivityOrderDetailsReviews.setBackgroundResource(R.drawable.shape_person_order_gray);
                            }
                        } else {
                            rlButtons.setVisibility(View.GONE);
                        }
                        if (adapter != null) {
                            adapter.setBean(bean);
                        } else {
                            adapter = new OrderDetailsAdapter(OrderDetailsActivity.this, bean);
                            rvActivityOrderDetails.setAdapter(adapter);
                        }
                    } else if (bean.getStatus() == 0) {
                        Toast.makeText(OrderDetailsActivity.this, getResources().getString(R.string.please_check_your_network_connection), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OrderDetailsActivity.this, getResources().getString(R.string.Please_Log_on_again), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        httpUtils.addParam("userid", MyApplication.getLogin().getUserId());
        httpUtils.addParam("orderid", orderId);
        httpUtils.addParam("token", MyApplication.getLogin().getToken());
        httpUtils.clicent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        InitTopBar();
        initRecylerView();
    }

    /**
     * 辅助函数
     */
    private void InitTopBar() {
        tpOrdreDetails.setBackGround(R.color.actionBar);
        tpOrdreDetails.setTbCenterTv(R.string.person_order_title, R.color.white);
        tpOrdreDetails.setTbLeftIv(R.drawable.img_back_icon_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tpOrdreDetails.setTbRightIv(R.drawable.img_icon_phone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean != null)
                    ContactUtils.ContactUS(OrderDetailsActivity.this,bean.getMsg().getShopphone());
            }
        });
    }

    private void initRecylerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivityOrderDetails.setLayoutManager(manager);
    }
}
