package com.colpencil.secondhandcar.Tools;

import com.colpencil.secondhandcar.Bean.City;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/5/3.
 */
public class PinYinComparator implements Comparator<City> {

    @Override
    public int compare(City o1, City o2) {
        if (o2.getPinyin().equals("#")) {
            return -1;
        } else if (o1.getPinyin().equals("#")) {
            return 1;
        } else {
            return o1.getPinyin().compareTo(o2.getPinyin());
        }
    }
}
