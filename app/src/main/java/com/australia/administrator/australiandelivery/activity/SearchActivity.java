package com.australia.administrator.australiandelivery.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.adapter.SearchListAdapter;
import com.australia.administrator.australiandelivery.bean.SearchBean;
import com.australia.administrator.australiandelivery.comm.BaseActivity;
import com.australia.administrator.australiandelivery.comm.Login;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import okhttp3.Call;

public class SearchActivity extends BaseActivity {
    private String TAG = "search";
    @Bind(R.id.im_search)
    ImageView imSearch;
    @Bind(R.id.et_search_content)
    EditText etSearchContent;
    @Bind(R.id.tv_cancel_search)
    TextView tvCancelSearch;
    @Bind(R.id.rv_activity_search_list)
    RecyclerView rvActivitySearchList;

    private SearchListAdapter adapter;
    private SearchBean bean;
    private String searchKey = "";

    private SearchRunnable runnable = new SearchRunnable();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initDate() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initClickListener();
        initListRecylerView();
        initEditText();
    }

    /**
     * 辅助函数
     */
    private void initListRecylerView(){
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivitySearchList.setLayoutManager(manager);
    }
    private void initClickListener() {
        tvCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initEditText() {
        etSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG,s.toString()+"0");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG,s.toString()+"1");
                if (s.length() != 0) {
                    searchKey = s.toString().trim();
                    rvActivitySearchList.setVisibility(View.VISIBLE);
                    runnable.post();
                }else {
                    if (adapter == null) {
                        adapter = new SearchListAdapter(SearchActivity.this, bean);
                        rvActivitySearchList.setAdapter(adapter);
                    }else {
                        bean = null;
                        adapter.setBean(bean);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG,s.toString()+"2");
            }
        });
    }

    /**
     * 辅助类
     */
    class SearchRunnable implements Runnable {
        Handler handler = new Handler();
        public void post() {
            handler.removeCallbacks(this);
            handler.post(this);
        }
        @Override
        public void run() {
            HttpUtils http = new HttpUtils(Contants.URL_SEARCHSHOP) {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Toast.makeText(SearchActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        JSONObject o = new JSONObject(response);
                        int status = o.getInt("status");
                        if (status == 1) {
                            bean = new Gson().fromJson(response, SearchBean.class);
                            if (adapter != null) {
                                adapter.setBean(bean);
                                adapter.notifyDataSetChanged();
                            }else {
                                adapter = new SearchListAdapter(SearchActivity.this, bean);
                                rvActivitySearchList.setAdapter(adapter);
                            }
                        }else if (status == 3) {
                            Toast.makeText(SearchActivity.this, R.string.mei_you_zhao_dao_xiang_guan, Toast.LENGTH_SHORT).show();
                        }else if (status == 9) {
                            Toast.makeText(SearchActivity.this, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                            MyApplication.saveLogin(null);
                            goToActivity(LoginActivity.class);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            http.addParam("shopname",searchKey);
            http.clicent();
        }
    }
}
