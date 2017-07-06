package com.colpencil.secondhandcar.Model.Mine;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Browse;
import com.colpencil.secondhandcar.Bean.Response.PersonalGroom;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IBrowseModel;
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
public class BrowseModel implements IBrowseModel {

    private Observable<Result<PersonalGroom>> observable;
    private Observable<Browse> deleteObservable;
    private Observable<Browse> cleanObservable;

    @Override
    public void browseRecord(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .browseRecord(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<PersonalGroom>, Result<PersonalGroom>>() {
                    @Override
                    public Result<PersonalGroom> call(Result<PersonalGroom> personalGroomResult) {
                        return  personalGroomResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<PersonalGroom>> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void deleteBrowse(HashMap<String, String> params) {
        deleteObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .deleteBrowse(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Browse, Browse>() {
                    @Override
                    public Browse call(Browse browse) {
                        return browse;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subDelete(Subscriber<Browse> browseSubscriber) {
        deleteObservable.subscribe(browseSubscriber);
    }

    @Override
    public void cleanBrowse(HashMap<String, String> params) {
        cleanObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .cleanBrowse(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Browse, Browse>() {
                    @Override
                    public Browse call(Browse browse) {
                        return browse;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subClean(Subscriber<Browse> browseSubscriber) {
        cleanObservable.subscribe(browseSubscriber);
    }
}
