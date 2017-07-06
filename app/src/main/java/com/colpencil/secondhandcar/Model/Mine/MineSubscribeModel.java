package com.colpencil.secondhandcar.Model.Mine;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.MineSub;
import com.colpencil.secondhandcar.Bean.Response.MineSubscribe;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IMineSubscribeModel;
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
public class MineSubscribeModel implements IMineSubscribeModel {

    private Observable<Result<MineSubscribe>> observable;
    private Observable<MineSub> subObservable;
    private Observable<Result_comment> result_commentObservable;

    @Override
    public void mineSubscribe(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .mineSubscribe(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<MineSubscribe>, Result<MineSubscribe>>() {
                    @Override
                    public Result<MineSubscribe> call(Result<MineSubscribe> mineSubscribeResultInfo) {
                        return mineSubscribeResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<MineSubscribe>> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void deleteSubscribe(HashMap<String, String> params) {
        subObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .deleteSubscribe(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<MineSub, MineSub>() {
                    @Override
                    public MineSub call(MineSub mineSub) {
                        return mineSub;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subDelete(Subscriber<MineSub> subSubscriber) {
        subObservable.subscribe(subSubscriber);
    }

    @Override
    public void cleanSubscribe(HashMap<String, String> params) {
        result_commentObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .cleanSubscribe(params)
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
        result_commentObservable.subscribe(subscriber);
    }
}
