package com.australia.administrator.australiandelivery.comm;


import com.australia.administrator.australiandelivery.bean.ShopDetailsBean;

import java.util.List;

/**
 * Created by dalong on 2016/12/27.
 */

public class MessageEvent {
    public int  num;
    public double  price;
    public List<ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean> goods;

    public MessageEvent(int totalNum, double price, List<ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean> goods) {
        this.num = totalNum;
        this.price = price;
        this.goods = goods;
    }
}
