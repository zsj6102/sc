package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Browse;
import com.colpencil.secondhandcar.Bean.Response.CarInfo;
import com.colpencil.secondhandcar.Bean.Response.Collection;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/5.
 */
public interface ICarDetailModel {

    void carDetail(HashMap<String, String> params);

    void sub(Subscriber<ResultInfo<CarInfo>> subscriber);

    void addBrowse(HashMap<String, String> params);

    void subBrowse(Subscriber<Browse> browseSubscriber);

    void addCollection(HashMap<String, String> params);

    void  subCollection(Subscriber<Collection> subscriber);

    void addReducePrice(HashMap<String, String> params);

    void subReducePrice(Subscriber<Result_comment> subscriber);
}
