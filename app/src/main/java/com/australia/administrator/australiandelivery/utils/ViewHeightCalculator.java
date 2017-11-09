package com.australia.administrator.australiandelivery.utils;

import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/6/14.
 */

public class ViewHeightCalculator {
    public static int ViewHeightCaluclate(View view) {
        view.measure(0,0);
        Log.i("height",view.getMeasuredHeight()+"");
        return view.getMeasuredHeight();
    }
}
