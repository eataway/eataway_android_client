package com.australia.administrator.australiandelivery.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.comm.BaseActivity;
import com.australia.administrator.australiandelivery.utils.CipherUtils;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.view.TopBar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/6/12.
 */

public class LoginReleaseActivity extends BaseActivity implements OnSendMessageHandler {
    @Bind(R.id.tb_login_login)
    TopBar tbLoginLogin;
    @Bind(R.id.iv_login_login_logo)
    ImageView ivLoginLoginLogo;
    @Bind(R.id.tv_login_login_logo)
    TextView tvLoginLoginLogo;
    @Bind(R.id.iv_login_login_user)
    ImageView ivLoginLoginUser;
    @Bind(R.id.et_login_release_user)
    EditText etLoginReleaseUser;
    @Bind(R.id.rl_login_release_user)
    RelativeLayout rlLoginReleaseUser;
    @Bind(R.id.iv_login_release_code)
    ImageView ivLoginReleaseCode;
    @Bind(R.id.et_login_release_code)
    EditText etLoginReleaseCode;
    @Bind(R.id.tv_release_get_code)
    TextView tvReleaseGetCode;
    @Bind(R.id.rl_login_release_code)
    RelativeLayout rlLoginReleaseCode;
    @Bind(R.id.iv_login_release_p)
    ImageView ivLoginReleaseP;
    @Bind(R.id.et_login_release_p)
    EditText etLoginReleaseP;
    @Bind(R.id.rl_login_release_p)
    RelativeLayout rlLoginReleaseP;
    @Bind(R.id.iv_login_release_p_again)
    ImageView ivLoginReleasePAgain;
    @Bind(R.id.et_login_release_p_again)
    EditText etLoginReleasePAgain;
    @Bind(R.id.rl_login_release_p_again)
    RelativeLayout rlLoginReleasePAgain;
    @Bind(R.id.tv_login_release_login)
    TextView tvLoginReleaseLogin;
    @Bind(R.id.bt_login_release)
    Button btLoginRelease;
    @Bind(R.id.cb_release)
    CheckBox cbRelease;
    @Bind(R.id.tv_release_rerms)
    TextView tvReleaseRerms;
    @Bind(R.id.tv_release_english)
    TextView tvReleaseEnglish;
    @Bind(R.id.tv_release_chinese)
    TextView tvReleaseChinese;
    private EventHandler eventHandler;
    private boolean isCheck = true;
    private TimeCount time;
    private Intent i;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_release;
    }

    @Override
    protected void initDate() {
        tbLoginLogin.setTbLeftIv(R.mipmap.login_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tbLoginLogin.setTbCenterTv(R.string.release_my, R.color.black);
        tbLoginLogin.setBackGround(R.color.app_bg_topbar);
        tvReleaseRerms.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(final int event, int result, final Object data) {
                if (data instanceof Throwable) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Throwable throwable = (Throwable) data;
                            String msg = throwable.getMessage();
                            Toast.makeText(LoginReleaseActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        register();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                time = new TimeCount(60000, 1000);
                                time.start();
                                Toast.makeText(LoginReleaseActivity.this, R.string.get_the_captcha_successfully, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        };
        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
        cbRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck = isChecked;
                if (isChecked) {
                    btLoginRelease.setBackgroundResource(R.drawable.shape_person_order_green);
                    btLoginRelease.setClickable(true);
                }else {
                    btLoginRelease.setBackgroundResource(R.drawable.shape_white_gray);
                    btLoginRelease.setClickable(false);
                }
            }
        });
    }

    private void register() {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_RELEASE) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(LoginReleaseActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (status == 0) {
                        Toast.makeText(mContext, R.string.release_failed, Toast.LENGTH_SHORT).show();
                    } else if (status == 1) {
                        Toast.makeText(mContext, R.string.zhu_ce_cheng_gong, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginReleaseActivity.this, LoginLoginActivity.class));
                        finish();
                    } else if (status == 9) {
                        Toast.makeText(mContext, R.string.validation_fails, Toast.LENGTH_SHORT).show();
                    } else if (status == 2) {
                        Toast.makeText(mContext, R.string.The_phone_number_has_been_registered, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("mobile", etLoginReleaseUser.getText().toString()).addParams("password", CipherUtils.md5(etLoginReleaseP.getText().toString() + "EatAway"));
        httpUtils.clicent();
    }

    private void checkCode() {
        SMSSDK.submitVerificationCode("61", etLoginReleaseUser.getText().toString().trim(), etLoginReleaseCode.getText().toString().trim());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    private void initMssage() {
        SMSSDK.getVerificationCode("61", etLoginReleaseUser.getText().toString().trim(), this);
    }

    @OnClick({R.id.tv_login_release_login, R.id.bt_login_release, R.id.tv_release_get_code, R.id.tv_release_rerms, R.id.tv_release_english, R.id.tv_release_chinese})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_release_get_code:
                if (etLoginReleaseUser.getText().toString().trim().equals("")) {
                    Toast.makeText(this, R.string.please_enter_the_cell_phone_number, Toast.LENGTH_SHORT).show();
                    return;
                }
                initMssage();
                break;
            case R.id.bt_login_release:
                if (etLoginReleaseUser.getText().toString().trim().equals("")) {
                    Toast.makeText(this, R.string.please_enter_the_cell_phone_number, Toast.LENGTH_SHORT).show();
                    return;
                } else if (etLoginReleaseCode.getText().toString().trim().equals("")) {
                    Toast.makeText(this, R.string.please_enter_the_verification_code, Toast.LENGTH_SHORT).show();
                    return;
                } else if (etLoginReleaseP.getText().toString().trim().equals("")) {
                    Toast.makeText(this, R.string.please_enter_the_password, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!etLoginReleaseP.getText().toString().equals(etLoginReleasePAgain.getText().toString().trim())) {
                    Toast.makeText(this, R.string.the_password_input_is_inconsistent_twice, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!isCheck) {
                    return;
                } else if (etLoginReleaseP.getText().length()<6) {
                    Toast.makeText(this, R.string.mi_ma_chang_du, Toast.LENGTH_SHORT).show();
                } else {
                    checkCode();
                }
                break;
            case R.id.tv_release_rerms:
                break;
            case R.id.tv_release_english:
            case R.id.tv_release_chinese:
                break;
            case R.id.tv_login_release_login:
                startActivity(new Intent(LoginReleaseActivity.this, LoginLoginActivity.class));
                finish();
                break;
        }
    }

    @Override
    public boolean onSendMessage(String s, String s1) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        tvReleaseRerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(ProtocolActivity.class);
            }
        });
    }

    @OnClick(R.id.ll_activity_login_login_language)
    public void onClick() {
        if (MyApplication.isEnglish) {
            BaseActivity.setLanguage(false, this);
            MyApplication.isEnglish = false;
            Intent i = new Intent(this, LoginReleaseActivity.class);
            startActivity(i);
            finish();
        } else {
            BaseActivity.setLanguage(true, this);
            MyApplication.isEnglish = true;
            Intent i = new Intent(this, LoginReleaseActivity.class);
            startActivity(i);
            finish();
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvReleaseGetCode.setBackgroundResource(R.drawable.shape_gray_half_corner_sel);
            tvReleaseGetCode.setClickable(false);
            tvReleaseGetCode.setText(getResources().getText(R.string.chong_xin_fa_song) + "(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            tvReleaseGetCode.setText(getResources().getText(R.string.get_verification_code));
            tvReleaseGetCode.setClickable(true);
            tvReleaseGetCode.setBackgroundResource(R.drawable.shape_gray_half_corner_default);
        }
    }
}
