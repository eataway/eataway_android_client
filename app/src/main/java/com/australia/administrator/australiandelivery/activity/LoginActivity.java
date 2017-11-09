package com.australia.administrator.australiandelivery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.comm.BaseActivity;
import com.australia.administrator.australiandelivery.comm.Login;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.HXLoginUtils;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.utils.LoginApi;
import com.australia.administrator.australiandelivery.utils.OnLoginListener;
import com.australia.administrator.australiandelivery.utils.UserInfo;
import com.australia.administrator.australiandelivery.view.TopBar;
import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
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

public class LoginActivity extends BaseActivity {
    @Bind(R.id.tb_login_activity)
    TopBar tbLoginActivity;
    @Bind(R.id.iv_login_logo)
    ImageView ivLoginLogo;
    @Bind(R.id.bt_login)
    Button btLogin;
    @Bind(R.id.bt_login_release)
    Button btLoginRelease;
    @Bind(R.id.tv_login_face)
    TextView tvLoginFace;
    @Bind(R.id.rl_login_facebook)
    RelativeLayout rlLoginFacebook;
    @Bind(R.id.tv_login_weixin)
    TextView tvLoginWeixin;
    @Bind(R.id.rl_login_weixin)
    RelativeLayout rlLoginWeixin;
    private String openId;
    private String nickname;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initDate() {
        tbLoginActivity.setBackGround(R.color.white);
        tbLoginActivity.setTbLeftIv(R.mipmap.login_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.bt_login, R.id.bt_login_release, R.id.rl_login_facebook, R.id.rl_login_weixin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                startActivity(new Intent(this, LoginLoginActivity.class));
                finish();
                break;
            case R.id.bt_login_release:
                startActivity(new Intent(this, LoginReleaseActivity.class));
                finish();
                break;
            case R.id.rl_login_facebook:
                Platform fb = ShareSDK.getPlatform(Facebook.NAME);
                login(fb, "facebook");
                login(Facebook.NAME);
                break;
            case R.id.rl_login_weixin:
                Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
                login(weixin, "wechat");
//                login(Wechat.NAME);
                break;
        }
    }

    private void login(String platformName) {
        LoginApi api = new LoginApi();
        //设置登陆的平台后执行登陆的方法
        api.setPlatform(platformName);
        api.setOnLoginListener(new OnLoginListener() {
            public boolean onLogin(String platform, HashMap<String, Object> res) {
                // 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
                // 此处全部给回需要注册
                Toast.makeText(mContext, platform, Toast.LENGTH_SHORT).show();
                return true;
            }

            public boolean onRegister(UserInfo info) {
                // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
                return true;
            }
        });
        api.login(this);
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
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LoginLoginActivity.MyBean bean = new Gson().fromJson(response, LoginLoginActivity.MyBean.class);
                        Toast.makeText(mContext, bean.getStatus() + "login", Toast.LENGTH_SHORT).show();
                        if (bean.getStatus() == 2) {
                            ThreeRelease(s);
                        } else if (bean.getStatus() == 1) {
                            Login login = new Login();
                            login.setToken(bean.getToken());
                            login.setUserId(bean.getUserid());
                            Toast.makeText(mContext, bean.getUserid(), Toast.LENGTH_SHORT).show();
                            MyApplication.saveLogin(login);
                            HXLoginUtils hxLoginUtils = new HXLoginUtils();
                            hxLoginUtils.loginHX(LoginActivity.this, bean.getUserid());
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
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
//        platfrom.authorize();//单独授权,OnComplete返回的hashmap是空的
        platfrom.showUser(null);//授权并获取用户信息
        //移除授权
        platfrom.removeAccount(true);
    }

    private void ThreeRelease(String s) {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_THREE_RELEASE) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(mContext, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                LoginLoginActivity.MyBean bean = new Gson().fromJson(response, LoginLoginActivity.MyBean.class);
                if (bean.getStatus() == 1) {
                    Login login = new Login();
                    login.setToken(bean.getToken());
                    login.setUserId(bean.getUserid());
                    MyApplication.saveLogin(login);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(mContext, R.string.release_failed, Toast.LENGTH_SHORT).show();
                }
            }
        };
        httpUtils.addParam("openid", openId).addParams("username", nickname).addParams("type", s);
        httpUtils.clicent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ll_activity_login_language)
    public void onClick() {
        if (MyApplication.isEnglish) {
            BaseActivity.setLanguage(false, this);
            MyApplication.isEnglish = false;
            Intent i = new Intent(LoginActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }else {
            BaseActivity.setLanguage(true, this);
            MyApplication.isEnglish = true;
            Intent i = new Intent(LoginActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }
}
