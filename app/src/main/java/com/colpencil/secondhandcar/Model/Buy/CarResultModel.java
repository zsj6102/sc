package com.colpencil.secondhandcar.Model.Buy;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.CarResult;
import com.colpencil.secondhandcar.Bean.Response.CarResultRes;
import com.colpencil.secondhandcar.Bean.Response.Collection;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.ICarResultModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/16.
 */
public class CarResultModel implements ICarResultModel {

    private Observable<ResultInfo<CarResultRes>> observable;
    private Observable<Collection> collectionObservable;

    @Override
    public void carResult(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .carResult(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<CarResultRes>, ResultInfo<CarResultRes>>() {
                    @Override
                    public ResultInfo<CarResultRes> call(ResultInfo<CarResultRes> resultResult) {
                        return resultResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo<CarResultRes>> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void collection(HashMap<String, String> params) {
        collectionObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .addCollection(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Collection, Collection>() {
                    @Override
                    public Collection call(Collection collection) {
                        return collection;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCollection(Subscriber<Collection> subscriber) {
        collectionObservable.subscribe(subscriber);
    }
}
