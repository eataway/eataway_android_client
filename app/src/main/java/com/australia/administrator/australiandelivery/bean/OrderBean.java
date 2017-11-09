package com.australia.administrator.australiandelivery.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/10.
 */

public class OrderBean {

    /**
     * status : 1
     * msg : {"shopid":"13","shopname":"武汉热干面","shophead":"http://wm.sawfree.com/uploads/5097005b31a2b6ab52c8c5c03dc22e66.jpg","shopphoto":"http://wm.sawfree.com/uploads/06cd84dd3e0fec02249821785bdef2ec.jpg","phone":"12345678","goodsinfo":"146,麻辣热干面,10.00,http://wm.sawfree.com/uploads/87c6637ad89935890124b5d5943a8787.jpg,2|","shopphone":"17180136582","cometime":"ASAP","statu":"3","money":"929.65","juli":"929.65","allprice":"949.65","orderid":"1504579160","state":"1","address":" Suzhou","beizhu":"","addtime":"2017-09-05 10:39:20","goods":[{"goodsname":"麻辣热干面","goodsphoto":"http://wm.sawfree.com/uploads/87c6637ad89935890124b5d5943a8787.jpg","goodsprice":"10.00","goodsid":"146","num":"2"}]}
     */

    private int status;
    private MsgBean msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * shopid : 13
         * shopname : 武汉热干面
         * shophead : http://wm.sawfree.com/uploads/5097005b31a2b6ab52c8c5c03dc22e66.jpg
         * shopphoto : http://wm.sawfree.com/uploads/06cd84dd3e0fec02249821785bdef2ec.jpg
         * phone : 12345678
         * goodsinfo : 146,麻辣热干面,10.00,http://wm.sawfree.com/uploads/87c6637ad89935890124b5d5943a8787.jpg,2|
         * shopphone : 17180136582
         * cometime : ASAP
         * statu : 3
         * money : 929.65
         * juli : 929.65
         * allprice : 949.65
         * orderid : 1504579160
         * state : 1
         * address :  Suzhou
         * beizhu :
         * addtime : 2017-09-05 10:39:20
         * goods : [{"goodsname":"麻辣热干面","goodsphoto":"http://wm.sawfree.com/uploads/87c6637ad89935890124b5d5943a8787.jpg","goodsprice":"10.00","goodsid":"146","num":"2"}]
         */

        private String shopid;
        private String shopname;
        private String shophead;
        private String shopphoto;
        private String phone;
        private String goodsinfo;
        private String shopphone;
        private String cometime;
        private String statu;
        private String money;
        private String juli;
        private String allprice;
        private String orderid;
        private String state;
        private String address;
        private String beizhu;
        private String addtime;
        private List<GoodsBean> goods;

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getShophead() {
            return shophead;
        }

        public void setShophead(String shophead) {
            this.shophead = shophead;
        }

        public String getShopphoto() {
            return shopphoto;
        }

        public void setShopphoto(String shopphoto) {
            this.shopphoto = shopphoto;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getGoodsinfo() {
            return goodsinfo;
        }

        public void setGoodsinfo(String goodsinfo) {
            this.goodsinfo = goodsinfo;
        }

        public String getShopphone() {
            return shopphone;
        }

        public void setShopphone(String shopphone) {
            this.shopphone = shopphone;
        }

        public String getCometime() {
            return cometime;
        }

        public void setCometime(String cometime) {
            this.cometime = cometime;
        }

        public String getStatu() {
            return statu;
        }

        public void setStatu(String statu) {
            this.statu = statu;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getJuli() {
            return juli;
        }

        public void setJuli(String juli) {
            this.juli = juli;
        }

        public String getAllprice() {
            return allprice;
        }

        public void setAllprice(String allprice) {
            this.allprice = allprice;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBeizhu() {
            return beizhu;
        }

        public void setBeizhu(String beizhu) {
            this.beizhu = beizhu;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * goodsname : 麻辣热干面
             * goodsphoto : http://wm.sawfree.com/uploads/87c6637ad89935890124b5d5943a8787.jpg
             * goodsprice : 10.00
             * goodsid : 146
             * num : 2
             */

            private String goodsname;
            private String goodsphoto;
            private String goodsprice;
            private String goodsid;
            private String num;

            public String getGoodsname() {
                return goodsname;
            }

            public void setGoodsname(String goodsname) {
                this.goodsname = goodsname;
            }

            public String getGoodsphoto() {
                return goodsphoto;
            }

            public void setGoodsphoto(String goodsphoto) {
                this.goodsphoto = goodsphoto;
            }

            public String getGoodsprice() {
                return goodsprice;
            }

            public void setGoodsprice(String goodsprice) {
                this.goodsprice = goodsprice;
            }

            public String getGoodsid() {
                return goodsid;
            }

            public void setGoodsid(String goodsid) {
                this.goodsid = goodsid;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }
    }
}
