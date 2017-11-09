package com.australia.administrator.australiandelivery.bean;

/**
 * Created by Administrator on 2017/7/8.
 */

public class UserBean {

    /**
     * status : 1
     * msg : {"username":"EA_15230868185","mobile":"15230868185","head_photo":"","head_pic":""}
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
         * username : EA_15230868185
         * mobile : 15230868185
         * head_photo :
         * head_pic :
         */

        private String username;
        private String mobile;
        private String head_photo;
        private String head_pic;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getHead_photo() {
            return head_photo;
        }

        public void setHead_photo(String head_photo) {
            this.head_photo = head_photo;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }
    }
}
