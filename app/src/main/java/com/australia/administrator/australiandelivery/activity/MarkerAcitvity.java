package com.australia.administrator.australiandelivery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.comm.*;
import com.australia.administrator.australiandelivery.view.TopBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;

public class MarkerAcitvity extends com.australia.administrator.australiandelivery.comm.BaseActivity implements OnMapReadyCallback {

    @Bind(R.id.tp_marker)
    TopBar tpMarker;
    private double la, lo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_marker_acitvity;
    }

    @Override
    protected void initDate() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Intent i = getIntent();
        String sla = i.getStringExtra("la");
        String slo = i.getStringExtra("lo");
        if (!"".equals(la) || !"".equals(lo)) {
            la = Double.parseDouble(sla);
            lo = Double.parseDouble(slo);
            googleMap.addMarker(new MarkerOptions().position(new LatLng(lo, la))
                    .icon(BitmapDescriptorFactory.defaultMarker()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lo, la), 15.0f));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tpMarker.setTbCenterTv(R.string.shang_jia_wei_zhi, R.color.white);
        tpMarker.setTbLeftIv(R.drawable.img_back_icon_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
