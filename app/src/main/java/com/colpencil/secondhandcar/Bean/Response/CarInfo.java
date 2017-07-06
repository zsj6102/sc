package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 * 车辆信息情况实体类
 */
public class CarInfo implements Serializable {

    private int goods_id;
    private String goods_name;
    private int is_urgent;
    private int installment;
    private double price;
    private double cost;
    private double deposit;
    private String ins_describe;
    private String owner;
    private String city_name;
    private String intro;
    private long annual_inspection_time;
    private long strong_risk_time;
    private long business_risk_time;
    private int change_num;
    private int is_invoice;
    private int is_four_s;
    private int is_modified;
    private int is_tax;
    private List<PicList> picList;
    private double deposit_rate;
    private double displacement;
    private int transmission;
    private String carNumber;
    private String sn;
    private int country;
    private int drive;
    private int emissions;
    private int fuel;
    private int color;
    private long listed_time;
    private double mileage;
    private int is_collection;
    private int market_enable;

    public int getMarket_enable() {
        return market_enable;
    }

    public void setMarket_enable(int market_enable) {
        this.market_enable = market_enable;
    }

    public int getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(int is_collection) {
        this.is_collection = is_collection;
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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public int getDrive() {
        return drive;
    }

    public void setDrive(int drive) {
        this.drive = drive;
    }

    public int getEmissions() {
        return emissions;
    }

    public void setEmissions(int emissions) {
        this.emissions = emissions;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getTransmission() {
        return transmission;
    }

    public void setTransmission(int transmission) {
        this.transmission = transmission;
    }

    public int getIs_modified() {
        return is_modified;
    }

    public void setIs_modified(int is_modified) {
        this.is_modified = is_modified;
    }

    public int getIs_tax() {
        return is_tax;
    }

    public void setIs_tax(int is_tax) {
        this.is_tax = is_tax;
    }

    public List<PicList> getPicList() {
        return picList;
    }

    public void setPicList(List<PicList> picList) {
        this.picList = picList;
    }


    public double getDeposit_rate() {
        return deposit_rate;
    }

    public void setDeposit_rate(double deposit_rate) {
        this.deposit_rate = deposit_rate;
    }

    public double getDisplacement() {
        return displacement;
    }

    public void setDisplacement(double displacement) {
        this.displacement = displacement;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public long getAnnual_inspection_time() {
        return annual_inspection_time;
    }

    public void setAnnual_inspection_time(long annual_inspection_time) {
        this.annual_inspection_time = annual_inspection_time;
    }

    public long getStrong_risk_time() {
        return strong_risk_time;
    }

    public void setStrong_risk_time(long strong_risk_time) {
        this.strong_risk_time = strong_risk_time;
    }

    public long getBusiness_risk_time() {
        return business_risk_time;
    }

    public void setBusiness_risk_time(long business_risk_time) {
        this.business_risk_time = business_risk_time;
    }

    public int getChange_num() {
        return change_num;
    }

    public void setChange_num(int change_num) {
        this.change_num = change_num;
    }

    public int getIs_invoice() {
        return is_invoice;
    }

    public void setIs_invoice(int is_invoice) {
        this.is_invoice = is_invoice;
    }

    public int getIs_four_s() {
        return is_four_s;
    }

    public void setIs_four_s(int is_four_s) {
        this.is_four_s = is_four_s;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public String getIns_describe() {
        return ins_describe;
    }

    public void setIns_describe(String ins_describe) {
        this.ins_describe = ins_describe;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
