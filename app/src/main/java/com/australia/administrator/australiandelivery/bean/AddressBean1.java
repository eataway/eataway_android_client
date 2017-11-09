package com.australia.administrator.australiandelivery.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

public class AddressBean1 {


    /**
     * status : 1
     * msg : [{"addid":"5","userid":"15","real_name":"李","gender":"1","mobile":"18633045435","location_address":"Denhill Lodge","coordinate":"-38.260924,141.624226","detailed_address":"202","flag":"1","add_sex":"女士"}]
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
         * addid : 5
         * userid : 15
         * real_name : 李
         * gender : 1
         * mobile : 18633045435
         * location_address : Denhill Lodge
         * coordinate : -38.260924,141.624226
         * detailed_address : 202
         * flag : 1
         * add_sex : 女士
         */

        private String addid;
        private String userid;
        private String real_name;
        private String gender;
        private String mobile;
        private String location_address;
        private String coordinate;
        private String detailed_address;
        private String flag;
        private String add_sex;

        public String getAddid() {
            return addid;
        }

        public void setAddid(String addid) {
            this.addid = addid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getLocation_address() {
            return location_address;
        }

        public void setLocation_address(String location_address) {
            this.location_address = location_address;
        }

        public String getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(String coordinate) {
            this.coordinate = coordinate;
        }

        public String getDetailed_address() {
            return detailed_address;
        }

        public void setDetailed_address(String detailed_address) {
            this.detailed_address = detailed_address;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getAdd_sex() {
            return add_sex;
        }

        public void setAdd_sex(String add_sex) {
            this.add_sex = add_sex;
        }
    }
}
