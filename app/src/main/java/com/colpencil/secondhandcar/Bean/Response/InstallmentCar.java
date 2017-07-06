package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/12.
 */
public class InstallmentCar implements Serializable {

    private String installment;
    private double ins_price;
    private int ins_num;

    public String getInstallment() {
        return installment;
    }

    public void setInstallment(String installment) {
        this.installment = installment;
    }

    public double getIns_price() {
        return ins_price;
    }

    public void setIns_price(double ins_price) {
        this.ins_price = ins_price;
    }

    public int getIns_num() {
        return ins_num;
    }

    public void setIns_num(int ins_num) {
        this.ins_num = ins_num;
    }
}
