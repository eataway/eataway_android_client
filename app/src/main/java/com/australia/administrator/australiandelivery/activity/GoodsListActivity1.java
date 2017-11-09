package com.australia.administrator.australiandelivery.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.adapter.ShopAdapter;
import com.australia.administrator.australiandelivery.adapter.TabFragmentAdapter;
import com.australia.administrator.australiandelivery.bean.ShopDetailsBean;
import com.australia.administrator.australiandelivery.comm.*;
import com.australia.administrator.australiandelivery.fragment.GoodsFragment;
import com.australia.administrator.australiandelivery.fragment.ShopDetailsFragment;
import com.australia.administrator.australiandelivery.fragment.TabFragment;
import com.australia.administrator.australiandelivery.ui.ShopToDetailListener;
import com.australia.administrator.australiandelivery.utils.AnimationUtil;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.GlideUtils;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.utils.NumberFormatUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/31.
 */

public class GoodsListActivity1 extends com.australia.administrator.australiandelivery.comm.BaseActivity {
    @Bind(R.id.iv_goods_list)
    ImageView ivGoodsList;
    @Bind(R.id.tb_activity_goods)
    Toolbar tbActivityGoods;
    @Bind(R.id.iv_activity_goods_back)
    ImageView ivActivityGoodsBack;
    @Bind(R.id.tb_goods_shop_name)
    TextView tbGoodsShopName;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private TabLayout slidingTabLayout;
    //fragment列表
    private List<Fragment> mFragments = new ArrayList<>();
    //tab名的列表
    private List<String> mTitles = new ArrayList<>();
    private ViewPager viewPager;
    private TabFragmentAdapter adapter;
    private TextView shopCartNum;
    private TextView totalPrice;
    private FrameLayout noShop;
    private RelativeLayout shopCartMain;
    private ViewGroup anim_mask_layout;//动画层
    private ShopDetailsBean bean;
    private String id;
    private TextView defaultText;
    private LinearLayout cardShopLayout;
    private View bg_layout;
    private ListView shoppingListView;
    private ShopAdapter shopAdapter;
    private TextView settlement;
    private ImageView shopping_cart;
    private GoodsFragment goodsFragment;

    //新增
    private TextView tvMinPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
        id = getIntent().getStringExtra("id");
        initViews();
        initView();
        setCollsapsing();
        setSupportActionBar(tbActivityGoods);
        ivActivityGoodsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean> cart = goodsFragment.getCart();
                if (goodsFragment.getCart().isEmpty() || goodsFragment.getCart() == null) {
                    defaultText.setVisibility(View.VISIBLE);
                } else {
                    defaultText.setVisibility(View.GONE);
                    shopAdapter = new ShopAdapter(GoodsListActivity1.this, goodsFragment.getCart());
                    shoppingListView.setAdapter(shopAdapter);
                    shopAdapter.setShopToDetailListener(new ShopToDetailListener() {
                        @Override
                        public void onUpdateDetailList(ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean product, String type) {
                            goodsFragment.setJia(product.getGoodsid(),type);
                        }

                        @Override
                        public void onRemovePriduct(ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean product) {
                            goodsFragment.setJian(product.getGoodsid());
                        }
                    });
                }

                if (noShop.getVisibility() == View.GONE) {

                    noShop.setVisibility(View.VISIBLE);
                    // 加载动画
                    Animation animation = AnimationUtils.loadAnimation(GoodsListActivity1.this, R.anim.push_bottom_in);
                    // 动画开始
                    cardShopLayout.setVisibility(View.VISIBLE);
                    cardShopLayout.startAnimation(animation);
                    bg_layout.setVisibility(View.VISIBLE);

                } else {
                    noShop.setVisibility(View.GONE);
                    bg_layout.setVisibility(View.GONE);
                    cardShopLayout.setVisibility(View.GONE);
                }
            }
        });

        bg_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noShop.setVisibility(View.GONE);
                bg_layout.setVisibility(View.GONE);
                cardShopLayout.setVisibility(View.GONE);
            }
        });

        settlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goodsFragment.getCart() == null || goodsFragment.getCart().size() == 0) {
                    return;
                }
                MyApplication.goods = null;
                Intent intent = new Intent(GoodsListActivity1.this, SubmitOrdersActivity.class);
                intent.putExtra("shopid", id);
                intent.putExtra("juli", bean.getMsg().getLkmoney());
                intent.putExtra("long", bean.getMsg().getLongX());
                intent.putExtra("maxlong", bean.getMsg().getMaxlong());
                intent.putExtra("maxprice", bean.getMsg().getMaxprice());
                intent.putExtra("shopicon", bean.getMsg().getShophead());
                intent.putExtra("shopname", bean.getMsg().getShopname());
                MyApplication.goods = goodsFragment.getCart();
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_MENULIST) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(GoodsListActivity1.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                bean = new Gson().fromJson(response, ShopDetailsBean.class);
                tbGoodsShopName.setText(bean.getMsg().getShopname());
                if (bean.getMsg().getLmoney()>0) {
                    tvMinPrice.setVisibility(View.VISIBLE);
                    if(MyApplication.isEnglish) {
                        tvMinPrice.setText("$"+bean.getMsg().getLmoney()+" MIN, REMAINING $"+bean.getMsg().getLmoney());
                    }else {
                        tvMinPrice.setText("最低$"+bean.getMsg().getLmoney()+" , 还差$"+bean.getMsg().getLmoney());
                    }
                }else {
                    tvMinPrice.setVisibility(View.GONE);
                }
                setViewPager();
            }
        };
        httpUtils.addParam("shopid", id);
        httpUtils.clicent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods1;
    }

    @Override
    protected void initDate() {

    }

    private void initView() {
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        slidingTabLayout = (TabLayout) findViewById(R.id.slidinglayout);
        viewPager = (ViewPager) findViewById(R.id.vp);
        shopCartMain = (RelativeLayout) findViewById(R.id.shopCartMain);
        shopCartNum = (TextView) findViewById(R.id.shopCartNum);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        noShop = (FrameLayout) findViewById(R.id.noShop);
        defaultText = (TextView) findViewById(R.id.defaultText);
        cardShopLayout = (LinearLayout) findViewById(R.id.cardShopLayout);
        bg_layout = findViewById(R.id.bg_layout);
        shoppingListView = (ListView) findViewById(R.id.shopproductListView);
        settlement = (TextView) findViewById(R.id.settlement);
        shopping_cart = (ImageView) findViewById(R.id.shopping_cart);
        tvMinPrice = (TextView) findViewById(R.id.minPrice);
    }

    private void setViewPager() {
        GlideUtils.load(this, bean.getMsg().getShopphoto(), ivGoodsList, GlideUtils.Shape.ShopPic);
        goodsFragment = GoodsFragment.newInstance(id, bean, this);
        TabFragment tabFragment = TabFragment.newInstance(id, bean, this);
        ShopDetailsFragment shopDetailsFragment = ShopDetailsFragment.newInstance(id, bean);
        mFragments.add(goodsFragment);
        mFragments.add(tabFragment);
        mFragments.add(shopDetailsFragment);
        mTitles.add(getString(R.string.shang_pin));
        mTitles.add(getString(R.string.ping_jia));
        mTitles.add(getString(R.string.xiang_qing));
        adapter = new TabFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        shopCartMain.startAnimation(AnimationUtil.createInAnimation(GoodsListActivity1.this, shopCartMain.getMeasuredHeight()));
                        break;
                    case 1:
                        shopCartMain.startAnimation(AnimationUtil.createOutAnimation(GoodsListActivity1.this, shopCartMain.getMeasuredHeight()));
                        break;
                    case 2:
                        shopCartMain.startAnimation(AnimationUtil.createOutAnimation(GoodsListActivity1.this, shopCartMain.getMeasuredHeight()));
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void setCollsapsing() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.touming));
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 添加 或者  删除  商品发送的消息处理
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event != null) {
            shopCartNum.setText(String.valueOf(event.num));
            shopCartNum.setVisibility(View.VISIBLE);
            totalPrice.setVisibility(View.VISIBLE);
//                noShop.setVisibility(View.GONE);
            event.price = NumberFormatUtil.round(event.price, 2);
            totalPrice.setText("$" + event.price);
            tvMinPrice.setVisibility(View.VISIBLE);

            //新增：(如何判断该条件不存在)
            double sub = bean.getMsg().getLmoney() - event.price;
            if (sub > 0) {
                sub = NumberFormatUtil.round(sub, 2);
                tvMinPrice.setVisibility(View.VISIBLE);
                settlement.setBackgroundResource(R.color.list_line);
                settlement.setClickable(false);
                if(MyApplication.isEnglish) {
                    tvMinPrice.setText("$"+bean.getMsg().getLmoney()+" MIN, REMAINING $"+sub);
                }else {
                    tvMinPrice.setText("最低$"+bean.getMsg().getLkmoney()+" , 还差$"+sub);
                }
            }else {
                tvMinPrice.setVisibility(View.GONE);
                settlement.setBackgroundResource(R.color.color_orange);
                settlement.setClickable(true);
            }


            Log.v("NewTabActivity", "添加的数量：" + event.goods.size());
        }

    }


    /**
     * 设置动画（点击添加商品）
     *
     * @param v
     * @param startLocation
     */
    public void setAnim(final View v, int[] startLocation) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v, startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        shopCartNum.getLocationInWindow(endLocation);
        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标

        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(400);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });

    }

    /**
     * 初始化动画图层
     *
     * @return
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE - 1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * 将View添加到动画图层
     *
     * @param parent
     * @param view
     * @param location
     * @return
     */
    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
