package com.australia.administrator.australiandelivery.utils;

import android.util.Log;

import com.australia.administrator.australiandelivery.bean.MyOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */

public class GoosInfoAdapterUtils {
//    private static String[] goods;
    private static List<MyOrder> info ;

    public static void InfoCrop(String s,int po) {
        String[] goods;
        info= new ArrayList<>();
        goods = s.split("\\|");
        if (goods != null && goods.length > 0) {
            for (int i = 0; i < goods.length; i++) {
                MyOrder order = new MyOrder();
                String[] split = goods[i].split(",");
                if (split != null && split.length > 0) {
                    for (int j = 0; j < split.length / 5; j++) {
                        order.setGoodsId(split[j*5]);
                        order.setGoodsName(split[j*5+1]);
                        order.setGoodsPrice(split[j*5+2]);
                        order.setGoodsPic(split[j*5+3]);
                        order.setGoodsNum(split[j*5+4]);
                        info.add(order);
                    }
                }
            }
        }
    }

    public static List<MyOrder> getList () {
        for (int i=0;i<info.size();i++){
            Log.i("mylist", "getList: "+info.get(i).getGoodsName());
        }
        return info;
    }

}
