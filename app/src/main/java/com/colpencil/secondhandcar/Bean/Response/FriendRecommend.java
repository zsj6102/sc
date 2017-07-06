package com.colpencil.secondhandcar.Bean.Response;

/**
 * Created by Administrator on 2017/3/22.
 */

public class FriendRecommend {

    private int goods_id;
    private String name;
    private String original;
    private double price;
    private int is_urgent;
    private int installment;
    private long listed_time;
    private double mileage;
    private int new_shelves;

    public int getNew_shelves() {
        return new_shelves;
    }

    public void setNew_shelves(int new_shelves) {
        this.new_shelves = new_shelves;
    }

    private String city_name;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public long getListed_time() {
        return listed_time;
    }

    public void setListed_time(long listed_time) {
        this.listed_time = listed_time;
    }

    public int getInstallment() {
        return installment;
    }

    public void setInstallment(int installment) {
        this.installment = installment;
    }

    public int getIs_urgent() {
        return is_urgent;
    }

    public void setIs_urgent(int is_urgent) {
        this.is_urgent = is_urgent;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
