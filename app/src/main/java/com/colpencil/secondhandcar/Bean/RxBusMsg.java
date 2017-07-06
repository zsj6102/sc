package com.colpencil.secondhandcar.Bean;

/**
 * Created by Administrator on 2017/3/24.
 */
public class RxBusMsg {
    private int carType;
    private String carClassify;
    private int isSell;
    private boolean isLogin;
    private int change;
    private int carId;
    private String keyword;
    private int typeId;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getIsSell() {
        return isSell;
    }

    public void setIsSell(int isSell) {
        this.isSell = isSell;
    }

    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public int isSell() {
        return isSell;
    }

    public void setSell(int sell) {
        isSell = sell;
    }

    public String getCarClassify() {
        return carClassify;
    }

    public void setCarClassify(String carClassify) {
        this.carClassify = carClassify;
    }

    public int getCarType() {
        return carType;
    }

    public void setCarType(int carType) {
        this.carType = carType;
    }
}
