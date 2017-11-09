package com.australia.administrator.australiandelivery.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.adapter.PersonOrderAdapter;
import com.australia.administrator.australiandelivery.bean.MyOrderBean;
import com.australia.administrator.australiandelivery.comm.BaseActivity;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.view.TopBar;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class PersonOrderActivity extends BaseActivity {
    @Bind(R.id.tp_person_order)
    TopBar tpPersonOrder;
    @Bind(R.id.rv_activity_person_order)
    RecyclerView rvActivityPersonOrder;
    @Bind(R.id.el_person_order)
    EasyRefreshLayout elPersonOrder;

    private MyOrderBean bean;
    private PersonOrderAdapter adapter;
    private HttpUtils utils;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_order;
    }

    @Override
    protected void initDate() {
        loadData();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        InitTopBar();
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivityPersonOrder.setLayoutManager(manager);
        elPersonOrder.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                        elPersonOrder.loadMoreComplete();
                    }
                });
            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        elPersonOrder.refreshComplete();
                    }
                }, 200);
            }
        });
    }

    private void InitTopBar() {
        tpPersonOrder.setBackGround(R.color.actionBar);
        tpPersonOrder.setTbCenterTv(R.string.person_order_title, R.color.white);
        tpPersonOrder.setTbLeftIv(R.drawable.img_back_icon_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 辅助函数
     */
    private void loadData() {
        utils = new HttpUtils(Contants.URL_ORDER_ORDERLIST) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(PersonOrderActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                bean = new Gson().fromJson(response, MyOrderBean.class);
                if (bean != null && bean.getMsg() != null) {
                    if (bean.getStatus() == 1) {
                        page ++;
                        if (page > 1 && bean.getMsg().size() == 0) page--;
                        if (adapter == null) {
                            adapter = new PersonOrderAdapter(PersonOrderActivity.this, bean);
                            rvActivityPersonOrder.setAdapter(adapter);
                        } else {
                            adapter.addBean(bean);
                        }
                        adapter.notifyDataSetChanged();
                    } else if (bean.getStatus() == 0) {
                        Toast.makeText(PersonOrderActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                    } else if (bean.getStatus() == 9) {
                        Toast.makeText(PersonOrderActivity.this, R.string.validation_fails, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        utils.addParam("page", page + "");
        utils.addParam("userid", MyApplication.getLogin().getUserId());
        utils.addParam("token", MyApplication.getLogin().getToken());
        utils.clicent();
        Toast.makeText(PersonOrderActivity.this, page + "", Toast.LENGTH_SHORT).show();
    }

    private void refreshData() {
        utils = new HttpUtils(Contants.URL_ORDER_ORDERLIST) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(PersonOrderActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                bean = new Gson().fromJson(response, MyOrderBean.class);
                if (bean != null && bean.getMsg() != null) {
                    if (bean.getStatus() == 1) {
                        page = 1;
                        if (adapter == null) {
                            adapter = new PersonOrderAdapter(PersonOrderActivity.this, bean);
                            rvActivityPersonOrder.setAdapter(adapter);
                        } else {
                            adapter.setBean(bean);
                            adapter.notifyDataSetChanged();
                        }
                    } else if (bean.getStatus() == 0) {
                        Toast.makeText(PersonOrderActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                    } else if (bean.getStatus() == 9) {
                        Toast.makeText(PersonOrderActivity.this, R.string.validation_fails, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        utils.addParam("page", "1");
        utils.addParam("userid", MyApplication.getLogin().getUserId());
        utils.addParam("token", MyApplication.getLogin().getToken());
        utils.clicent();
    }
}
