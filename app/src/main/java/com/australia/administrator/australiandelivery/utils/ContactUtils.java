package com.australia.administrator.australiandelivery.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Administrator on 2017/7/28.
 */

public class ContactUtils {
    private static String PHONE_NUMBER = "0123456";

    public static void ContactUS (Context context){
        Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + PHONE_NUMBER));
        context.startActivity(dialIntent);
    }

    public static void ContactUS(Context context, String phoneNumber) {
        Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        context.startActivity(dialIntent);
    }

}
