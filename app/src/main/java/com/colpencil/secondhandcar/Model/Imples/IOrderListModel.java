package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Order;
import com.colpencil.secondhandcar.Bean.Result;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/23.
 */
public interface IOrderListModel {

    void orderList(HashMap<String, String> params);

    void sub(Subscriber<Result<Order>> subscriber);
}
