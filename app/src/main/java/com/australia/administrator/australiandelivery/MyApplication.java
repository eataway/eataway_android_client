package com.australia.administrator.australiandelivery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

import com.australia.administrator.australiandelivery.bean.ShopDetailsBean;
import com.australia.administrator.australiandelivery.comm.Login;
import com.australia.administrator.australiandelivery.model.DemoHelper;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.android.gms.maps.model.LatLng;
import com.mob.MobApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/3/20.
 */

public class MyApplication extends MobApplication {
    public static int width, height;
    public static Context context;
    public static Handler handler;
    public static Thread mainThread;//主线程
    public static int mainThreadId;
    public static int status = 0;
    public static boolean isLogin = false;
    public static LatLng mLocation=new LatLng(30,30);
    private RelativeLayout mLoadingView;
    public static String currentUserNick = "";
    public static Login login1;
    public static List<ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean> goods = null;
    public static String GoogleKey = "&key=AIzaSyAxx2Icrq4qiATBqIgDAgNHeNFpqdScMnU";
    public static boolean isEnglish = false;
    private static MyApplication instance;

    /**
     * activity栈保存
     */
    public List<Activity> activityStack = null;

    public static void get(final Activity activity, final String manifest) {
        if (ActivityCompat.checkSelfPermission(activity, manifest)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    manifest)) {
                // Provide an additional rationale to the user if the permission was not granted
                // and the user would benefit from additional context for the use of the permission.
                // For example if the user has previously denied the permission.
                new AlertDialog.Builder(activity)
                        .setMessage("申请定位权限，以便获取您的位置")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //申请相机权限
                                ActivityCompat.requestPermissions(activity,
                                        new String[]{manifest}, 1);
                            }
                        })
                        .show();
            } else {

                // Camera permission has not been granted yet. Request it directly.
                ActivityCompat.requestPermissions(activity, new String[]{manifest},
                        1);
            }
        }
    }
    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        context = getApplicationContext();
        instance = this;
        // activity管理
        activityStack = new ArrayList<Activity>();
        handler = new Handler();
        mainThread = Thread.currentThread();//当前实例化MyApplication的线程，就是主线程
        mainThreadId = android.os.Process.myTid();//获取当前线程的id

        //配置okhttp
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);

        DemoHelper.getInstance().init(this);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    public static MyApplication getInstance() {
        return instance;
    }
    //    保存登录信息
    public static boolean saveLogin(Login login) {
        SharedPreferences sp = context.getSharedPreferences("EatAway", Context.MODE_PRIVATE);
        if (login == null) {
            SharedPreferences.Editor editor = sp.edit();
            editor.clear().commit();
            return true;
        }
//        Toast.makeText(context,login.getUserId()+login.getUserName()+login.getToken(),Toast.LENGTH_SHORT).show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(login);
            String loginVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("LoginMessage", loginVal);
            editor.commit();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public interface setLocation {
        void locatin(String s);
    }

    private static setLocation l;

    public static void onSetLocation(setLocation lc) {
        l = lc;
    }

    //获取登录信息
    public static Login getLogin() {
        SharedPreferences sp = context.getSharedPreferences("EatAway", Context.MODE_PRIVATE);

        if (sp.contains("LoginMessage")) {
            String loginVal = sp.getString("LoginMessage", null);
//             userid = sp.getString("userid", "0");
//             token = sp.getString("token", "00");
            byte[] buffer = Base64.decode(loginVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                Login login = (Login) ois.readObject();
                return login;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // /退出整个应用

    public void exitApp() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);
    }
}