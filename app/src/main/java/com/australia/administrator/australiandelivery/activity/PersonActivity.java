package com.australia.administrator.australiandelivery.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.bean.UserBean;
import com.australia.administrator.australiandelivery.comm.BaseActivity;
import com.australia.administrator.australiandelivery.comm.Login;
import com.australia.administrator.australiandelivery.ui.CustomDialog;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.GlideUtils;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

public class PersonActivity extends BaseActivity {
    @Bind(R.id.img_person_back_icon)
    ImageView imgPersonBackIcon;
    @Bind(R.id.cimg_person_user_icon)
    CircleImageView cimgPersonUserIcon;
    @Bind(R.id.tv_person_user_name)
    TextView tvPersonUserName;
    @Bind(R.id.tv_person_user_phone)
    TextView tvPersonUserPhone;
    @Bind(R.id.rl_person_user_info)
    RelativeLayout rlPersonUserInfo;
    @Bind(R.id.img_person_address_icon)
    ImageView imgPersonAddressIcon;
    @Bind(R.id.rl_person_address)
    RelativeLayout rlPersonAddress;
    @Bind(R.id.img_person_order_icon)
    ImageView imgPersonOrderIcon;
    @Bind(R.id.rl_person_order)
    RelativeLayout rlPersonOrder;
    @Bind(R.id.img_person_joinus_icon)
    ImageView imgPersonJoinusIcon;
    @Bind(R.id.rl_person_joinus)
    RelativeLayout rlPersonJoinus;
    @Bind(R.id.img_person_advice_icon)
    ImageView imgPersonAdviceIcon;
    @Bind(R.id.rl_person_advice)
    RelativeLayout rlPersonAdvice;
    @Bind(R.id.img_person_share_icon)
    ImageView imgPersonShareIcon;
    @Bind(R.id.rl_person_share)
    RelativeLayout rlPersonShare;
    @Bind(R.id.img_person_aboutus_icon)
    ImageView imgPersonAboutusIcon;
    @Bind(R.id.rl_person_aboutus)
    RelativeLayout rlPersonAboutus;

    private Bundle bundle = new Bundle();
    private Login login = MyApplication.getLogin();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_person;
    }

    @Override
    protected void initDate() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (MyApplication.getLogin() != null) {
            HttpUtils httpUtils = new HttpUtils(Contants.URL_USER_PERSONALCENTER) {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Toast.makeText(PersonActivity.this, getResources().getString(R.string.please_check_your_network_connection), Toast.LENGTH_SHORT).show();
                    tvPersonUserName.setText(getString(R.string.user_login_sign_up_default));
                    tvPersonUserPhone.setVisibility(View.GONE);
                }

                @Override
                public void onResponse(String response, int id) {
                    Log.i("person",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int state = jsonObject.getInt("status");
                        if (state == 1) {
                            UserBean bean = new Gson().fromJson(response, UserBean.class);
                            if (bean != null) {
                                tvPersonUserName.setText(bean.getMsg().getUsername());
                                login.setUserName(bean.getMsg().getUsername());
                                tvPersonUserPhone.setVisibility(View.VISIBLE);
                                tvPersonUserPhone.setText(bean.getMsg().getMobile());
                                login.setPhone(bean.getMsg().getMobile());
                                if (bean.getMsg().getHead_photo() != null && !("").equals(bean.getMsg().getHead_photo())) {
                                    GlideUtils.load(PersonActivity.this, bean.getMsg().getHead_photo(), cimgPersonUserIcon, GlideUtils.Shape.UserIcon);
                                    login.setHead(bean.getMsg().getHead_photo());
                                }
                                MyApplication.saveLogin(login);
                            }
                        }else if (state == 9) {
                            Toast.makeText(PersonActivity.this, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                            MyApplication.saveLogin(null);
                            goToActivity(LoginActivity.class);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            httpUtils.addParam("userid", MyApplication.getLogin().getUserId());
            httpUtils.addParam("token", MyApplication.getLogin().getToken());
            httpUtils.clicent();
        }else {
            finish();
        }
    }

    @OnClick({R.id.cimg_person_user_icon, R.id.tv_person_user_name, R.id.rl_person_order,
            R.id.tv_person_user_phone, R.id.rl_person_address, R.id.rl_person_joinus,
            R.id.rl_person_advice, R.id.rl_person_aboutus, R.id.img_person_back_icon, R.id.rl_person_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cimg_person_user_icon:
            case R.id.tv_person_user_name:
            case R.id.tv_person_user_phone:
                goToActivity(PersonUserInfoActivity.class);
                break;
            case R.id.rl_person_address:
                Intent i = new Intent(this, AddressActivity.class);
                i.putExtra("type", "1");
                startActivity(i);
                break;
            case R.id.rl_person_order:
                goToActivity(PersonOrderActivity.class);
                break;
            case R.id.rl_person_joinus:
                goToActivity(JoinUsActivity.class);
                break;
            case R.id.rl_person_advice:
                goToActivity(FeedBackActivity.class);
                break;
            case R.id.rl_person_share:
                CustomDialog dialog = new CustomDialog(this, R.style.ShareDialog);
                dialog.show();
                break;
            case R.id.rl_person_aboutus:
                goToActivity(AboutUsActivity.class);
                break;
            case R.id.img_person_back_icon:
                finish();
                break;
        }
    }
}
