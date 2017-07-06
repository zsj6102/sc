package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/16.
 */
public class SellAdv implements Serializable {

    private String atturl;
    private int adv_type;
    private String url;
    private int goods_id;
    private int aid;

    public String getAtturl() {
        return atturl;
    }

    public void setAtturl(String atturl) {
        this.atturl = atturl;
    }

    public int getAdv_type() {
        return adv_type;
    }

    public void setAdv_type(int adv_type) {
        this.adv_type = adv_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }
}
