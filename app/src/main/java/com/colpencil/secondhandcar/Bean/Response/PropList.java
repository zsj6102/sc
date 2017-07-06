package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/5.
 * 属性列表
 */
public class PropList implements Serializable {

    private String pro_name;
    private int pro_type;

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public int getPro_type() {
        return pro_type;
    }

    public void setPro_type(int pro_type) {
        this.pro_type = pro_type;
    }
}
