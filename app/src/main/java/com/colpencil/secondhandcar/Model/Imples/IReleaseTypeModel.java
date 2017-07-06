package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.CarType;
import com.colpencil.secondhandcar.Bean.Result;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/18.
 */
public interface IReleaseTypeModel {

    void getType();

    void sub(Subscriber<Result<CarType>> subscriber);
}
