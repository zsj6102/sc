package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.CarResult;
import com.colpencil.secondhandcar.Bean.Response.CarResultRes;
import com.colpencil.secondhandcar.Bean.Response.CarType;
import com.colpencil.secondhandcar.Bean.Response.MineSub;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/11.
 */
public interface ICarTypeModel {

    void carType();

    void sub(Subscriber<Result<CarType>>subscriber);

    void addSubscribe(HashMap<String, String> params);

    void subAdd(Subscriber<MineSub> subscriber);

    void searchResult(HashMap<String, String> params);

    void subResult(Subscriber<ResultInfo<CarResultRes>> subscriber);

}
