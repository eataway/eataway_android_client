package com.australia.administrator.australiandelivery.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by local123 on 2017/9/16.
 */

public class ToastUtils {
    public static Toast mToast;

    public static void showToast(int textId, Context context) {
        if (mToast == null) {
            mToast = Toast.makeText(context, context.getString(textId), Toast.LENGTH_SHORT);
        } else {
            mToast.setText(context.getString(textId));
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
