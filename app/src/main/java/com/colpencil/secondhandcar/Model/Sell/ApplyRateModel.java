package com.colpencil.secondhandcar.Model.Sell;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.ApplyRate;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IApplyRateModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/16.
 */
public class ApplyRateModel implements IApplyRateModel {

    private Observable<ResultInfo<ApplyRate>> observable;

    @Override
    public void applyRate(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST)
                .createApi(CarApi.class)
                .applyRate(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<ApplyRate>, ResultInfo<ApplyRate>>() {
                    @Override
                    public ResultInfo<ApplyRate> call(ResultInfo<ApplyRate> resultInfo) {
                        return resultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo<ApplyRate>> subscriber) {
        observable.subscribe(subscriber);
    }
}
