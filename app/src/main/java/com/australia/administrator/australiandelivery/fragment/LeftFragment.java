package com.australia.administrator.australiandelivery.fragment;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.activity.ChatActivity;
import com.australia.administrator.australiandelivery.activity.CurrentOrderActivity;
import com.australia.administrator.australiandelivery.activity.LoginActivity;
import com.australia.administrator.australiandelivery.activity.MainActivity;
import com.australia.administrator.australiandelivery.activity.PersonActivity;
import com.australia.administrator.australiandelivery.bean.UserBean;
import com.australia.administrator.australiandelivery.comm.BaseActivity;
import com.australia.administrator.australiandelivery.comm.Login;
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

/**
 * Created by Administrator on 2017/6/8.
 */

public class LeftFragment extends Fragment {
    @Bind(R.id.left_frame)
    FrameLayout leftFrame;
    @Bind(R.id.cimg_left_user_icon_default)
    CircleImageView cimgLeftUserIconDefault;
    @Bind(R.id.tv_left_user_info_login_username)
    TextView tvLeftUserInfoLoginUsername;
    @Bind(R.id.tv_left_user_info_login_userphone)
    TextView tvLeftUserInfoLoginUserphone;
    @Bind(R.id.ll_left_res)
    LinearLayout llLeftRes;
    @Bind(R.id.ll_left_order)
    LinearLayout llLeftOrder;
    @Bind(R.id.ll_left_person)
    LinearLayout llLeftPerson;
    @Bind(R.id.ll_left_custom)
    LinearLayout llLeftCustom;
    @Bind(R.id.btn_left_user_exit)
    Button btnLeftUserExit;
    @Bind(R.id.tv_left_chinese)
    TextView tvLeftChinese;
    @Bind(R.id.tv_left_en)
    TextView tvLeftEn;
    @Bind(R.id.left_line_03)
    View leftLine03;
    @Bind(R.id.left_line_04)
    View leftLine04;
    @Bind(R.id.ll_yuyan)
    LinearLayout llYuyan;
    private View mView;
    private UserBean bean;
    private Login login;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.lv_left_popwindow, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initData() {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_USER_PERSONALCENTER) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(getContext(), getResources().getString(R.string.please_check_your_network_connection), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("person", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int state = jsonObject.getInt("status");
                    if (state == 1) {
                        login = MyApplication.getLogin();
                        if (login == null) {
                            login = new Login();
                        }
                        UserBean bean = new Gson().fromJson(response, UserBean.class);
                        tvLeftUserInfoLoginUsername.setText(bean.getMsg().getUsername());
                        login.setUserName(bean.getMsg().getUsername());
                        tvLeftUserInfoLoginUserphone.setVisibility(View.VISIBLE);
                        tvLeftUserInfoLoginUserphone.setText(bean.getMsg().getMobile());
                        btnLeftUserExit.setVisibility(View.VISIBLE);
                        login.setPhone(bean.getMsg().getMobile());
                        if (bean.getMsg().getHead_photo() != null && !("").equals(bean.getMsg().getHead_photo())) {
                            GlideUtils.load(getContext(), bean.getMsg().getHead_photo(), cimgLeftUserIconDefault, GlideUtils.Shape.UserIcon);
                            login.setHead(bean.getMsg().getHead_photo());
                        MyApplication.saveLogin(login);
                        }
                    } else if (state == 9) {
                        Toast.makeText(getContext(), R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                        MyApplication.saveLogin(null);
                        cimgLeftUserIconDefault.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_user_icon_default));
                        tvLeftUserInfoLoginUsername.setText(getResources().getString(R.string.user_login_sign_up_default));
                        tvLeftUserInfoLoginUserphone.setVisibility(View.GONE);
                        btnLeftUserExit.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        if (MyApplication.getLogin() != null) {
            httpUtils.addParam("userid", MyApplication.getLogin().getUserId());
            httpUtils.addParam("token", MyApplication.getLogin().getToken());
            httpUtils.clicent();
        } else {
            cimgLeftUserIconDefault.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_user_icon_default));
            tvLeftUserInfoLoginUsername.setText(getResources().getString(R.string.user_login_sign_up_default));
            tvLeftUserInfoLoginUserphone.setVisibility(View.GONE);
            btnLeftUserExit.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.cimg_left_user_icon_default, R.id.tv_left_user_info_login_username, R.id.tv_left_user_info_login_userphone, R.id.ll_left_res, R.id.ll_left_order, R.id.ll_left_person, R.id.ll_left_custom, R.id.btn_left_user_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cimg_left_user_icon_default:
            case R.id.tv_left_user_info_login_username:
            case R.id.tv_left_user_info_login_userphone:
                if (MyApplication.getLogin() != null) {
                    goToActivity(PersonActivity.class);
                } else {
                    goToActivity(LoginActivity.class);
                }
                break;
            case R.id.ll_left_res:
                ((MainActivity)getContext()).menu.toggle();
                break;
            case R.id.ll_left_order:
                if (MyApplication.getLogin() != null) {
                    goToActivity(CurrentOrderActivity.class);
                } else {
                    goToActivity(LoginActivity.class);
                }
                break;
            case R.id.ll_left_person:
                if (MyApplication.getLogin() != null) {
                    goToActivity(PersonActivity.class);
                } else {
                    goToActivity(LoginActivity.class);
                }
                break;
            case R.id.ll_left_custom:
                if (MyApplication.getLogin() != null)
                    goToActivity(ChatActivity.class);
                else
                    goToActivity(LoginActivity.class);
                break;
            case R.id.btn_left_user_exit:
                HttpUtils httpUtils = new HttpUtils(Contants.URL_USER_LOGOUT) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                    }
                };
                httpUtils.addParam("userid", MyApplication.getLogin().getUserId());
                MyApplication.saveLogin(null);
                cimgLeftUserIconDefault.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_user_icon_default));
                tvLeftUserInfoLoginUsername.setText(getResources().getString(R.string.user_login_sign_up_default));
                tvLeftUserInfoLoginUserphone.setVisibility(View.GONE);
                btnLeftUserExit.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 辅助函数
     */

    //启动一个新的activity
    public void goToActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(MyApplication.context, activity);
        if (bundle != null) {
            intent.putExtra("data", bundle);
        }
        startActivity(intent);
    }

    public void goToActivity(Class activity) {
        Intent intent = new Intent(MyApplication.context, activity);
        startActivity(intent);
    }

    @OnClick(R.id.ll_yuyan)
    public void onClick() {
        if (MyApplication.isEnglish) {
            BaseActivity.setLanguage(false, getActivity());
            MyApplication.isEnglish = false;
            Intent i = new Intent(getContext(), MainActivity.class);
            startActivity(i);
            getActivity().finish();
        }else {
            BaseActivity.setLanguage(true, getActivity());
            MyApplication.isEnglish = true;
            Intent i = new Intent(getContext(), MainActivity.class);
            startActivity(i);
            getActivity().finish();
        }
    }
}
