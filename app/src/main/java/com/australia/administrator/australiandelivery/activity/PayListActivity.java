package com.australia.administrator.australiandelivery.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.view.TopBar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/10.
 */

public class PayListActivity extends AppCompatActivity {
    @Bind(R.id.tb_pay_list)
    TopBar tbPayList;
    @Bind(R.id.iv_yinhangka_left)
    ImageView ivYinhangkaLeft;
    @Bind(R.id.iv_yinghangka_right)
    ImageView ivYinghangkaRight;
    @Bind(R.id.iv_yinghangka_right1)
    ImageView ivYinghangkaRight1;
    @Bind(R.id.rl_yinhangka)
    RelativeLayout rlYinhangka;
    @Bind(R.id.iv_weixin_left)
    ImageView ivWeixinLeft;
    @Bind(R.id.rl_weixin)
    RelativeLayout rlWeixin;
    @Bind(R.id.iv_zhifubao_left)
    ImageView ivZhifubaoLeft;
    @Bind(R.id.rl_zhifubao)
    RelativeLayout rlZhifubao;
    private BraintreeFragment mBraintreeFragment;
    static final String EXTRA_PAYMENT_METHOD_NONCE = "payment_method_nonce";
    static final String EXTRA_DEVICE_DATA = "device_data";
    static final String EXTRA_COLLECT_DEVICE_DATA = "collect_device_data";
    static final String EXTRA_ANDROID_PAY_CART = "android_pay_cart";
    private String mAuthorization;
    private Intent i;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_list_activity);
        ButterKnife.bind(this);
        initTopBar();
        i = new Intent();
//        HttpUtils httpUtils = new HttpUtils(Contants.URL_GET_TOKEN) {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                Toast.makeText(PayListActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    mAuthorization = jsonObject.getString("token");
//                    mBraintreeFragment = BraintreeFragment.newInstance(PayListActivity.this, mAuthorization);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (InvalidArgumentException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        httpUtils.clicent();
    }
    @OnClick({R.id.rl_yinhangka, R.id.rl_weixin, R.id.rl_zhifubao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_yinhangka:
                i.putExtra("type",3);
                i.putExtra("payname",getString(R.string.xin_yong_ka));
                setResult(11,i);
                finish();
                break;
            case R.id.rl_weixin:

                break;
            case R.id.rl_zhifubao:

                break;
        }
    }

    public void onBraintreeSubmit() {
        DropInRequest dropInRequest = new DropInRequest().clientToken(mAuthorization);//.clientToken(clientToken)
        startActivityForResult(dropInRequest.getIntent(this), 4);
    }

    private void initTopBar() {
        tbPayList.setTbCenterTv(R.string.xuan_ze_zhi_fu_fang_shi, R.color.white);
        tbPayList.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 4) {
//            if (resultCode == Activity.RESULT_OK) {
//                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
//                // use the result to update your UI and send the payment method nonce to your server
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                // the user canceled
//            } else {
//                // handle errors here, an exception may be available in
//                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
//            }
//        }
//    }
}
