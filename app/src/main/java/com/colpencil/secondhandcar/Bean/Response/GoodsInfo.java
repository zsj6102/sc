package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */
public class GoodsInfo implements Serializable {

    private int goods_id;
    private String goods_name;
    private int is_urgent;
    private int installment;
    private double price;
    private double cost;
    private String owner;
    private int privince_id;
    private int city_id;
    private String city_name;
    private String intro;
    private long listed_time;
    private long annual_inspection_time;
    private long strong_risk_time;
    private long business_risk_time;
    private int change_num;
    private int is_invoice;
    private int is_four_s;
    private int is_modified;
    private String situation;
    private int is_local_license_plate;

    public int getIs_local_license_plate() {
        return is_local_license_plate;
    }

    public void setIs_local_license_plate(int is_local_license_plate) {
        this.is_local_license_plate = is_local_license_plate;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    private int is_tax;
    private List<PicList> picList;
    private double displacement;
    private int transmission;
    private int country;
    private int drive;
    private int emissions;
    private int fuel;
    private int color;
    private double mileage;
    private int card_privince_id;
    private int card_city_id;
    private String card_city_name;
    private int type_id;
    private String type_name;
    private int cat_id;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getPrivince_id() {
        return privince_id;
    }

    public void setPrivince_id(int privince_id) {
        this.privince_id = privince_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public long getListed_time() {
        return listed_time;
    }

    public void setListed_time(long listed_time) {
        this.listed_time = listed_time;
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

    public double getDisplacement() {
        return displacement;
    }

    public void setDisplacement(double displacement) {
        this.displacement = displacement;
    }

    public int getTransmission() {
        return transmission;
    }

    public void setTransmission(int transmission) {
        this.transmission = transmission;
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

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public int getCard_privince_id() {
        return card_privince_id;
    }

    public void setCard_privince_id(int card_privince_id) {
        this.card_privince_id = card_privince_id;
    }

    public int getCard_city_id() {
        return card_city_id;
    }

    public void setCard_city_id(int card_city_id) {
        this.card_city_id = card_city_id;
    }

    public String getCard_city_name() {
        return card_city_name;
    }

    public void setCard_city_name(String card_city_name) {
        this.card_city_name = card_city_name;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }
}
