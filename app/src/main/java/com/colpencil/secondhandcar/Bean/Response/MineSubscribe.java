package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 * 我的订阅实体类
 */
public class MineSubscribe implements Serializable {

    private int id;
    private List<SubscribeGood> goodsList;
    private int new_num;
    private ArrayList<String> typeCatList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<SubscribeGood> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<SubscribeGood> goodsList) {
        this.goodsList = goodsList;
    }

    public int getNew_num() {
        return new_num;
    }

    public void setNew_num(int new_num) {
        this.new_num = new_num;
    }

    public ArrayList<String> getTypeCatList() {
        return typeCatList;
    }

    public void setTypeCatList(ArrayList<String> typeCatList) {
        this.typeCatList = typeCatList;
    }
}
