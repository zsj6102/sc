package com.colpencil.secondhandcar.Model.Home;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.City;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.ICityModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/3.
 */
public class GetCityModel implements ICityModel {

    private Observable<Result<City>> observable;

    @Override
    public void getCity() {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .getCity()
                .map(new Func1<Result<City>, Result<City>>() {
                    @Override
                    public Result<City> call(Result<City> cityResult) {
                        return cityResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<City>> subscriber) {
        observable.subscribe(subscriber);
    }
}
