package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 * 分期购车实体类（支付）
 */
public class PeriodBuyCar implements Serializable {

    private List<InsList> insList;
    private Goods goods;

    public List<InsList> getInsList() {
        return insList;
    }

    public void setInsList(List<InsList> insList) {
        this.insList = insList;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
