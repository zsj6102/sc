package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/10.
 */
public class Installment implements Serializable {

    private int installment;
    private String name;
    private boolean ixChecked;

    public boolean isIxChecked() {
        return ixChecked;
    }

    public void setIxChecked(boolean ixChecked) {
        this.ixChecked = ixChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInstallment() {
        return installment;
    }

    public void setInstallment(int installment) {
        this.installment = installment;
    }
}
