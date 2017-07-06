package com.colpencil.secondhandcar.Model.Buy;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.PeriodBuyCar;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IPeriodBuyModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/24.
 */
public class PeriodBuyModel implements IPeriodBuyModel {

    private Observable<ResultInfo<PeriodBuyCar>> observable;
    private Observable<Result_comment> commentObservable;

    @Override
    public void goInstallment(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .goInstallment(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<PeriodBuyCar>, ResultInfo<PeriodBuyCar>>() {
                    @Override
                    public ResultInfo<PeriodBuyCar> call(ResultInfo<PeriodBuyCar> resultInfo) {
                        return resultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo<PeriodBuyCar>> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void create(HashMap<String, String> params) {
        commentObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .createInstallment(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result_comment, Result_comment>() {
                    @Override
                    public Result_comment call(Result_comment result_comment) {
                        return result_comment;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCreate(Subscriber<Result_comment> subscriber) {
        commentObservable.subscribe(subscriber);
    }
}
