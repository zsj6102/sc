package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/23.
 */
public class BillDetails implements Serializable {

    private String sn;
    private double set_money;
    private String bank_card;
    private String bank;
    private String name;
    private double price;
    private long set_time;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public double getSet_money() {
        return set_money;
    }

    public void setSet_money(double set_money) {
        this.set_money = set_money;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
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

    public long getSet_time() {
        return set_time;
    }

    public void setSet_time(long set_time) {
        this.set_time = set_time;
    }
}
