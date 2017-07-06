package com.colpencil.secondhandcar.Model.Mine;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.MineRemind;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IReducePriceModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/12.
 */
public class ReducePriceModel implements IReducePriceModel {

    private Observable<ResultInfo<MineRemind>> observable;
    private Observable<Result_comment> cleanObservable;
    private Observable<Result_comment> deleteObservable;

    @Override
    public void reducePrice(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .redecuPriceRecord(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<MineRemind>, ResultInfo<MineRemind>>() {
                    @Override
                    public ResultInfo<MineRemind> call(ResultInfo<MineRemind> remindResult) {
                        return remindResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo<MineRemind>> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void deleteReduce(HashMap<String, String> params) {
        deleteObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .deleteReduce(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result_comment, Result_comment>() {
                    @Override
                    public Result_comment call(Result_comment result_comment) {
                        return result_comment;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subDelete(Subscriber<Result_comment> subscriber) {
        deleteObservable.subscribe(subscriber);
    }

    @Override
    public void cleanRecord(HashMap<String, String> params) {
        cleanObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .cleanReduceRecord(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result_comment, Result_comment>() {
                    @Override
                    public Result_comment call(Result_comment result_comment) {
                        return result_comment;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subClean(Subscriber<Result_comment> subscriber) {
        cleanObservable.subscribe(subscriber);
    }
}
