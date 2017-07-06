package com.colpencil.secondhandcar.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */
public class Result<T> implements Serializable {

    private int result;
    private int code;
    private String message;
    private List<T> data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
