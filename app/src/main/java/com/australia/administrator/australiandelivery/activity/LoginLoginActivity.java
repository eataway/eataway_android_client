package com.australia.administrator.australiandelivery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.comm.BaseActivity;
import com.australia.administrator.australiandelivery.comm.Login;
import com.australia.administrator.australiandelivery.utils.CipherUtils;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.HXLoginUtils;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.view.TopBar;
import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/6/12.
 */

public class LoginLoginActivity extends BaseActivity {
    @Bind(R.id.tb_login_login)
    TopBar tbLoginLogin;
    @Bind(R.id.iv_login_login_logo)
    ImageView ivLoginLoginLogo;
    @Bind(R.id.tv_login_login_logo)
    TextView tvLoginLoginLogo;
    @Bind(R.id.iv_login_login_user)
    ImageView ivLoginLoginUser;
    @Bind(R.id.et_login_login_user)
    EditText etLoginLoginUser;
    @Bind(R.id.rl_login_login_user)
    RelativeLayout rlLoginLoginUser;
    @Bind(R.id.iv_login_login_p)
    ImageView ivLoginLoginP;
    @Bind(R.id.et_login_login_p)
    EditText etLoginLoginP;
    @Bind(R.id.rl_login_login_p)
    RelativeLayout rlLoginLoginP;
    @Bind(R.id.tv_login_forget)
    TextView tvLoginForget;
    @Bind(R.id.tv_login_login_release)
    TextView tvLoginLoginRelease;
    @Bind(R.id.bt_login_login)
    Button btLoginLogin;
    @Bind(R.id.tv_disanfang)
    TextView tvDisanfang;
    @Bind(R.id.rl_login_login_weixin)
    RelativeLayout rlLoginLoginWeixin;
    @Bind(R.id.rl_login_login_facebook)
    RelativeLayout rlLoginLoginFacebook;
    @Bind(R.id.ll_activity_login_login_language)
    LinearLayout llActivityLoginLoginLanguage;
    @Bind(R.id.rl_login_login_main)
    RelativeLayout rlLoginLoginMain;
    @Bind(R.id.rl_newsdetails)
    RelativeLayout rlNewsdetails;
    private String openId;
    private String nickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_login;
    }

    @Override
    protected void initDate() {
        initView();
    }

    private void initView() {
        tbLoginLogin.setTbLeftIv(R.mipmap.login_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginLoginActivity.this, LoginActivity.class));
                finish();
            }
        });
        tbLoginLogin.setTbCenterTv(R.string.login_my, R.color.black);
        tbLoginLogin.setBackGround(R.color.app_bg_topbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rlNewsdetails.getVisibility() == View.VISIBLE) {
            rlNewsdetails.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.tv_login_forget, R.id.tv_login_login_release, R.id.bt_login_login, R.id.rl_login_login_weixin, R.id.rl_login_login_facebook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_forget:
                goToActivity(ResetPwdActivity.class);
                break;
            case R.id.tv_login_login_release:
                startActivity(new Intent(this, LoginReleaseActivity.class));
                finish();
                break;
            case R.id.bt_login_login:
                if (etLoginLoginUser.getText().toString().equals("")) {
                    Toast.makeText(mContext, R.string.please_enter_the_cell_phone_number, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etLoginLoginP.getText().toString().equals("")) {
                    Toast.makeText(mContext, R.string.please_enter_the_password, Toast.LENGTH_SHORT).show();
                    return;
                }
                rlNewsdetails.setVisibility(View.VISIBLE);
                login();
                break;
            case R.id.rl_login_login_weixin:
                Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
                rlNewsdetails.setVisibility(View.VISIBLE);
                login(weixin, "wechat");
                break;
            case R.id.rl_login_login_facebook:
                Platform fb = ShareSDK.getPlatform(Facebook.NAME);
                rlNewsdetails.setVisibility(View.VISIBLE);
                login(fb, "facebook");
                break;
        }
    }

    private void login(final Platform platfrom, final String s) {
        if (platfrom.isAuthValid()) {
            platfrom.removeAccount(true);
        }
        platfrom.SSOSetting(false);  //设置false表示使用SSO授权方式
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        platfrom.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
                rlNewsdetails.setVisibility(View.GONE);
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //输出所有授权信息
                arg0.getDb().exportData();
                // 获取用户在此平台的ID
                openId = platfrom.getDb().getUserId();
                // 获取用户昵称
                nickname = platfrom.getDb().getUserName();
                HttpUtils httpUtils = new HttpUtils(Contants.URL_THREE_LOGIN) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                        rlNewsdetails.setVisibility(View.GONE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        rlNewsdetails.setVisibility(View.GONE);
                        MyBean bean = new Gson().fromJson(response, MyBean.class);
                        if (bean.getStatus() == 2) {
                            ThreeRelease(s);
                        } else if (bean.getStatus() == 1) {
                            Login login = new Login();
                            login.setToken(bean.getToken());
                            login.setUserId(bean.getUserid());
                            MyApplication.saveLogin(login);
                            HXLoginUtils hxLoginUtils = new HXLoginUtils();
                            hxLoginUtils.loginHX(LoginLoginActivity.this, bean.getUserid());
                            startActivity(new Intent(LoginLoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(mContext, R.string.login_failed, Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                httpUtils.addParam("openid", openId).addParams("username", nickname).addParams("type", s);
                httpUtils.clicent();
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub

            }
        });
        //authorize与showUser单独调用一个即可
        platfrom.authorize();//单独授权,OnComplete返回的hashmap是空的
        platfrom.showUser(null);//授权并获取用户信息
        //移除授权
        //weibo.removeAccount(true);
    }

    private void ThreeRelease(String s) {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_THREE_RELEASE) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(mContext, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                rlNewsdetails.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response, int id) {
                rlNewsdetails.setVisibility(View.GONE);
                MyBean bean = new Gson().fromJson(response, MyBean.class);
                if (bean.getStatus() == 1) {
                    Login login = new Login();
                    login.setToken(bean.getToken());
                    login.setUserId(bean.getUserid());
                    MyApplication.saveLogin(login);
                    HXLoginUtils hxLoginUtils = new HXLoginUtils();
                    hxLoginUtils.loginHX(MyApplication.context, bean.getUserid());
                    startActivity(new Intent(LoginLoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(mContext, R.string.release_failed, Toast.LENGTH_SHORT).show();
                }
            }
        };
        httpUtils.addParam("openid", openId).addParams("username", nickname).addParams("type", s);
        httpUtils.clicent();
    }

    private void login() {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_LOGIN) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(mContext, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                rlNewsdetails.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response, int id) {
                rlNewsdetails.setVisibility(View.GONE);
                MyBean bean = new Gson().fromJson(response, MyBean.class);
                if (bean.getStatus() == 1) {
                    Login login = new Login();
                    login.setToken(bean.getToken());
                    login.setUserId(bean.getUserid());
                    MyApplication.saveLogin(login);
                    HXLoginUtils hxLoginUtils = new HXLoginUtils();
                    hxLoginUtils.loginHX(LoginLoginActivity.this, bean.getUserid());
                    startActivity(new Intent(LoginLoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(mContext, R.string.login_failed, Toast.LENGTH_SHORT).show();
                }
            }
        };
        httpUtils.addParam("mobile", etLoginLoginUser.getText().toString()).addParams("password", CipherUtils.md5(etLoginLoginP.getText().toString() + "EatAway"));
        httpUtils.clicent();
    }

    @OnClick(R.id.ll_activity_login_login_language)
    public void onClick() {
        if (MyApplication.isEnglish) {
            BaseActivity.setLanguage(false, this);
            MyApplication.isEnglish = false;
            Intent i = new Intent(this, LoginLoginActivity.class);
            startActivity(i);
            finish();
        } else {
            BaseActivity.setLanguage(true, this);
            MyApplication.isEnglish = true;
            Intent i = new Intent(this, LoginLoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    class MyBean {

        /**
         * status : 0
         * userid :
         * token :
         */

        private int status;
        private String userid;
        private String token;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
