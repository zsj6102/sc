package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/12.
 */
public class Goods implements Serializable {

    private String name;
    private double price;
    private long create_time;
    private double mileage;
    private String city_name;
    private int is_urgent;
    private int installment;
    private int new_shelves;
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
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

    public int getNew_shelves() {
        return new_shelves;
    }

    public void setNew_shelves(int new_shelves) {
        this.new_shelves = new_shelves;
    }
}
