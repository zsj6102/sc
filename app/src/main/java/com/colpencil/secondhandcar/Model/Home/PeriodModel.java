package com.colpencil.secondhandcar.Model.Home;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Installment;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IPeriodModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/10.
 */
public class PeriodModel implements IPeriodModel {

    private Observable<Result<Installment>> observable;

    @Override
    public void installment() {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .installment()
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<Installment>, Result<Installment>>() {
                    @Override
                    public Result<Installment> call(Result<Installment> installmentResult) {
                        return installmentResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<Installment>> subscriber) {
        observable.subscribe(subscriber);
    }
}
