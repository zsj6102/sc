package com.colpencil.secondhandcar.Model.Mine;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Order;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IOrderModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/23.
 */
public class OrderModel implements IOrderModel {

    private Observable<Result<Order>> observable;

    @Override
    public void orderRecord(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .storeOrder(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<Order>, Result<Order>>() {
                    @Override
                    public Result<Order> call(Result<Order> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<Order>> subscriber) {
        observable.subscribe(subscriber);
    }
}
