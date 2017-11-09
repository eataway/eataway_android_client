package com.australia.administrator.australiandelivery.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.comm.Login;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.view.TopBar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Project: EatAway
 * Author: ZhangHao
 * Date:    2017/6/28
 */

public class AddressReleaseActiviyty extends AppCompatActivity {
    @Bind(R.id.tb_address_release)
    TopBar tbAddressRelease;
    @Bind(R.id.et_add_address_name)
    EditText etAddAddressName;
    @Bind(R.id.rb_add_address_nan)
    RadioButton rbAddAddressNan;
    @Bind(R.id.rb_add_address_nv)
    RadioButton rbAddAddressNv;
    @Bind(R.id.rg_add_address)
    RadioGroup rgAddAddress;
    @Bind(R.id.et_add_address_phone)
    EditText etAddAddressPhone;
    @Bind(R.id.et_add_address_community)
    TextView etAddAddressCommunity;
    @Bind(R.id.et_add_address_num)
    EditText etAddAddressNum;
    private String gender="1";
    private String coordinate;
    Login login=MyApplication.getLogin();
    private String vicinity;
    private String lat_lon;
    private String url;
    private String addid;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_release);
        ButterKnife.bind(this);
        initData();
        tbAddressRelease.setBackGround(R.color.actionBar);
        tbAddressRelease.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rgAddAddress.check(R.id.rb_add_address_nan);
        rgAddAddress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_add_address_nan:
                        gender="1";
                        break;
                    case R.id.rb_add_address_nv:
                        gender="2";
                        break;
                }
            }
        });
        tbAddressRelease.setTbRightTv(R.string.save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                coordinate=login.getLongitude()+","+login.getLatitude();
                if (coordinate==null||coordinate.equals("")){
                    Toast.makeText(AddressReleaseActiviyty.this, R.string.shopping_address, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etAddAddressName.getText()==null||etAddAddressName.getText().toString().trim().equals("")){
                    Toast.makeText(AddressReleaseActiviyty.this, R.string.please_fill_in_the_consignee_name, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etAddAddressPhone.getText()==null|etAddAddressPhone.getText().toString().trim().equals("")){
                    Toast.makeText(AddressReleaseActiviyty.this, R.string.phone, Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpUtils httpUtils=new HttpUtils(url) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(AddressReleaseActiviyty.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status==1){
                                Toast.makeText(AddressReleaseActiviyty.this, R.string.bao_cun_cheng_gong, Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(AddressReleaseActiviyty.this, R.string.save_fall, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                httpUtils.addParam("userid", MyApplication.getLogin().getUserId()).addParams("token", MyApplication.getLogin().getToken())
                        .addParams("real_name",etAddAddressName.getText().toString())
                        .addParams("gender", gender).addParams("mobile",etAddAddressPhone.getText().toString())
                        .addParams("coordinate", coordinate).addParams("location_address",etAddAddressCommunity.getText().toString());
                        if(etAddAddressCommunity.getText().length()!=0){
                            httpUtils.addParam("detailed_address",etAddAddressNum.getText().toString());
                        }
                if (Contants.URL_EDITADDRESS.equals(url)) httpUtils.addParam("addid",addid);
                httpUtils.clicent();
            }
        });
        etAddAddressCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.get(AddressReleaseActiviyty.this, Manifest.permission.ACCESS_COARSE_LOCATION);
                startActivityForResult(new Intent(AddressReleaseActiviyty.this,MapActivity.class),10);
            }
        });
    }

    private void initData() {
        Intent i = getIntent();
        if (i.getStringExtra("coordinate")!=null && !"".equals(i.getStringExtra("coordinate"))) {
            etAddAddressName.setText(i.getStringExtra("name"));
            etAddAddressNum.setText(i.getStringExtra("weizhixiangxi"));
            etAddAddressCommunity.setText(i.getStringExtra("weizhiname"));
            etAddAddressPhone.setText(i.getStringExtra("phone"));
            coordinate = i.getStringExtra("coordinate");
            tbAddressRelease.setTbCenterTv(R.string.xiu_gai_di_zhi, R.color.white);
            addid = i.getStringExtra("addid");
            url = Contants.URL_EDITADDRESS;
        }else{
            tbAddressRelease.setTbCenterTv(R.string.new_shipping_address, R.color.white);
            url = Contants.URL_ADD_ADDRESS;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10){
            if (resultCode==9){
                vicinity = data.getStringExtra("vicinity");
                coordinate = data.getStringExtra("lat_lon");
                etAddAddressCommunity.setText(vicinity);
            }
        }
    }
}
