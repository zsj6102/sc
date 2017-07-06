package com.colpencil.secondhandcar.Model.Mine;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.CarResult;
import com.colpencil.secondhandcar.Bean.Response.CarResultRes;
import com.colpencil.secondhandcar.Bean.Response.CarType;
import com.colpencil.secondhandcar.Bean.Response.MineSub;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.ICarTypeModel;
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
public class CarTypeModel implements ICarTypeModel {

    private Observable<Result<CarType>> observable;
    private Observable<MineSub> addObservable;
    private Observable<ResultInfo<CarResultRes>> resultObservable;

    @Override
    public void carType() {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .carType()
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<CarType>, Result<CarType>>() {
                    @Override
                    public Result<CarType> call(Result<CarType> carTypeResultInfo) {
                        return carTypeResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<CarType>> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void addSubscribe(HashMap<String, String> params) {
        addObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .addSubscribe(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<MineSub, MineSub>() {
                    @Override
                    public MineSub call(MineSub mineSub) {
                        return mineSub;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subAdd(Subscriber<MineSub> subscriber) {
        addObservable.subscribe(subscriber);
    }

    @Override
    public void searchResult(HashMap<String, String> params) {
        resultObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
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
    public void subResult(Subscriber<ResultInfo<CarResultRes>> subscriber) {
        resultObservable.subscribe(subscriber);
    }
}
