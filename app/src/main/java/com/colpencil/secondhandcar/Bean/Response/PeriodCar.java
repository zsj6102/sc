package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */
public class PeriodCar implements Serializable {

    private List<Adv> advList;
    private Goods goods;
    private List<InstallmentCar> insList;

    public List<Adv> getAdvList() {
        return advList;
    }

    public void setAdvList(List<Adv> advList) {
        this.advList = advList;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public List<InstallmentCar> getInsList() {
        return insList;
    }

    public void setInsList(List<InstallmentCar> insList) {
        this.insList = insList;
    }
}
