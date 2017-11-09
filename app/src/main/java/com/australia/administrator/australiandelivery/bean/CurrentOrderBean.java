package com.australia.administrator.australiandelivery.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */

public class CurrentOrderBean {

    /**
     * status : 1
     * msg : [{"shopid":"32","shopname":"天津的","mobile":"123","content":"","shophead":"http://wm.sawfree.com/uploads/582bfdcbd40bdc06c65b281f07859844.jpg","shopphoto":"http://wm.sawfree.com/uploads/655b3eefa33d6a8c20deb6506e654739.jpg","state":"3","states":"1","detailed_address":"万达广场（东城店）","coordinate":"113.7807606,23.0348763","gotime":" ","long":"12","maxprice":"-1","maxlong":"-1","lkmoney":"3","bkm":"","flag":"1","addtime":"2017-07-27 17:17:51","orderid":"1501147071","uid":"25","money":"0.00","allprice":"84.00","cometime":"尽快送达","endtime":null,"name":"张鑫","sex":"1","phone":"1523086815","address":"张鑫33","beizhu":"","statu":"2","juli":"3","pay":"4","todaynums":"12","goodsinfo":"145,包子,12.00,http://wm.sawfree.com/uploads/d5603bc3eb7f2af6a4390c309cc141a9.jpg,7|","lifetime":"2017-07-27 17:32:51","goods":[{"goodsid":"145","goodsname":"包子","goodsphoto":"uploads/d5603bc3eb7f2af6a4390c309cc141a9.jpg","goodsprice":"12.00","goodscontent":null,"cid":"139","shopid":"32","flag":"2","addtime":"2017-07-26 09:35:06","num":"7"}]},{"shopid":"32","shopname":"天津的","mobile":"123","content":"","shophead":"http://wm.sawfree.com/uploads/582bfdcbd40bdc06c65b281f07859844.jpg","shopphoto":"http://wm.sawfree.com/uploads/655b3eefa33d6a8c20deb6506e654739.jpg","state":"3","states":"1","detailed_address":"万达广场（东城店）","coordinate":"113.7807606,23.0348763","gotime":" ","long":"12","maxprice":"-1","maxlong":"-1","lkmoney":"3","bkm":"","flag":"1","addtime":"2017-07-27 17:24:09","orderid":"1501147449","uid":"25","money":"0.00","allprice":"84.00","cometime":"尽快送达","endtime":null,"name":"张鑫","sex":"1","phone":"1523086815","address":"张鑫33","beizhu":"","statu":"2","juli":"3","pay":"4","todaynums":"13","goodsinfo":"145,包子,12.00,http://wm.sawfree.com/uploads/d5603bc3eb7f2af6a4390c309cc141a9.jpg,7|","lifetime":"2017-07-27 17:39:09","goods":[{"goodsid":"145","goodsname":"包子","goodsphoto":"uploads/d5603bc3eb7f2af6a4390c309cc141a9.jpg","goodsprice":"12.00","goodscontent":null,"cid":"139","shopid":"32","flag":"2","addtime":"2017-07-26 09:35:06","num":"7"}]},{"shopid":"32","shopname":"天津的","mobile":"123","content":"","shophead":"http://wm.sawfree.com/uploads/582bfdcbd40bdc06c65b281f07859844.jpg","shopphoto":"http://wm.sawfree.com/uploads/655b3eefa33d6a8c20deb6506e654739.jpg","state":"3","states":"1","detailed_address":"万达广场（东城店）","coordinate":"113.7807606,23.0348763","gotime":" ","long":"12","maxprice":"-1","maxlong":"-1","lkmoney":"3","bkm":"","flag":"1","addtime":"2017-07-27 17:24:41","orderid":"1501147481","uid":"25","money":"0.00","allprice":"84.00","cometime":"尽快送达","endtime":null,"name":"张鑫","sex":"1","phone":"1523086815","address":"张鑫33","beizhu":"","statu":"2","juli":"3","pay":"4","todaynums":"14","goodsinfo":"145,包子,12.00,http://wm.sawfree.com/uploads/d5603bc3eb7f2af6a4390c309cc141a9.jpg,7|","lifetime":"2017-07-27 17:39:41","goods":[{"goodsid":"145","goodsname":"包子","goodsphoto":"uploads/d5603bc3eb7f2af6a4390c309cc141a9.jpg","goodsprice":"12.00","goodscontent":null,"cid":"139","shopid":"32","flag":"2","addtime":"2017-07-26 09:35:06","num":"7"}]},{"shopid":"32","shopname":"天津的","mobile":"123","content":"","shophead":"http://wm.sawfree.com/uploads/582bfdcbd40bdc06c65b281f07859844.jpg","shopphoto":"http://wm.sawfree.com/uploads/655b3eefa33d6a8c20deb6506e654739.jpg","state":"3","states":"1","detailed_address":"万达广场（东城店）","coordinate":"113.7807606,23.0348763","gotime":" ","long":"12","maxprice":"-1","maxlong":"-1","lkmoney":"3","bkm":"","flag":"1","addtime":"2017-07-27 17:31:27","orderid":"1501147887","uid":"25","money":"0.00","allprice":"84.00","cometime":"尽快送达","endtime":null,"name":"张鑫","sex":"1","phone":"1523086815","address":"张鑫33","beizhu":"","statu":"2","juli":"3","pay":"4","todaynums":"15","goodsinfo":"145,包子,12.00,http://wm.sawfree.com/uploads/d5603bc3eb7f2af6a4390c309cc141a9.jpg,7|","lifetime":"2017-07-27 17:46:27","goods":[{"goodsid":"145","goodsname":"包子","goodsphoto":"uploads/d5603bc3eb7f2af6a4390c309cc141a9.jpg","goodsprice":"12.00","goodscontent":null,"cid":"139","shopid":"32","flag":"2","addtime":"2017-07-26 09:35:06","num":"7"}]},{"shopid":"32","shopname":"天津的","mobile":"123","content":"","shophead":"http://wm.sawfree.com/uploads/582bfdcbd40bdc06c65b281f07859844.jpg","shopphoto":"http://wm.sawfree.com/uploads/655b3eefa33d6a8c20deb6506e654739.jpg","state":"3","states":"1","detailed_address":"万达广场（东城店）","coordinate":"113.7807606,23.0348763","gotime":" ","long":"12","maxprice":"-1","maxlong":"-1","lkmoney":"3","bkm":"","flag":"1","addtime":"2017-07-27 17:31:34","orderid":"1501147894","uid":"25","money":"0.00","allprice":"84.00","cometime":"尽快送达","endtime":null,"name":"张鑫","sex":"1","phone":"1523086815","address":"张鑫33","beizhu":"","statu":"2","juli":"3","pay":"4","todaynums":"16","goodsinfo":"145,包子,12.00,http://wm.sawfree.com/uploads/d5603bc3eb7f2af6a4390c309cc141a9.jpg,7|","lifetime":"2017-07-27 17:46:34","goods":[{"goodsid":"145","goodsname":"包子","goodsphoto":"uploads/d5603bc3eb7f2af6a4390c309cc141a9.jpg","goodsprice":"12.00","goodscontent":null,"cid":"139","shopid":"32","flag":"2","addtime":"2017-07-26 09:35:06","num":"7"}]},{"shopid":"32","shopname":"天津的","mobile":"123","content":"","shophead":"http://wm.sawfree.com/uploads/582bfdcbd40bdc06c65b281f07859844.jpg","shopphoto":"http://wm.sawfree.com/uploads/655b3eefa33d6a8c20deb6506e654739.jpg","state":"3","states":"1","detailed_address":"万达广场（东城店）","coordinate":"113.7807606,23.0348763","gotime":" ","long":"12","maxprice":"-1","maxlong":"-1","lkmoney":"3","bkm":"","flag":"1","addtime":"2017-07-27 17:32:15","orderid":"1501147935","uid":"25","money":"0.00","allprice":"84.00","cometime":"尽快送达","endtime":null,"name":"张鑫","sex":"1","phone":"1523086815","address":"张鑫33","beizhu":"","statu":"2","juli":"3","pay":"4","todaynums":"17","goodsinfo":"145,包子,12.00,http://wm.sawfree.com/uploads/d5603bc3eb7f2af6a4390c309cc141a9.jpg,7|","lifetime":"2017-07-27 17:47:15","goods":[{"goodsid":"145","goodsname":"包子","goodsphoto":"uploads/d5603bc3eb7f2af6a4390c309cc141a9.jpg","goodsprice":"12.00","goodscontent":null,"cid":"139","shopid":"32","flag":"2","addtime":"2017-07-26 09:35:06","num":"7"}]}]
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
         * shophead : http://wm.sawfree.com/uploads/582bfdcbd40bdc06c65b281f07859844.jpg
         * shopphoto : http://wm.sawfree.com/uploads/655b3eefa33d6a8c20deb6506e654739.jpg
         * state : 3
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
         * addtime : 2017-07-27 17:17:51
         * orderid : 1501147071
         * uid : 25
         * money : 0.00
         * allprice : 84.00
         * cometime : 尽快送达
         * endtime : null
         * name : 张鑫
         * sex : 1
         * phone : 1523086815
         * address : 张鑫33
         * beizhu :
         * statu : 2
         * juli : 3
         * pay : 4
         * todaynums : 12
         * goodsinfo : 145,包子,12.00,http://wm.sawfree.com/uploads/d5603bc3eb7f2af6a4390c309cc141a9.jpg,7|
         * lifetime : 2017-07-27 17:32:51
         * goods : [{"goodsid":"145","goodsname":"包子","goodsphoto":"uploads/d5603bc3eb7f2af6a4390c309cc141a9.jpg","goodsprice":"12.00","goodscontent":null,"cid":"139","shopid":"32","flag":"2","addtime":"2017-07-26 09:35:06","num":"7"}]
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
        private String orderid;
        private String uid;
        private String money;
        private String allprice;
        private String cometime;
        private Object endtime;
        private String name;
        private String sex;
        private String phone;
        private String address;
        private String beizhu;
        private String statu;
        private String juli;
        private String pay;
        private String todaynums;
        private String goodsinfo;
        private String lifetime;
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

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAllprice() {
            return allprice;
        }

        public void setAllprice(String allprice) {
            this.allprice = allprice;
        }

        public String getCometime() {
            return cometime;
        }

        public void setCometime(String cometime) {
            this.cometime = cometime;
        }

        public Object getEndtime() {
            return endtime;
        }

        public void setEndtime(Object endtime) {
            this.endtime = endtime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getStatu() {
            return statu;
        }

        public void setStatu(String statu) {
            this.statu = statu;
        }

        public String getJuli() {
            return juli;
        }

        public void setJuli(String juli) {
            this.juli = juli;
        }

        public String getPay() {
            return pay;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }

        public String getTodaynums() {
            return todaynums;
        }

        public void setTodaynums(String todaynums) {
            this.todaynums = todaynums;
        }

        public String getGoodsinfo() {
            return goodsinfo;
        }

        public void setGoodsinfo(String goodsinfo) {
            this.goodsinfo = goodsinfo;
        }

        public String getLifetime() {
            return lifetime;
        }

        public void setLifetime(String lifetime) {
            this.lifetime = lifetime;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * goodsid : 145
             * goodsname : 包子
             * goodsphoto : uploads/d5603bc3eb7f2af6a4390c309cc141a9.jpg
             * goodsprice : 12.00
             * goodscontent : null
             * cid : 139
             * shopid : 32
             * flag : 2
             * addtime : 2017-07-26 09:35:06
             * num : 7
             */

            private String goodsid;
            private String goodsname;
            private String goodsphoto;
            private String goodsprice;
            private Object goodscontent;
            private String cid;
            private String shopid;
            private String flag;
            private String addtime;
            private String num;

            public String getGoodsid() {
                return goodsid;
            }

            public void setGoodsid(String goodsid) {
                this.goodsid = goodsid;
            }

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

            public Object getGoodscontent() {
                return goodscontent;
            }

            public void setGoodscontent(Object goodscontent) {
                this.goodscontent = goodscontent;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
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

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }
    }
}
