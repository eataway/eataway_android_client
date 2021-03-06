package com.australia.administrator.australiandelivery.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.compat.BuildConfig;


public class getSetting {

    /**
     * 跳转到权限设置界面
     */
    private Intent getAppDetailSettingIntent(Context context){

        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }
    /**
     * 跳转到魅族的权限管理系统
     */
    private void gotoMeizuPermission(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            gotoHuaweiPermission(context);
        }
    }
    /**
     * 华为的权限管理页面
     */
    private void gotoHuaweiPermission(Context c) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            c.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            c.startActivity(getAppDetailSettingIntent(c));
        }
    }
    /**
     * 跳转到miui的权限管理页面
     */
    private void gotoMiuiPermission(Context c) {
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname", c.getPackageName());
        try {
            c.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            gotoMeizuPermission(c);
        }
    }
    /**
     * 跳转到vivo的权限管理页面
     */
    public void gotoVIVO(Context c) {
        Intent appIntent = c.getPackageManager().getLaunchIntentForPackage("com.iqoo.secure");
        try {
            c.startActivity(appIntent);
        } catch (Exception e) {
            e.printStackTrace();
            gotoOPPO(c);
        }
    }
    /**
     * 跳转到oppo的权限管理页面
     */
    public void gotoOPPO(Context c) {
        Intent appIntent = c.getPackageManager().getLaunchIntentForPackage("com.oppo.safe");
        try {
            c.startActivity(appIntent);
        } catch (Exception e) {
            e.printStackTrace();
            gotoMiuiPermission(c);
        }
    }
}
