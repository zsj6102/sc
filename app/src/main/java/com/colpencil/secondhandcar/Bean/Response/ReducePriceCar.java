package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/12.
 */
public class ReducePriceCar implements Serializable {

    private int goods_id;
    private String name;
    private String pic;
    private double price;
    private int is_urgent;
    private int installment;
    private long listed_time;
    private double mileage;
    private int new_shelves;
    private String city_name;
    private int id;
    private long add_time;
    private double reduce_price;
    private int status;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIs_urgent() {
        return is_urgent;
    }

    public void setIs_urgent(int is_urgent) {
        this.is_urgent = is_urgent;
    }

    public int getInstallment() {
        return installment;
    }

    public void setInstallment(int installment) {
        this.installment = installment;
    }

    public long getListed_time() {
        return listed_time;
    }

    public void setListed_time(long listed_time) {
        this.listed_time = listed_time;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public int getNew_shelves() {
        return new_shelves;
    }

    public void setNew_shelves(int new_shelves) {
        this.new_shelves = new_shelves;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public double getReduce_price() {
        return reduce_price;
    }

    public void setReduce_price(double reduce_price) {
        this.reduce_price = reduce_price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
