package com.australia.administrator.australiandelivery.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

public class EmptyBean {
    /**
     * status : 9
     * msg : []
     */

    private int status;
    private List<?> msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<?> getMsg() {
        return msg;
    }

    public void setMsg(List<?> msg) {
        this.msg = msg;
    }
}
