package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/7.
 */
public class ColorClassify implements Serializable {

    private int resId;
    private String color;

    public ColorClassify(int id, String name){
        this.resId = id;
        this.color = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
