package com.colpencil.secondhandcar.Model.Buy;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.BrandList;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IBrandModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/4.
 */
public class BrandModel implements IBrandModel {

    private Observable<ResultInfo<BrandList>> observable;

    @Override
    public void brandList(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .brandList(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<BrandList>, ResultInfo<BrandList>>() {
                    @Override
                    public ResultInfo<BrandList> call(ResultInfo<BrandList> brandCarResult) {
                        return brandCarResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo<BrandList>> subscriber) {
        observable.subscribe(subscriber);
    }
}
