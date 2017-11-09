package com.australia.administrator.australiandelivery.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.comm.BaseActivity;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.view.TopBar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


public class FeedBackActivity extends BaseActivity {
    @Bind(R.id.tp_activity_feed_back)
    TopBar tpActivityFeedBack;
    @Bind(R.id.et_activity_feed_back)
    EditText etActivityFeedBack;
    @Bind(R.id.btn_activity_feed_back)
    Button btnActivityFeedBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initDate() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        InitTopBar();
    }

    @OnClick(R.id.btn_activity_feed_back)
    public void onClick() {
        if (etActivityFeedBack.getText().length() != 0){
            HttpUtils httpUtils = new HttpUtils(Contants.URL_EVALUATE_BACK) {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Toast.makeText(FeedBackActivity.this, getResources().getString(R.string.please_check_your_network_connection), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        JSONObject object = new JSONObject(response);
                        int status = object.getInt("status");
                        if (status == 1) {
                            Toast.makeText(FeedBackActivity.this, R.string.thank_you_for_comments, Toast.LENGTH_SHORT).show();
                            etActivityFeedBack.setText("");
                        } else if (status == 0) {
                            Toast.makeText(FeedBackActivity.this, R.string.Give_FeedBack_unsuccessfully, Toast.LENGTH_SHORT).show();
                        } else if (status == 9) {
                            Toast.makeText(FeedBackActivity.this, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
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
            httpUtils.addParam("content", etActivityFeedBack.getText().toString().trim());
            httpUtils.clicent();
        }else {
            Toast.makeText(FeedBackActivity.this, R.string.content_cannot_be_empty, Toast.LENGTH_SHORT).show();
        }
    }

    private void InitTopBar() {
        tpActivityFeedBack.setBackGround(R.color.actionBar);
        tpActivityFeedBack.setTbCenterTv(R.string.person_advice, R.color.white);
        tpActivityFeedBack.setTbLeftIv(R.drawable.img_back_icon_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
