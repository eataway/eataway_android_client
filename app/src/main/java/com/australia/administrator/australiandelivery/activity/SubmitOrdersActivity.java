package com.australia.administrator.australiandelivery.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.adapter.OrdersAdapter;
import com.australia.administrator.australiandelivery.alipay.PayResult;
import com.australia.administrator.australiandelivery.bean.ShopDetailsBean;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.GlideUtils;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.utils.NumberFormatUtil;
import com.australia.administrator.australiandelivery.view.TopBar;
import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/8.
 */

public class SubmitOrdersActivity extends com.australia.administrator.australiandelivery.comm.BaseActivity {
    private  final int BANK = 1, ALIPAY = 2, WECHAT = 3;

    @Bind(R.id.tb_submit_orders)
    TopBar tbSubmitOrders;
    @Bind(R.id.tv_weizhi)
    TextView tvWeizhi;
    @Bind(R.id.tv_submit_name)
    TextView tvSubmitName;
    @Bind(R.id.tv_submit_sex)
    TextView tvSubmitSex;
    @Bind(R.id.tv_submit_phone)
    TextView tvSubmitPhone;
    @Bind(R.id.iv_orders_shop)
    CircleImageView ivOrdersShop;
    @Bind(R.id.tv_orders_shop_name)
    TextView tvOrdersShopName;
    @Bind(R.id.rv_orders)
    RecyclerView rvOrders;
    @Bind(R.id.tv_orders_delivery)
    TextView tvOrdersDelivery;
    @Bind(R.id.tv_orders_dress)
    TextView tvOrdersDress;
    @Bind(R.id.tv_orders_money)
    TextView tvOrdersMoney;
    @Bind(R.id.tv_orders_taste_notes)
    TextView tvOrdersTasteNotes;
    @Bind(R.id.tv_orders_method_of_payment)
    TextView tvOrdersMethodOfPayment;
    @Bind(R.id.shopping_cart)
    TextView shoppingCart;
    @Bind(R.id.shoppingPrise)
    TextView shoppingPrise;
    @Bind(R.id.settlement)
    TextView settlement;
    @Bind(R.id.toolBar)
    LinearLayout toolBar;
    @Bind(R.id.ll_weizhi)
    LinearLayout llWeizhi;
    @Bind(R.id.tv_orders_time)
    TextView tvOrdersTime;
    @Bind(R.id.rl_orders_pay)
    RelativeLayout rlOrdersPay;
    @Bind(R.id.list_line)
    View listLine;
    private BraintreeFragment mBraintreeFragment;
    private String mAuthorization;
    private int hour;
    private int minute;
    private List<ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean> goods;
    private OrdersAdapter adapter;
    private String name;
    private String phone;
    private String sex;
    private String weizhi;
    private String id;
    private String coordinate;
    private String shopid;
    private String price;
    private double price1;
    private TimePickerDialog dialog;

    private String format1 = "0";
    private boolean isp = true;
    private String am="am";
    private int pay = 4;//支付方式：4货到付款3银行卡、2支付宝、1微信
    private boolean canOrder = false;
    private String juli;

    //一千米多少钱
    private double feeForLk;
    //超过多少钱时免费
    private double freeMoney;
    //多少千米多少钱
    private double feeForKm;
    private double feeForMoney;

    //商家的最大配送距离
    private double maxX;
    //实际计算的距离
    private double peisong;
    //计算的配送费
    private double psmoney;
    //计算的总价格
    private double calmoney;

    //新增，此页无用，最低起送价格
    private String minprice;

    private String nonce;


    //支付宝支付相关：
    // 商户PID
    public static final String PARTNER = "2088721824853457";
    // 商户收款账号
    public static final String SELLER = "2088721824853457";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;

    private static String order_num = "";

    private String sign = "";

    private String orderInfo = "";

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(SubmitOrdersActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(SubmitOrdersActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(SubmitOrdersActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    /**
     * call alipay sdk pay. 调用SDK支付
     *
     */
    public void pay(View v, String orderInfo) {
//        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
//            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialoginterface, int i) {
//                            //
//                            finish();
//                        }
//                    }).show();
//            return;
//        }
//        String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");
//
//        /**
//         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
//         */
//        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(SubmitOrdersActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     *
     */
    private String getOrderInfo(String subject, String body, String price, String orderNum) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderNum + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://wm.sawfree.com/index.php/home/order/backurl" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";
        //global pay special parameters
        orderInfo += "&currency=\"USD\"";
        orderInfo += "&forex_biz=\"FP\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    private String getOutTradeNo() {
//        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
//        Date date = new Date();
        String key = System.currentTimeMillis() + phone;

//        Random r = new Random();
//        key = key + r.nextInt();
//        key = key.substring(0, 15);
        return key;
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_submit_orders;
    }

    @Override
    protected void initDate() {
        goods = MyApplication.goods;
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
                super.onMeasure(recycler, state, widthSpec, heightSpec);
                if (getChildCount() > 0) {
                    View firstChildView = recycler.getViewForPosition(0);
                    measureChild(firstChildView, widthSpec, heightSpec);
                    setMeasuredDimension(View.MeasureSpec.getSize(widthSpec), firstChildView.getMeasuredHeight() * getChildCount());
                } else {
                    super.onMeasure(recycler, state, widthSpec, heightSpec);
                }
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        rvOrders.setLayoutManager(manager);
        rvOrders.setNestedScrollingEnabled(false);
        rvOrders.setHasFixedSize(true);
        adapter = new OrdersAdapter(this, goods);
        rvOrders.setAdapter(adapter);
        for (int i = 0; i < goods.size(); i++) {
            price1 += Double.parseDouble(goods.get(i).getGoodsprice()) * goods.get(i).getNumber();
        }
        DecimalFormat format = new DecimalFormat("#.00");
        price = format.format(price1);
        tvOrdersMoney.setText("$" + price);
        shoppingPrise.setText("$" + price);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        shopid = getIntent().getStringExtra("shopid");
        GlideUtils.load(this, getIntent().getStringExtra("shopicon"), ivOrdersShop, GlideUtils.Shape.ShopIcon);
        tvOrdersShopName.setText(getIntent().getStringExtra("shopname"));
        initTopBar();
    }

    private void initTopBar() {
        tbSubmitOrders.setTbCenterTv(R.string.ti_jiao_ding_dan_title, R.color.white);
        tbSubmitOrders.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.tv_weizhi, R.id.ll_weizhi, R.id.tv_orders_time, R.id.rl_orders_pay, R.id.settlement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_weizhi:
            case R.id.ll_weizhi:
                if (MyApplication.getLogin() == null) {
                    Toast.makeText(mContext, R.string.login_agin, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SubmitOrdersActivity.this, AddressActivity.class);
                intent.putExtra("type", "2");
                startActivityForResult(intent, 0);
                break;
            case R.id.tv_orders_time:
                dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay>12){
                            hourOfDay=hourOfDay-12;
                            am="pm";
                        }else {
                            am="am";
                        }
                        tvOrdersTime.setText(getString(R.string.the_delivery_time1) + hourOfDay + ":" + minute+" "+am);
                    }
                }, hour, minute, false);
                dialog.show();
                break;
            case R.id.rl_orders_pay:
                Intent intent1 = new Intent(SubmitOrdersActivity.this, PayListActivity.class);
                startActivityForResult(intent1,10);
                break;
            case R.id.settlement:
                if (weizhi == null || weizhi.equals("")) {
                    Toast.makeText(mContext, R.string.please_select_ocation, Toast.LENGTH_SHORT).show();
                    return;
                }else if (pay == 3) {
                    if (canOrder) {
                        HttpUtils httpUtils = new HttpUtils(Contants.URL_GET_TOKEN) {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(SubmitOrdersActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    mAuthorization = jsonObject.getString("token");
                                    mBraintreeFragment = BraintreeFragment.newInstance(SubmitOrdersActivity.this, mAuthorization);
                                    DropInRequest dropInRequest = new DropInRequest().clientToken(mAuthorization);//.clientToken(clientToken)
                                    startActivityForResult(dropInRequest.getIntent(SubmitOrdersActivity.this), 4);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (InvalidArgumentException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        httpUtils.clicent();
                    }else {
                        Toast.makeText(this, R.string.chao_chu_pei_song, Toast.LENGTH_SHORT).show();
                    }
                }else if (pay == 1) {

                }else if (pay == 2) {   //支付宝
                    if (canOrder) {
                        order_num = getOutTradeNo();
                        HttpUtils httpUtils = new HttpUtils(Contants.URL_ALIPAY_SIGN) {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(SubmitOrdersActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                sign = response;
                                if (sign != null && !"".equals(sign)) {
                                    final String payInfo = orderInfo + sign;
                                    Runnable payRunnable = new Runnable() {

                                        @Override
                                        public void run() {
                                            // 构造PayTask 对象
                                            PayTask alipay = new PayTask(SubmitOrdersActivity.this);
                                            // 调用支付接口，获取支付结果
                                            String result = alipay.pay(payInfo, true);

                                            Message msg = new Message();
                                            msg.what = SDK_PAY_FLAG;
                                            msg.obj = result;
                                            mHandler.sendMessage(msg);
                                        }
                                    };

                                    // 必须异步调用
                                    Thread payThread = new Thread(payRunnable);
                                    payThread.start();
                                }
                            }
                        };
//                    httpUtils.addParam("partner", PARTNER);
//                    httpUtils.addParam("service", "mobile.securitypay.pay");
//                    httpUtils.addParam("orderinfo", orderInfo);
//                    httpUtils.clicent();
                        String s;
                        if (sex.equals("女士")||sex.equals("Miss")) {
                            s = "1";
                        } else {
                            s = "0";
                        }
                        String trim = tvOrdersTime.getText().toString().trim();
                        String s1 = trim.replaceAll(getString(R.string.the_delivery_time1), "");
                        httpUtils.addParam("allprices", price1 + Double.parseDouble(format1) + "").addParams("shopid", shopid)
                                .addParams("money", format1 + "").addParams("userid", MyApplication.getLogin().getUserId())
                                .addParams("cometime", s1).addParams("name", name).addParams("sex", s)
                                .addParams("phone", phone).addParams("address", weizhi).addParams("beizhu", tvOrdersTasteNotes.getText().toString()).addParams("juli", peisong+"")
                                .addParams("token", MyApplication.getLogin().getToken());
                        for (int i = 0; i < goods.size(); i++) {
                            httpUtils.addParam("goods[" + i + "][0]", goods.get(i).getGoodsid());
                            httpUtils.addParam("goods[" + i + "][1]", goods.get(i).getNumber() + "");
                        }
                        orderInfo = getOrderInfo(order_num, "EatAway客户端下的单", price1 + Double.parseDouble(format1) + "", order_num);
                        httpUtils.addParam("orderinfo", orderInfo);
                        httpUtils.clicent();
                    }else {
                        Toast.makeText(this, R.string.chao_chu_pei_song, Toast.LENGTH_SHORT).show();
                    }
                }else if (pay == 4) {
                    //测试用：货到付款
//                    postOrder(0, Contants.URL_HADDODER);
                    Toast.makeText(this, R.string.qing_xuan_ze_zhi_fu_fang_shi, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == 3) {
                name = data.getStringExtra("name");
                phone = data.getStringExtra("phone");
                sex = data.getStringExtra("sex");
                weizhi = data.getStringExtra("weizhi");
                id = data.getStringExtra("id");
                coordinate = data.getStringExtra("coordinate");
                llWeizhi.setVisibility(View.VISIBLE);
                if (MyApplication.isEnglish) {
                    tvSubmitName.setText(sex);
                    tvSubmitSex.setText(name);
                }else {
                    tvSubmitSex.setText(sex);
                    tvSubmitName.setText(name);
                }
                tvSubmitPhone.setText(phone);
                tvWeizhi.setText(weizhi);
                HttpUtils httpUtils = new HttpUtils(Contants.URL_ADDRESS_JI) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(SubmitOrdersActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                MyBean bean = new Gson().fromJson(response, MyBean.class);
                                peisong = bean.getMsg().getJuli();
                                freeMoney = bean.getMsg().getMaxprice();
                                feeForKm = bean.getMsg().getMaxlong();
                                feeForMoney = bean.getMsg().getMaxmoney();
                                feeForLk = bean.getMsg().getLkmoney();
                                maxX = bean.getMsg().getLongX();
                                canOrder = true;
//                                if (Double.parseDouble(maxlong) > 0 && peisong < Double.parseDouble(maxlong)) {
////                                    tvOrdersDelivery.setText("$" + "0.00");
//                                    //修改
//                                    tvOrdersDelivery.setText("$" + bean.getMsg().getMinprice());
//                                } else if (peisong > Double.parseDouble(aLong)) {
//                                    tvOrdersDelivery.setText(R.string.chao_chu_pei_song);
//                                    canOrder = false;
//                                } else if (Double.parseDouble(maxprice) > 0 && price1 > Double.parseDouble(maxprice)) {
//                                    tvOrdersDelivery.setText("$" + "0.00");
//                                } else {
//                                    DecimalFormat format = new DecimalFormat("#0.00");
//                                    if (Double.parseDouble(maxlong) == -1)
//                                        format1 = format.format(peisong * Double.parseDouble(bean.getMsg().getLkmoney()));
//                                    else {
//                                        //修改
//                                        format1 = format.format((peisong - Double.parseDouble(maxlong)) * Double.parseDouble(bean.getMsg().getLkmoney())) + bean.getMsg().getMinprice();
////                                        format1 = format.format((peisong - Double.parseDouble(maxlong)) * Double.parseDouble(bean.getMsg().getLkmoney()));
//                                    }
//                                    tvOrdersDelivery.setText("$" + format1);
//                                }
                                if (peisong > maxX) {
                                    //超出配送距离
                                    tvOrdersDelivery.setText(R.string.chao_chu_pei_song);
                                    canOrder = false;
                                }else {
                                    if (freeMoney > 0 && price1 >= freeMoney) {
                                        psmoney = 0;
                                    }else if (feeForKm >= 0 && peisong <= feeForKm) {
                                        psmoney = feeForMoney;
                                    }else if (feeForKm >= 0 && peisong > feeForKm){
                                        psmoney = (peisong - feeForKm) * feeForLk + feeForMoney;
                                    }else if (feeForKm < 0) {
                                        psmoney = peisong * feeForLk;
                                    }
                                    psmoney = NumberFormatUtil.round(psmoney, 2);
                                    calmoney = NumberFormatUtil.round(price1 + psmoney, 2);
                                    tvOrdersDelivery.setText("$" + psmoney);
                                    tvOrdersMoney.setText("$" + calmoney);
                                    shoppingPrise.setText("$" + calmoney);
                                }
                                tvOrdersDress.setText(getString(R.string.order_distance) + " " + peisong + "km");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                httpUtils.addParam("coordinate", coordinate).addParams("shopid", shopid);
                httpUtils.clicent();
            }

        }else if (requestCode == 7) {
            if (resultCode == 9) {
                tvOrdersTasteNotes.setText(data.getStringExtra("note"));
            }
        }else if (requestCode == 10) {
            if (resultCode == 11) {
                pay = data.getIntExtra("type",4);
                tvOrdersMethodOfPayment.setText(data.getStringExtra("payname"));
//                if (pay == 2 && canOrder) {     //如果是支付宝并且订单符合条件，生成orderInfo
//                    orderInfo = getOrderInfo(getOutTradeNo(), "EatAway客户端下单", price1 + Double.parseDouble(format1) + "");
//                    HttpUtils httpUtils = new HttpUtils(Contants.URL_ALIPAY_SIGN) {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//                            Toast.makeText(SubmitOrdersActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onResponse(String response, int id) {
//                            sign = response;
//                            if (sign != null && !"".equals(sign)) {
//
//                            }
//                        }
//                    };
//                    httpUtils.addParam("partner", PARTNER);
//                    httpUtils.addParam("service", "mobile.securitypay.pay");
//                    httpUtils.addParam("orderinfo", orderInfo);
//                    httpUtils.clicent();
//                    String s;
//                    if (sex.equals("女士")||sex.equals("Miss")) {
//                        s = "1";
//                    } else {
//                        s = "0";
//                    }
//                    String trim = tvOrdersTime.getText().toString().trim();
//                    String s1 = trim.replaceAll(getString(R.string.the_delivery_time1), "");
//                    httpUtils.addParam("allprices", price1 + Double.parseDouble(format1) + "").addParams("shopid", shopid)
//                            .addParams("money", format1 + "").addParams("userid", MyApplication.getLogin().getUserId())
//                            .addParams("cometime", s1).addParams("name", name).addParams("sex", s)
//                            .addParams("phone", phone).addParams("address", weizhi).addParams("beizhu", tvOrdersTasteNotes.getText().toString()).addParams("juli", peisong+"")
//                            .addParams("token", MyApplication.getLogin().getToken());
//                    for (int i = 0; i < goods.size(); i++) {
//                        httpUtils.addParam("goods[" + i + "][0]", goods.get(i).getGoodsid());
//                        httpUtils.addParam("goods[" + i + "][1]", goods.get(i).getNumber() + "");
//                    }
//                    httpUtils.clicent();
//                }else {
//                    orderInfo = "";
//                }
            }
        }else if (requestCode == 4) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                // use the result to update your UI and send the payment method nonce to your server
                nonce = result.getPaymentMethodNonce().getNonce();
                postOrder(BANK, Contants.URL_ADDODER);
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // the user canceled
            Toast.makeText(this, R.string.qu_xiao_ding_dan, Toast.LENGTH_SHORT).show();
        } else {
            // handle errors here, an exception may be available in
            Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            Toast.makeText(this, R.string.xia_dan_shi_bai, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.rl_beizhu)
    public void onClick() {
        Intent i = new Intent(this, BeiZhuActivity.class);
        startActivityForResult(i,7);
    }

    class MyBean {

        /**
         * status : 1
         * msg : {"coordinate":"138.6062277,-34.92060300000001","maxprice":"-1","maxlong":"-1","lkmoney":"2","long":"20000","juli":1.01}
         */

        private int status;
        private MsgBean msg;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public MsgBean getMsg() {
            return msg;
        }

        public void setMsg(MsgBean msg) {
            this.msg = msg;
        }

        public class MsgBean {
            /**
             * coordinate : 138.6062277,-34.92060300000001
             * maxprice : -1
             * maxlong : -1
             * lkmoney : 2
             * long : 20000
             * juli : 1.01
             */

            private String coordinate;
            private double maxprice;    //满多少钱免费
            private double lkmoney;
            @SerializedName("long")
            private double longX;       //商家的最大配送距离
            private double juli;

            //新增
            private double minprice;
            private double maxlong;
            private double maxmoney;

            public double getMaxmoney() {
                return maxmoney;
            }

            public void setMaxmoney(double maxmoney) {
                this.maxmoney = maxmoney;
            }

            public double getMinprice() {
                return minprice;
            }

            public void setMinprice(double minprice) {
                this.minprice = minprice;
            }

            public String getCoordinate() {
                return coordinate;
            }

            public void setCoordinate(String coordinate) {
                this.coordinate = coordinate;
            }

            public double getMaxprice() {
                return maxprice;
            }

            public void setMaxprice(double maxprice) {
                this.maxprice = maxprice;
            }

            public double getMaxlong() {
                return maxlong;
            }

            public void setMaxlong(double maxlong) {
                this.maxlong = maxlong;
            }

            public double getLkmoney() {
                return lkmoney;
            }

            public void setLkmoney(double lkmoney) {
                this.lkmoney = lkmoney;
            }

            public double getLongX() {
                return longX;
            }

            public void setLongX(int longX) {
                this.longX = longX;
            }

            public double getJuli() {
                return juli;
            }

            public void setJuli(double juli) {
                this.juli = juli;
            }
        }
    }

    private void postOrder(int type, String url) {
        HttpUtils httpUtils = new HttpUtils(url) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("xiadan", e.getMessage());
                Toast.makeText(SubmitOrdersActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("xiadan", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (status == 0) {
                        Toast.makeText(SubmitOrdersActivity.this, R.string.xia_dan_shi_bai, Toast.LENGTH_SHORT).show();
                    } else if (status == 1) {
                        Toast.makeText(SubmitOrdersActivity.this, R.string.xia_dan_cheng_gong, Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (status == 9) {
                        Toast.makeText(SubmitOrdersActivity.this, R.string.login_agin, Toast.LENGTH_SHORT).show();
                        MyApplication.saveLogin(null);
                        startActivity(new Intent(SubmitOrdersActivity.this, LoginActivity.class));
                        finish();
                    } else if (status == 4) {
                        Toast.makeText(SubmitOrdersActivity.this, R.string.shang_dian_wei_ying_ye, Toast.LENGTH_SHORT).show();
                    } else if (status == 5) {
                        Toast.makeText(SubmitOrdersActivity.this, R.string.chao_chu_zui_da_pei_song_ju_li, Toast.LENGTH_SHORT).show();
                    }else if(status == 66) {
                        Toast.makeText(SubmitOrdersActivity.this, R.string.qing_shua_xin_lie_biao, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String s;
        if (sex.equals("女士")||sex.equals("Miss")) {
            s = "1";
        } else {
            s = "0";
        }
        String trim = tvOrdersTime.getText().toString().trim();
        String s1 = trim.replaceAll(getString(R.string.the_delivery_time1), "");
        httpUtils.addParam("allprices", calmoney + "").addParams("shopid", shopid)
                .addParams("money", psmoney + "").addParams("userid", MyApplication.getLogin().getUserId())
                .addParams("cometime", s1).addParams("name", name).addParams("sex", s)
                .addParams("phone", phone).addParams("address", weizhi).addParams("beizhu", tvOrdersTasteNotes.getText().toString()).addParams("juli", peisong+"")
                .addParams("token", MyApplication.getLogin().getToken());
        for (int i = 0; i < goods.size(); i++) {
            httpUtils.addParam("goods[" + i + "][0]", goods.get(i).getGoodsid());
            httpUtils.addParam("goods[" + i + "][1]", goods.get(i).getNumber() + "");
        }
        if (type == BANK) {
            httpUtils.addParam("nonce",nonce);
            httpUtils.addParam("pay","3");
        }
        httpUtils.clicent();
    }
}
