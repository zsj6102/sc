package com.colpencil.secondhandcar.Model.Home;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.HotSearch;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IHotSearchModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/5.
 */
public class HotSearchModel implements IHotSearchModel {

    private Observable<Result<HotSearch>> observable;

    @Override
    public void hotSearch() {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST)
                .createApi(CarApi.class)
                .hotSearch()
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<HotSearch>, Result<HotSearch>>() {
                    @Override
                    public Result<HotSearch> call(Result<HotSearch> hotSearchResult) {
                        return hotSearchResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<HotSearch>> subscriber) {
        observable.subscribe(subscriber);
    }
}
