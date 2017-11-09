package com.australia.administrator.australiandelivery.ui;

import com.australia.administrator.australiandelivery.bean.ShopDetailsBean;

/**
 * Created by 曹博 on 2016/6/7.
 * 购物车添加接口回调
 */
public interface ShopToDetailListener {
    /**
     * Type表示添加或减少
     * @param product
     * @param type
     */
    void onUpdateDetailList(ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean product, String type);

    void onRemovePriduct(ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean product);
}
