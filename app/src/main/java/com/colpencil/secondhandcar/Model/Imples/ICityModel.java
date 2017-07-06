package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.City;
import com.colpencil.secondhandcar.Bean.Result;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/3.
 */
public interface ICityModel {

    void getCity();

    void sub(Subscriber<Result<City>> subscriber);
}
