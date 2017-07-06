package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 * 我的降价提醒实体类
 */
public class MineRemind implements Serializable {

    private List<ReducePriceCar> goodsList;

    private List<ReducePriceCar> soldList;

    public List<ReducePriceCar> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<ReducePriceCar> goodsList) {
        this.goodsList = goodsList;
    }

    public List<ReducePriceCar> getSoldList() {
        return soldList;
    }

    public void setSoldList(List<ReducePriceCar> soldList) {
        this.soldList = soldList;
    }
}
