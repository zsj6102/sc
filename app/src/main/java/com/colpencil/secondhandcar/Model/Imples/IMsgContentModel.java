package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/15.
 */
public interface IMsgContentModel {

    void msgCntent(int id);

    void sub(Subscriber<ResultInfo<Url>> subscriber);
}
