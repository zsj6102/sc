package com.colpencil.secondhandcar.Model.Sell;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.CarType;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IReleaseTypeModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/18.
 */
public class ReleaseTypeModel implements IReleaseTypeModel {

    private Observable<Result<CarType>> observable;

    @Override
    public void getType() {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .carType()
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<CarType>, Result<CarType>>() {
                    @Override
                    public Result<CarType> call(Result<CarType> carTypeResult) {
                        return carTypeResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<CarType>> subscriber) {
        observable.subscribe(subscriber);
    }
}
