package com.australia.administrator.australiandelivery.ui;


import com.australia.administrator.australiandelivery.bean.ShopDetailsBean;

/**
 * Created by 曹博 on 2016/6/7.
 * 购物车添加接口回调
 */
public interface onCallBackListener {
    /**
     * Type表示添加或减少
     * @param product
     * @param type
     */
    void updateProduct(ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean product, String type);
}
