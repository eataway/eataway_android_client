
package com.australia.administrator.australiandelivery.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.australia.administrator.australiandelivery.R;

/**
 * Created by GSD on 2016/12/23 0023.
 * QQ:461842166
 * GitHub:Shengdi-Gu
 */

public class GlideUtils {
    public static void load(Context context, String url, ImageView imageView, Shape shape) {
        if (shape == Shape.UserIcon) {
            Glide.with(context).load(url).error(R.drawable.img_user_icon_default).into(imageView);
        } else if (shape == Shape.ShopPic) {
            Glide.with(context).load(url).error(R.drawable.img_shop_default).into(imageView);
        } else {
            Glide.with(context).load(url).error(R.drawable.img_shop_icon_default).into(imageView);
        }
    }

    public enum Shape {
        UserIcon, ShopPic, ShopIcon
    }
}
