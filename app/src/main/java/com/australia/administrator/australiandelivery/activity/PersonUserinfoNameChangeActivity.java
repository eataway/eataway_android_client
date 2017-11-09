package com.australia.administrator.australiandelivery.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.comm.BaseActivity;
import com.australia.administrator.australiandelivery.comm.Login;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.view.TopBar;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class PersonUserinfoNameChangeActivity extends BaseActivity {
    @Bind(R.id.et_person_name_change)
    EditText etPersonNameChange;
    @Bind(R.id.btn_person_name_change)
    Button btnPersonNameChange;
    @Bind(R.id.tp_name_change)
    TopBar tpNameChange;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_userinfo_name_change;
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

    private void InitTopBar() {
        tpNameChange.setBackGround(R.color.actionBar);
        tpNameChange.setTbCenterTv(R.string.person_userinfo_changeusername, R.color.white);
        tpNameChange.setTbLeftIv(R.drawable.img_back_icon_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Nullable
    @OnClick(R.id.btn_person_name_change)
    public void onClick() {
        if (!"".equals(etPersonNameChange.getText().toString())) {
            HttpUtils httpUtils = new HttpUtils(Contants.URL_USER_EDITNAME) {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Toast.makeText(PersonUserinfoNameChangeActivity.this, getResources().getString(R.string.please_check_your_network_connection), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response, int id) {
                    MyBean bean = new Gson().fromJson(response, MyBean.class);
                    if (bean != null) {
                        if (bean.getStatus() == 1) {
                            Toast.makeText(PersonUserinfoNameChangeActivity.this, R.string.change_successfully, Toast.LENGTH_SHORT).show();
                            Login login = MyApplication.getLogin();
                            login.setUserName(etPersonNameChange.getText().toString().trim());
                            MyApplication.saveLogin(login);
                            finish();
                        } else if (bean.getStatus() == 0) {
                            Toast.makeText(PersonUserinfoNameChangeActivity.this, R.string.change_unsuccessfully, Toast.LENGTH_SHORT).show();
                        } else if (bean.getStatus() == 9) {
                            Toast.makeText(PersonUserinfoNameChangeActivity.this, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                            MyApplication.saveLogin(null);
                            goToActivity(LoginActivity.class);
                            finish();
                        }
                    }
                }
            };
            httpUtils.addParam("userid", MyApplication.getLogin().getUserId());
            httpUtils.addParam("username", MyApplication.getLogin().getUserName());
            httpUtils.addParam("token", MyApplication.getLogin().getToken());
            httpUtils.clicent();
        }
    }

    private class MyBean {

        /**
         * status : 1
         */

        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
