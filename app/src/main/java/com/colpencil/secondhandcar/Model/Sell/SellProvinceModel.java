package com.colpencil.secondhandcar.Model.Sell;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Province;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.ISellProvinceModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/19.
 */
public class SellProvinceModel implements ISellProvinceModel {

    private Observable<ResultInfo<Province>> observable;

    @Override
    public void getProvinceId(String name) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .getCityId(name)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<Province>, ResultInfo<Province>>() {
                    @Override
                    public ResultInfo<Province> call(ResultInfo<Province> resultInfo) {
                        return resultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo<Province>> subscriber) {
        observable.subscribe(subscriber);
    }
}
