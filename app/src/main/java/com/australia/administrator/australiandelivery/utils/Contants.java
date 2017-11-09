package com.australia.administrator.australiandelivery.utils;

/**
 * Created by Administrator on 2017/3/23.
 */

public interface Contants {
//    String URL = "http://wm.sawfree.com/index.php/home/";
//    String URL = "http://108.61.96.39/index.php/home/";
//    String URL = "http://www21mxwx.cn/tp/index.php/home/";
    String URL = "http://192.168.2.126/tp/index.php/home/";


    //google play download address(Merchant)
    String ADDRESS_DOWNLOAD = "https://play.google.com/store/apps/details?id=com.administrator.administrator.eataway";
    //用户设置默认地址
    String URL_DEFADDRESS = URL + "user/set_address";
    //批量删除收货地址
    String URL_DELADDRESS = URL + "user/alldel_address";
    //用户编辑收货地址
    String URL_EDITADDRESS = URL + "user/edit_address";
    //重置密码
    String URL_RESETPWD = URL + "user/replay_password";
    //未完成订单
    String URL_CURRENT = URL + "order/worderlist";
    //退出登录
    String URL_LOGOUT = URL + "user/logout";
    //确认收货
    String URL_QUEREN = URL + "order/queren";
    //上传评价
    String URL_ADDPINGJIA = URL + "evaluate/addpingjia";
    //个人中心
    String URL_USER_PERSONALCENTER = URL + "user/personal_center";
    //订单列表
    String URL_ORDER_ORDERLIST = URL + "order/orderlist";
    //订单详情
    String URL_ORDER_ORDERDETAILS = URL + "order/orderdetails";
    //修改头像
    String URL_USER_EDITHEAD = URL + "user/set_photo";
    //修改用户名
    String URL_USER_EDITNAME = URL + "user/edit_username";
    //验证手机号
    String URL_USER_VERIPHONE = URL + "user/veri_mobile";
    //修改绑定手机号
    String URL_USER_EDITPHONE = URL + "user/editmobile";
    //用户退出登录
    String URL_USER_LOGOUT = URL + "user/logout";
    //获取收货地址
    //意见反馈
    String URL_EVALUATE_BACK = URL + "evaluate/back";
    String URL_RELEASE = URL + "user/register";
    String URL_LOGIN = URL + "user/login";
    String URL_THREE_LOGIN = URL + "user/third_login";
    String URL_THREE_RELEASE = URL + "user/third_register";
    //商户搜索
    String URL_SEARCHSHOP = URL + "shop/sercheshop";
    String URL_GETADDRESS = URL + "user/get_address";
    String URL_ADD_ADDRESS = URL + "user/add_address";
    String URL_SHOPLIST = URL + "shop/shoplist";
    String URL_MENULIST = URL + "shop/menulist";
    String URL_GET_TOKEN = URL + "order/brtoken";
    String URL_ADDRESS_JI = URL + "shop/jisuan";
    String URL_GOOGLE = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    String URL_HADDODER = URL + "order/haddorder";
    String URL_ADDODER = URL + "order/addorder";

    //支付宝签名获取接口
    String URL_ALIPAY_SIGN = URL + "order/sign";

}
