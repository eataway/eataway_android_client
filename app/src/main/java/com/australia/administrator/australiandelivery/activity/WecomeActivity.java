package com.australia.administrator.australiandelivery.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.utils.HXLoginUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import  com.australia.administrator.australiandelivery.comm.BaseActivity;

/**
 * Created by Administrator on 2017/7/9.
 */

public class WecomeActivity extends AppCompatActivity{
//     implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks
    private GoogleApiClient mGoogleApiClient;
    private boolean mLocationPermissionGranted;
    private Location mLastKnownLocation;
    private HXLoginUtils hxLoginUtils = new HXLoginUtils();
    private SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wecome_activity);
//        getDeviceLocation();
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */,
//                        this /* OnConnectionFailedListener */)
//                .addConnectionCallbacks(this)
//                .addApi(LocationServices.API)
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
//                .build();
//        mGoogleApiClient.connect();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                preferences = getSharedPreferences("lan_client",MODE_PRIVATE);
                if (preferences.getInt("isFirst",0)==1){  //中文
                    BaseActivity.setLanguage(false, WecomeActivity.this);
                    MyApplication.isEnglish = false;
                    if (MyApplication.getLogin() == null) {
                        startActivity(new Intent(WecomeActivity.this, LoginActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(WecomeActivity.this, MainActivity.class));
                        finish();
                    }
                } else if (preferences.getInt("isFirst",0)==2) {    //英文
                    BaseActivity.setLanguage(true, WecomeActivity.this);
                    MyApplication.isEnglish = true;
                    if (MyApplication.getLogin() == null) {
                        startActivity(new Intent(WecomeActivity.this, LoginActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(WecomeActivity.this, MainActivity.class));
                        finish();
                    }
                }else {
                    startActivity(new Intent(WecomeActivity.this, SelectLanActivity.class));
                    finish();
                }
//                BaseActivity.setLanguage(true, WecomeActivity.this);
//                MyApplication.isEnglish = true;
//                if (MyApplication.getLogin()!=null) hxLoginUtils.loginHX(WecomeActivity.this, MyApplication.getLogin().getUserId());
//                Intent intent = new Intent(WecomeActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        },2000);
    }

//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    private void getDeviceLocation() {
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            mLocationPermissionGranted = true;
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.RECORD_AUDIO},
//                    1);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String permissions[],
//                                           @NonNull int[] grantResults) {
//        mLocationPermissionGranted = false;
//        switch (requestCode) {
//            case 1: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    mLocationPermissionGranted = true;
//                }
//            }
//        }
//
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    private void getlat() {
//        mLastKnownLocation = getmLastKnownLocation();
//        if (mLastKnownLocation != null) {
//            MyApplication.mLocation = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
//        }
//
//    }
//
//    private Location getmLastKnownLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return  null;
//        }
//        mLastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        if (mLastKnownLocation==null){
//            getmLastKnownLocation();
//        }
//        return mLastKnownLocation;
//    }
}
