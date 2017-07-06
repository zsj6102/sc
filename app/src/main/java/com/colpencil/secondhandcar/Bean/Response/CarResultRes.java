package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 * 车辆信息情况实体类
 */
public class CarResultRes implements Serializable {

    private int goodsCount;
    private List<CarResult> goodsList;

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public List<CarResult> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<CarResult> goodsList) {
        this.goodsList = goodsList;
    }
}
