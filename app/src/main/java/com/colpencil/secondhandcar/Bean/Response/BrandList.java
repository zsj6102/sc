package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 */
public class BrandList implements Serializable {

    private List<BrandCar> hot_list;
    private List<BrandCar> cat_list;

    public List<BrandCar> getHot_list() {
        return hot_list;
    }

    public void setHot_list(List<BrandCar> hot_list) {
        this.hot_list = hot_list;
    }

    public List<BrandCar> getCat_list() {
        return cat_list;
    }

    public void setCat_list(List<BrandCar> cat_list) {
        this.cat_list = cat_list;
    }
}
