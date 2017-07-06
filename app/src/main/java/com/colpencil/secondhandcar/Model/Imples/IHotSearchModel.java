package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.HotSearch;
import com.colpencil.secondhandcar.Bean.Result;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/5.
 */
public interface IHotSearchModel {

    void hotSearch();

    void sub(Subscriber<Result<HotSearch>> subscriber);
}
