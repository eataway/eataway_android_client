package com.australia.administrator.australiandelivery.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.utils.CipherUtils;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.view.TopBar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;

public class ResetPwdActivity extends com.australia.administrator.australiandelivery.comm.BaseActivity implements OnSendMessageHandler {
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
    @Bind(R.id.bt_login_release)
    Button btLoginRelease;
    private EventHandler eventHandler;
    private TimeCount time;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    protected void initDate() {
        tbLoginLogin.setTbLeftIv(R.mipmap.login_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tbLoginLogin.setTbCenterTv(R.string.chong_zhi_mi_ma, R.color.black);
        tbLoginLogin.setBackGround(R.color.app_bg_topbar);
        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(final int event, int result, final Object data) {
                if (data instanceof Throwable) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Throwable throwable = (Throwable) data;
                            String msg = throwable.getMessage();
                            Toast.makeText(ResetPwdActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        resetpwd();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                time = new TimeCount(60000,1000);
                                time.start();
                                Toast.makeText(ResetPwdActivity.this, R.string.get_the_captcha_successfully, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        };
        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }

    private void resetpwd() {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_RESETPWD) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(ResetPwdActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (status == 0) {
                        Toast.makeText(mContext, R.string.xiu_gai_shi_bai, Toast.LENGTH_SHORT).show();
                    } else if (status == 1) {
                        Toast.makeText(mContext, R.string.xiu_gai_cheng_gong, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPwdActivity.this, LoginLoginActivity.class));
                        finish();
                    } else if (status == 9) {
                        Toast.makeText(mContext, R.string.validation_fails, Toast.LENGTH_SHORT).show();
                    } else if (status == 2) {
                        Toast.makeText(mContext, R.string.gai_shou_ji_wei_zhu_ce, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("mobile", etLoginReleaseUser.getText().toString()).addParams("password", CipherUtils.md5(etLoginReleaseP.getText().toString()+"EatAway"));
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

    @OnClick({R.id.bt_login_release, R.id.tv_release_get_code})
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
                } else if (etLoginReleaseP.getText().length()<6) {
                    Toast.makeText(this, R.string.mi_ma_chang_du, Toast.LENGTH_SHORT).show();
                } else {
                    checkCode();
                }
                break;
        }
    }

    @Override
    public boolean onSendMessage(String s, String s1) {
        return false;
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
