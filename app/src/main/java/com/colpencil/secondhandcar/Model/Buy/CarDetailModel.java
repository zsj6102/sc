package com.colpencil.secondhandcar.Model.Buy;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Browse;
import com.colpencil.secondhandcar.Bean.Response.CarInfo;
import com.colpencil.secondhandcar.Bean.Response.Collection;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.ICarDetailModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/5.
 */
public class CarDetailModel implements ICarDetailModel {

    private Observable<ResultInfo<CarInfo>> observable;
    private Observable<Browse> browseObservable;
    private Observable<Collection> collectionObservable;
    private Observable<Result_comment> reduceObservable;

    @Override
    public void carDetail(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .carInfo(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<CarInfo>, ResultInfo<CarInfo>>() {
                    @Override
                    public ResultInfo<CarInfo> call(ResultInfo<CarInfo> carInfoResultInfo) {
                        return carInfoResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo<CarInfo>> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void addBrowse(HashMap<String, String> params) {
        browseObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .addBrowse(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Browse, Browse>() {
                    @Override
                    public Browse call(Browse browse) {
                        return browse;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subBrowse(Subscriber<Browse> browseSubscriber) {
        browseObservable.subscribe(browseSubscriber);
    }

    @Override
    public void addCollection(HashMap<String, String> params) {
        collectionObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .addCollection(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Collection, Collection>() {
                    @Override
                    public Collection call(Collection collection) {
                        return collection;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCollection(Subscriber<Collection> subscriber) {
        collectionObservable.subscribe(subscriber);
    }

    @Override
    public void addReducePrice(HashMap<String, String> params) {
        reduceObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .addReducePrice(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result_comment, Result_comment>() {
                    @Override
                    public Result_comment call(Result_comment result_comment) {
                        return result_comment;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subReducePrice(Subscriber<Result_comment> subscriber) {
        reduceObservable.subscribe(subscriber);
    }
}
