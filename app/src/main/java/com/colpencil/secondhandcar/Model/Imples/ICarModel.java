package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.BrandCar;
import com.colpencil.secondhandcar.Bean.Result;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/9.
 */
public interface ICarModel {

    void carList(HashMap<String, String> params);

    void sub(Subscriber<Result<BrandCar>> subscriber);
}
