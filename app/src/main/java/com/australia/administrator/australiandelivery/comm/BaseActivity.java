package com.australia.administrator.australiandelivery.comm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;

import java.util.Locale;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;


/**
 * Created by GSD on 2016/12/3 0003.
 * QQ:461842166
 * GitHub:Shengdi-Gu
 */
public abstract class BaseActivity extends SupportActivity {

    public Context mContext = this;
    public boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
//        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary).init();
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //将当前的activity添加到栈管理器中
        AppManager.getInstance().add(this);
        initDate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ImmersionBar.with(this).destroy();
    }

    protected abstract int getLayoutId();
    protected abstract void initDate();

    /** * @param isEnglish true ：点击英文，把中文设置未选中 * false ：点击中文，把英文设置未选中 */
    public static void setLanguage(boolean isEnglish, Activity activity) {
        Configuration configuration = activity.getResources().getConfiguration();
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        if (isEnglish) {
            //设置英文
            configuration.locale = Locale.ENGLISH;
        } else {
            //设置中文
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }
        //更新配置
        activity.getResources().updateConfiguration(configuration, displayMetrics);
    }

    //启动一个新的activity
    public void goToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);

    }
    public void goToActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtra("data", bundle);
        }
        startActivity(intent);

    }

    //结束当前activity的显示
    public void closeCurrentActivity() {
        AppManager.getInstance().removeCurrent();
    }
}
