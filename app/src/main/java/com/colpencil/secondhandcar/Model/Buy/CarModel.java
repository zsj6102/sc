package com.colpencil.secondhandcar.Model.Buy;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.BrandCar;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.ICarModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/9.
 */
public class CarModel implements ICarModel {

    private Observable<Result<BrandCar>> observable;

    @Override
    public void carList(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .carList(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<BrandCar>, Result<BrandCar>>() {
                    @Override
                    public Result<BrandCar> call(Result<BrandCar> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<BrandCar>> subscriber) {
        observable.subscribe(subscriber);
    }
}
