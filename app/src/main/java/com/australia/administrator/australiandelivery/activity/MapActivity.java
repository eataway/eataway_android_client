package com.australia.administrator.australiandelivery.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.adapter.AddressAdapter;
import com.australia.administrator.australiandelivery.bean.AddressBean;
import com.australia.administrator.australiandelivery.comm.Login;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.utils.PermissionUtils;
import com.australia.administrator.australiandelivery.view.TopBar;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener,GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback{
    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    private static final String TAG = MapActivity.class.getSimpleName();

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    // The entry point to Google Play services, used by the Places API and Fused Location Provider.
    private GoogleApiClient mGoogleApiClient;

    private static final float ZOOM_DELTA = 2.0f;
    private static final float DEFAULT_MIN_ZOOM = 2.0f;
    private static final float DEFAULT_MAX_ZOOM = 22.0f;

    private String Location = "location=";
    private String Radius = "&radius=";

    private static final LatLng location = MyApplication.mLocation;

    //    private static final LatLngBounds ADELAIDE = new LatLngBounds(
//            new LatLng(-35.0, 138.58), new LatLng(-34.9, 138.61));
//    private static final CameraPosition ADELAIDE_CAMERA = new CameraPosition.Builder()
//            .target(new LatLng(-34.92873, 138.59995)).zoom(20.0f).bearing(0).tilt(0).build();
//
//    private static final LatLngBounds PACIFIC = new LatLngBounds(
//            new LatLng(-15.0, 165.0), new LatLng(15.0, -165.0));
//    private static final CameraPosition PACIFIC_CAMERA = new CameraPosition.Builder()
//            .target(new LatLng(0, -180)).zoom(4.0f).bearing(0).tilt(0).build();
    @Bind(R.id.tp_activity_map)
    TopBar tpActivityMap;
    @Bind(R.id.rl_activity_map_address)
    RecyclerView rlActivityMapAddress;

    private GoogleMap mMap;
    private AddressAdapter adapter;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    /**
     * Internal min zoom level that can be toggled via the demo.
     */
    private float mMinZoom;

    /**
     * Internal max zoom level that can be toggled via the demo.
     */
    private float mMaxZoom;

    private TextView mCameraTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        mMap = null;
        resetMinMaxZoom();


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initRecylerView();
        initTopBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        map.setOnCameraIdleListener(this);
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
    }

    private void initTopBar(){
        tpActivityMap.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tpActivityMap.setBackGround(R.color.actionBar);
        tpActivityMap.setTbCenterTv(R.string.xuan_ze_di_zhi, R.color.white);
        tpActivityMap.setTbRightIv(R.drawable.img_search_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
//                            .setBoundsBias(new LatLngBounds(new LatLng(mMap.getCameraPosition().target.latitude,mMap.getCameraPosition().target.longitude)))
                            .build(MapActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO:Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO:Handle the error.
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i("tag", "Place: " + place.getLatLng());
                Login login = MyApplication.getLogin();
                if (login != null){
                    login.setLocation_text(place.getName()+"");
                    login.setLatitude(place.getLatLng().latitude+"");
                    login.setLongitude(place.getLatLng().longitude+"");
                    MyApplication.saveLogin(login);
                    Intent i = new Intent();
//                    i.putExtra("vicinity", place.getName());
                    //change:
                    i.putExtra("vicinity", place.getName()+" "+ place.getAddress());
                    i.putExtra("lat_lon", place.getLatLng().longitude + "," +place.getLatLng().latitude);
                    setResult(9, i);
                    finish();
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO:Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void initRecylerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rlActivityMapAddress.setLayoutManager(manager);
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }


    @Override
    public void onCameraIdle() {
        LatLng target = mMap.getCameraPosition().target;
        String send = target.latitude + "," + target.longitude;
        String url = Contants.URL_GOOGLE + Location + send + Radius + "500" + MyApplication.GoogleKey;
        HttpUtils httpUtils = new HttpUtils(url) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(MapActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                AddressBean bean = new Gson().fromJson(response, AddressBean.class);
                if (adapter != null) {
                    adapter.setBean(bean);
                }else {
                    adapter = new AddressAdapter(MapActivity.this, bean,MapActivity.this);
                }
                rlActivityMapAddress.setAdapter(adapter);
            }
        };
        httpUtils.clicent();
    }

    /**
     * Before the map is ready many calls will fail.
     * This should be called on all entry points that call methods on the Google Maps API.
     */
    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void toast(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void resetMinMaxZoom() {
        mMinZoom = DEFAULT_MIN_ZOOM;
        mMaxZoom = DEFAULT_MAX_ZOOM;
    }

}
