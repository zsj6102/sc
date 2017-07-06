package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/28.
 * 品牌车名
 */
public class BrandCarInfo implements Serializable {

    private int car_id;

    public BrandCarInfo(int id){
        this.car_id = id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }
}
