package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/6/1.
 */
public interface IEditGoodModel {

    void editGood(HashMap<String, String> params);

    void sub(Subscriber<ResultInfo<Url>> subscriber);
}
