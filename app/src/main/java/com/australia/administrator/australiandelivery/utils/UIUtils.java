package com.australia.administrator.australiandelivery.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;


/**
 * 专门提供为处理一些UI相关的问题而创建的工具类
 */
public class UIUtils {

    public static Context getContext() {
        return MyApplication.context;
    }

    public static Handler getHandler() {
        return MyApplication.handler;
    }

    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }

    public static View getXmlView(int viewId) {
        Context context = MyApplication.context;
        int viewId1 = viewId;
        return View.inflate(getContext(), viewId, null);
    }

    public static String[] getStringArr(int arrId) {
        return getContext().getResources().getStringArray(arrId);
    }

    //与屏幕分辨率相关的
    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5);
    }

    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    //    主线程执行的操作
    public static void runOnUiThread(Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            Log.e("TAG", "handler" + getHandler());
            getHandler().post(runnable);
        }
    }

    public static boolean isMainThread() {
        int myTid = android.os.Process.myTid();
        if (myTid == MyApplication.mainThreadId) {
            return true;
        }
        return false;
    }

    public static void Toast(String text, boolean isLong) {
        Toast.makeText(getContext(), text, isLong == true ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }
}
