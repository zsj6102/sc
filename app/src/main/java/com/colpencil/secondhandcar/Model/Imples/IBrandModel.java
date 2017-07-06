package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.BrandList;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/4.
 */
public interface IBrandModel {

    void brandList(HashMap<String, String> params);

    void sub(Subscriber<ResultInfo<BrandList>> subscriber);
}
