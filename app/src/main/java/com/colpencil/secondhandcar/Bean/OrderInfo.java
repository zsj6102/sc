package com.colpencil.secondhandcar.Bean;

import java.io.Serializable;

public class OrderInfo implements Serializable {
    private String sn;
    private long look_time;
    private double order_amount;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    private int order_id;
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public long getLook_time() {
        return look_time;
    }

    public void setLook_time(long look_time) {
        this.look_time = look_time;
    }

    public double getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(double order_amount) {
        this.order_amount = order_amount;
    }
}
