package com.australia.administrator.australiandelivery.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.adapter.MainPageAdapter;
import com.australia.administrator.australiandelivery.bean.ShopBean;
import com.australia.administrator.australiandelivery.comm.BaseActivity;
import com.australia.administrator.australiandelivery.comm.Login;
import com.australia.administrator.australiandelivery.fragment.LeftFragment;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.HXLoginUtils;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.utils.MyClassOnclik;
import com.australia.administrator.australiandelivery.utils.ToastUtils;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class MainActivity extends BaseActivity {

    @Bind(R.id.iv_left_main)
    ImageView ivLeftMain;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.sort_default)
    public TextView sortDefault;
    @Bind(R.id.sort_recommend)
    public TextView sortRecommend;
    @Bind(R.id.sort_distance)
    public TextView sortDistance;
    @Bind(R.id.sort_sale)
    public TextView sortSale;
    @Bind(R.id.rv_main)
    RecyclerView rvMain;
    @Bind(R.id.content_frame)
    FrameLayout contentFrame;
    @Bind(R.id.lv_sortbar)
    LinearLayout lvSortbar;
    @Bind(R.id.er_refresh)
    EasyRefreshLayout erRefresh;
    @Bind(R.id.rl_search_bar)
    RelativeLayout rlSearchBar;

    private MainPageAdapter mainPageAdapter;
    public SlidingMenu menu;
    private View.OnClickListener listener;
    private int scrollOffset = 0;
    private ShopBean shopBean;

    //当前页页码：
    private int currentPage = 1;
    private boolean isScroll = true;
    private int num=0;

    private LinearLayoutManager manager;
    private final int REQUEST_CODE = 10086;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        //初始化页面
        initMainRecylerView();
        initClickListener();
        initSlidingMenu();
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getLogin() != null) {
                    Intent i = new Intent(MainActivity.this, AddressActivity.class);
                    i.putExtra("type", "3");
                    startActivity(i);
                }else {
                    goToActivity(LoginActivity.class);
                }
            }
        });
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.RECORD_AUDIO},
                REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        loadData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }else {
                    ToastUtils.showToast(R.string.huo_qu_lu_yin_quan_xian, this);
                }
            }
        }

    }

    /**
     * 初始化函数
     */
    private void initSlidingMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.color.colorAccent);

        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
//        if (mainPageAdapter.banner != null) menu.addIgnoredView(mainPageAdapter.banner);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT, false);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.lv_left_popwindow);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_frame, new LeftFragment()).commit();
    }

    private void initMainRecylerView() {
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setAutoMeasureEnabled(true);
        rvMain.setLayoutManager(manager);
//        loadData();
        mainPageAdapter = new MainPageAdapter(this, shopBean, new MyClassOnclik() {
            @Override
            public void setRefreshing(int num,int page) {
                MainActivity.this.num = num;
                MainActivity.this.currentPage=page;
                loadData();
            }
        });
        rvMain.setAdapter(mainPageAdapter);
        erRefresh.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                        erRefresh.loadMoreComplete();
                    }
                });
            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentPage=1;
                        loadData();
                        erRefresh.refreshComplete();
                    }
                }, 200);
            }
        });
        rvMain.addOnScrollListener(new EndLoadOnScrollListener());
    }

    private void initClickListener() {
        rlSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(SearchActivity.class);
            }
        });
        listener = mainPageAdapter.getSortBarListener();
        sortDefault.setOnClickListener(listener);
        sortDistance.setOnClickListener(listener);
        sortRecommend.setOnClickListener(listener);
        sortSale.setOnClickListener(listener);
    }

    /**
     * 辅助函数
     */
    private void loadData() {
        HttpUtils http = new HttpUtils(Contants.URL_SHOPLIST) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(MainActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                shopBean = new Gson().fromJson(response,ShopBean.class);
                if (shopBean != null && shopBean.getMsg().size() != 0){
                    if (currentPage > 1){
                        mainPageAdapter.addBean(shopBean);
                        mainPageAdapter.notifyDataSetChanged();
                    }else {
                        mainPageAdapter.setShopBean(shopBean);
                    }
                    currentPage ++;
                }else if (currentPage!=1){
//                    Toast.makeText(mContext, R.string.temporarily_no_data, Toast.LENGTH_SHORT).show();
                    ToastUtils.showToast(R.string.temporarily_no_data, mContext);
                }
            }
        };
        http.addParam("page",currentPage+"").addParams("num",num+"").addParams("coor",MyApplication.mLocation.longitude+","+MyApplication.mLocation.latitude);
        if (num==2||num==3){
            if (currentPage==1){
                http.addParam("row","0");
            }else {
                http.addParam("row",(currentPage-1)*15+"");
            }
        }else {
            http.addParam("row","");
        }
        http.clicent();
    }

    /**
     * 辅助类
     */
    //监听滑动距离判断是否令首页样式改变
    private class EndLoadOnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //首页样式判断
//            scrollOffset += dy;
            Log.i("taa", "onScrolled: "+getScollYDistance()+" "+scrollOffset+" " + rvMain.getTop()+" " +recyclerView.computeVerticalScrollOffset());
            if (recyclerView.computeVerticalScrollOffset() >= mainPageAdapter.BannerHeight) {
                lvSortbar.setVisibility(View.VISIBLE);
                lvSortbar.bringToFront();
            } else {
                lvSortbar.setVisibility(View.GONE);
            }
        }
    }

    public int getScollYDistance() {
        int position = manager.findFirstVisibleItemPosition();
        View firstVisiableChildView = manager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }

    @OnClick(R.id.iv_left_main)
    public void onClick() {
        menu.toggle();
    }

    private long exitTime = 0;

    /**
     * 再按一次退出程序（间隔2秒之后再按返回才会提示）
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                if (MyApplication.isEnglish) {
                    Toast.makeText(getApplicationContext(), "Press again to exit the program",
                            Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序",
                            Toast.LENGTH_SHORT).show();
                }
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
