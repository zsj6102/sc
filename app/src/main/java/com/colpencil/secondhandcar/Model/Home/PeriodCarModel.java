package com.colpencil.secondhandcar.Model.Home;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.PeriodCar;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IPeriodCarModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/12.
 */
public class PeriodCarModel implements IPeriodCarModel {

    private Observable<ResultInfo<PeriodCar>> observable;
    private Observable<Result_comment> commentObservable;

    @Override
    public void installmentCar(int goodsId) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .periodCar(goodsId)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<PeriodCar>, ResultInfo<PeriodCar>>() {
                    @Override
                    public ResultInfo<PeriodCar> call(ResultInfo<PeriodCar> resultInfo) {
                        return resultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo<PeriodCar>> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void advCount(int id) {
        commentObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST)
                .createApi(CarApi.class)
                .advCount(id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result_comment, Result_comment>() {
                    @Override
                    public Result_comment call(Result_comment result_comment) {
                        return result_comment;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subAdv(Subscriber<Result_comment> subscriber) {
        commentObservable.subscribe(subscriber);
    }
}
