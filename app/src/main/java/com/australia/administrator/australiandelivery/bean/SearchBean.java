package com.australia.administrator.australiandelivery.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/7/27.
 */

public class SearchBean {

    /**
     * status : 1
     * msg : [{"shopid":"32","shopname":"天津的","mobile":"123","content":"","shophead":"http://192.168.0.111/uploads/582bfdcbd40bdc06c65b281f07859844.jpg","shopphoto":"http://192.168.0.111/uploads/655b3eefa33d6a8c20deb6506e654739.jpg","state":"1","states":"1","detailed_address":"万达广场（东城店）","coordinate":"113.7807606,23.0348763","gotime":" ","long":"12","maxprice":"-1","maxlong":"-1","lkmoney":"3","bkm":"","flag":"1","addtime":"2017-07-26 09:33:11","category":[{"cid":"139","cname":"天津包子"}]}]
     */

    private int status;
    private List<MsgBean> msg;

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

    public static class MsgBean {
        /**
         * shopid : 32
         * shopname : 天津的
         * mobile : 123
         * content :
         * shophead : http://192.168.0.111/uploads/582bfdcbd40bdc06c65b281f07859844.jpg
         * shopphoto : http://192.168.0.111/uploads/655b3eefa33d6a8c20deb6506e654739.jpg
         * state : 1
         * states : 1
         * detailed_address : 万达广场（东城店）
         * coordinate : 113.7807606,23.0348763
         * gotime :
         * long : 12
         * maxprice : -1
         * maxlong : -1
         * lkmoney : 3
         * bkm :
         * flag : 1
         * addtime : 2017-07-26 09:33:11
         * category : [{"cid":"139","cname":"天津包子"}]
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

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public static class CategoryBean {
            /**
             * cid : 139
             * cname : 天津包子
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
}
