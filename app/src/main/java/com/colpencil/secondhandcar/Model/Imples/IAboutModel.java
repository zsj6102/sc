package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/9.
 */
public interface IAboutModel {

    void about();

    void sub(Subscriber<ResultInfo<Url>> subscriber);
}
