package com.colpencil.secondhandcar.Model.Mine;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.SubscribeGood;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.ISubscribeRecordModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/11.
 */
public class SubscribeRecordModel implements ISubscribeRecordModel {

    private Observable<Result<SubscribeGood>> observable;

    @Override
    public void subscribeRecord(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .subscribeRecord(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<SubscribeGood>, Result<SubscribeGood>>() {
                    @Override
                    public Result<SubscribeGood> call(Result<SubscribeGood> subscribeGoodResult) {
                        return subscribeGoodResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<SubscribeGood>> subscriber) {
        observable.subscribe(subscriber);
    }
}
