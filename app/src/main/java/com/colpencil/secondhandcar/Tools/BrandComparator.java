package com.colpencil.secondhandcar.Tools;

import com.colpencil.secondhandcar.Bean.Response.BrandCar;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/5/8.
 */
public class BrandComparator implements Comparator<BrandCar> {

    @Override
    public int compare(BrandCar o1, BrandCar o2) {
        if (o2.getPinYin().equals("#")) {
            return -1;
        } else if (o1.getPinYin().equals("#")) {
            return 1;
        } else {
            return o1.getPinYin().compareTo(o2.getPinYin());
        }
    }
}
