package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Province;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/19.
 */
public interface ISellProvinceModel {

    void getProvinceId(String name);

    void sub(Subscriber<ResultInfo<Province>> subscriber);
}
