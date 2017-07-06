package com.colpencil.secondhandcar.Model.Mine;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Collection;
import com.colpencil.secondhandcar.Bean.Response.PersonalGroom;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.ICollectionModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/10.
 */
public class CollectionModel implements ICollectionModel {

    private Observable<Result<PersonalGroom>> observable;
    private Observable<Collection> deleteObservable;
    private Observable<Collection> cleanObservable;

    @Override
    public void collectionRecord(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .collectionRecord(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<PersonalGroom>, Result<PersonalGroom>>() {
                    @Override
                    public Result<PersonalGroom> call(Result<PersonalGroom> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<PersonalGroom>> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void deleteCollection(HashMap<String, String> params) {
        deleteObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .deleteCollection(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Collection, Collection>() {
                    @Override
                    public Collection call(Collection collection) {
                        return collection;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subDelete(Subscriber<Collection> subscriber) {
        deleteObservable.subscribe(subscriber);
    }

    @Override
    public void cleanCollection(HashMap<String, String> params) {
        cleanObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .cleanCollection(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Collection, Collection>() {
                    @Override
                    public Collection call(Collection collection) {
                        return collection;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subClean(Subscriber<Collection> subscriber) {
        cleanObservable.subscribe(subscriber);
    }
}
