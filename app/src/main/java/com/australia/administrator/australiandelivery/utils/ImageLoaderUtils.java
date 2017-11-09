package com.australia.administrator.australiandelivery.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * Created by GSD on 2016/12/17 0017.
 * QQ:461842166
 * GitHub:Shengdi-Gu
 */

public class ImageLoaderUtils implements ImageLoader {


    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity).
                load(new File(path))
                .crossFade()
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
