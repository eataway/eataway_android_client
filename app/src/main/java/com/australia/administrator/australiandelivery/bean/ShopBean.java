package com.australia.administrator.australiandelivery.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ShopBean {

    /**
     * status : 1
     * msg : [{"shopid":"20","shopname":"yyyy","mobile":"15230868185","content":"yyyy","shophead":"http://wm.sawfree.com/uploads/c51104c4e72ffbcd47bb37138ba5979d.jpg","shopphoto":"http://wm.sawfree.com/uploads/723de35e838c1dc30f27c6583ba13878.jpg","state":"2","states":"1","detailed_address":"111111111111111111","coordinate":"116.475558,39.936625","gotime":"22:41-23:55","long":"12","maxprice":"-1","maxlong":"-1","lkmoney":"32","bkm":"","flag":"1","addtime":"2017-07-20 14:48:56","juli":7649.07,"category":[{"cid":"54","cname":"hdhd"}]},{"shopid":"15","shopname":"Good good shop","mobile":"18600012345","content":"Good!","shophead":"http://wm.sawfree.com/uploads/731bae8dca83b1ea43dad98cd2483cb6.jpg","shopphoto":"http://wm.sawfree.com/uploads/420734c7f8a4889b8ecd7451cfc25e09.jpg","state":"1","states":"1","detailed_address":"001, MUJI To Go Hong Kong Station","coordinate":"114.158177,22.284681","gotime":"11:50-20:00","long":"900","maxprice":"-1","maxlong":"-1","lkmoney":"10","bkm":"","flag":"1","addtime":"2017-07-17 18:26:34","juli":8258.03,"category":[{"cid":"62","cname":""},{"cid":"51","cname":"333333"},{"cid":"52","cname":"Adsfasfdsf"}]}]
     * msg1 : [{"shopid":"4","shopphoto":"http://wm.sawfree.com/uploads/e000088a97b6fd295232d9eb2630d310.jpg"},{"shopid":"5","shopphoto":"http://wm.sawfree.com/uploads/e88a9703abfb1a289d9e7df7e57ed221.jpg"},{"shopid":"6","shopphoto":"http://wm.sawfree.com/uploads/4a98c2eaacab7fe5cf5d3072fa171f3d.jpg"},{"shopid":"7","shopphoto":"http://wm.sawfree.com/uploads/2869ffb63d16d7bbb0b16c0da250901c.jpg"}]
     */

    private int status;
    private List<MsgBean> msg;
    private List<Msg1Bean> msg1;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<MsgBean> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgBean> msg) {
        this.msg = msg;
    }

    public List<Msg1Bean> getMsg1() {
        return msg1;
    }

    public void setMsg1(List<Msg1Bean> msg1) {
        this.msg1 = msg1;
    }

    public static class MsgBean {
        /**
         * shopid : 20
         * shopname : yyyy
         * mobile : 15230868185
         * content : yyyy
         * shophead : http://wm.sawfree.com/uploads/c51104c4e72ffbcd47bb37138ba5979d.jpg
         * shopphoto : http://wm.sawfree.com/uploads/723de35e838c1dc30f27c6583ba13878.jpg
         * state : 2
         * states : 1
         * detailed_address : 111111111111111111
         * coordinate : 116.475558,39.936625
         * gotime : 22:41-23:55
         * long : 12
         * maxprice : -1
         * maxlong : -1
         * lkmoney : 32
         * bkm :
         * flag : 1
         * addtime : 2017-07-20 14:48:56
         * juli : 7649.07
         * category : [{"cid":"54","cname":"hdhd"}]
         */

        private String shopid;
        private String shopname;
        private String mobile;
        private String content;
        private String shophead;
        private String shopphoto;
        private String state;
        private String states;
        private String detailed_address;
        private String coordinate;
        private String gotime;
        @SerializedName("long")
        private String longX;
        private String maxprice;
        private String maxlong;
        private String lkmoney;
        private String bkm;
        private String flag;
        private String addtime;
        private double juli;
        private List<CategoryBean> category;

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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStates() {
            return states;
        }

        public void setStates(String states) {
            this.states = states;
        }

        public String getDetailed_address() {
            return detailed_address;
        }

        public void setDetailed_address(String detailed_address) {
            this.detailed_address = detailed_address;
        }

        public String getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(String coordinate) {
            this.coordinate = coordinate;
        }

        public String getGotime() {
            return gotime;
        }

        public void setGotime(String gotime) {
            this.gotime = gotime;
        }

        public String getLongX() {
            return longX;
        }

        public void setLongX(String longX) {
            this.longX = longX;
        }

        public String getMaxprice() {
            return maxprice;
        }

        public void setMaxprice(String maxprice) {
            this.maxprice = maxprice;
        }

        public String getMaxlong() {
            return maxlong;
        }

        public void setMaxlong(String maxlong) {
            this.maxlong = maxlong;
        }

        public String getLkmoney() {
            return lkmoney;
        }

        public void setLkmoney(String lkmoney) {
            this.lkmoney = lkmoney;
        }

        public String getBkm() {
            return bkm;
        }

        public void setBkm(String bkm) {
            this.bkm = bkm;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public double getJuli() {
            return juli;
        }

        public void setJuli(double juli) {
            this.juli = juli;
        }

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public static class CategoryBean {
            /**
             * cid : 54
             * cname : hdhd
             */

            private String cid;
            private String cname;

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getCname() {
                return cname;
            }

            public void setCname(String cname) {
                this.cname = cname;
            }
        }
    }

    public static class Msg1Bean {
        /**
         * shopid : 4
         * shopphoto : http://wm.sawfree.com/uploads/e000088a97b6fd295232d9eb2630d310.jpg
         */

        private String shopid;
        private String shopphoto;

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getShopphoto() {
            return shopphoto;
        }

        public void setShopphoto(String shopphoto) {
            this.shopphoto = shopphoto;
        }
    }
}
