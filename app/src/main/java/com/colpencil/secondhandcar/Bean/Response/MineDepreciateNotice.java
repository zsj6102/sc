package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/10.
 * 降价通知实体类
 */
public class MineDepreciateNotice implements Serializable {

    private int goods_id;
    private String title;
    private long create_time;
    private String name;
    private double price;
    private double reduce_price;
    private String pic;
    private int market_enable;

    public int getMarket_enable() {
        return market_enable;
    }

    public void setMarket_enable(int market_enable) {
        this.market_enable = market_enable;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getReduce_price() {
        return reduce_price;
    }

    public void setReduce_price(double reduce_price) {
        this.reduce_price = reduce_price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
