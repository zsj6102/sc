package com.colpencil.secondhandcar.Bean.Response;

import com.colpencil.secondhandcar.Bean.OrderInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/11.
 */
public class Result_comment implements Serializable {

    private int code;
    private String message;
    private OrderInfo data;

    public OrderInfo getData() {
        return data;
    }

    public void setData(OrderInfo data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
