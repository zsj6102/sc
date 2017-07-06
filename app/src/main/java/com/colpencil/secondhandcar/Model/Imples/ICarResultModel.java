package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.CarResult;
import com.colpencil.secondhandcar.Bean.Response.CarResultRes;
import com.colpencil.secondhandcar.Bean.Response.Collection;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/16.
 */
public interface ICarResultModel {

    void carResult(HashMap<String, String> params);

    void sub(Subscriber<ResultInfo<CarResultRes>> subscriber);

    void collection(HashMap<String, String> params);

    void subCollection(Subscriber<Collection> subscriber);
}
