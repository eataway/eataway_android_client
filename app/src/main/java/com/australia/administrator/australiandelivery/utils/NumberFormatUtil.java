package com.australia.administrator.australiandelivery.utils;

import java.math.BigDecimal;

/**
 * Created by local123 on 2017/10/10.
 */

public class NumberFormatUtil {
    public static Double round(Double doubleValue, int scale){
        Double flag = null;
        String text = doubleValue.toString();
        BigDecimal bd = new BigDecimal(text).setScale(scale, BigDecimal.ROUND_HALF_UP);
        flag = bd.doubleValue();
        return flag;
    }
}
