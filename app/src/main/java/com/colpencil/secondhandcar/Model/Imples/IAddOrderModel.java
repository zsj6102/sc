package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Result_comment;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/23.
 */
public interface IAddOrderModel {

    void addOrder(HashMap<String, String> params);

    void sub(Subscriber<Result_comment> subscriber);
}
