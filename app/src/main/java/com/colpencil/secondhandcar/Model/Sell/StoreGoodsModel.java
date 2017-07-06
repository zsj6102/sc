package com.colpencil.secondhandcar.Model.Sell;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.SellCarRecord;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IStoreGoodsModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/22.
 */
public class StoreGoodsModel implements IStoreGoodsModel {

    private Observable<Result<SellCarRecord>> observable;
    private Observable<Result_comment> shelvesObservable;

    @Override
    public void storeGoods(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .storeGoods(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<SellCarRecord>, Result<SellCarRecord>>() {
                    @Override
                    public Result<SellCarRecord> call(Result<SellCarRecord> recordResult) {
                        return recordResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<SellCarRecord>> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void shelves(HashMap<String, String> params) {
        shelvesObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .shelves(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result_comment, Result_comment>() {
                    @Override
                    public Result_comment call(Result_comment result_comment) {
                        return result_comment;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subShelves(Subscriber<Result_comment> subscriber) {
        shelvesObservable.subscribe(subscriber);
    }
}
