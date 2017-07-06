package com.colpencil.secondhandcar.Model.Sell;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Installment;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IAddGoodsModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/17.
 */
public class AddGoodsModel implements IAddGoodsModel {

    private Observable<Result_comment> observable;
    private Observable<Result<Installment>> periodObservable;

    @Override
    public void addGoods(HashMap<String, RequestBody> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE)
                .createApi(CarApi.class)
                .addGoods(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result_comment, Result_comment>() {
                    @Override
                    public Result_comment call(Result_comment result_comment) {
                        return result_comment;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result_comment> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void getPeriodNum() {
        periodObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .installment()
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<Installment>, Result<Installment>>() {
                    @Override
                    public Result<Installment> call(Result<Installment> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subPeriod(Subscriber<Result<Installment>> subscriber) {
        periodObservable.subscribe(subscriber);
    }
}
