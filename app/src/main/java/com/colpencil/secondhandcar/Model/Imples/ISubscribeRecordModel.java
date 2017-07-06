package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.SubscribeGood;
import com.colpencil.secondhandcar.Bean.Result;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/11.
 */
public interface ISubscribeRecordModel {

    void subscribeRecord(HashMap<String, String> params);

    void sub(Subscriber<Result<SubscribeGood>> subscriber);
}
