package com.colpencil.secondhandcar.Model.Sell;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.SellAdv;
import com.colpencil.secondhandcar.Bean.Response.SellApply;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IApplySellModel;
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
 * Created by Administrator on 2017/5/4.
 */
public class ApplySellModel implements IApplySellModel {

    private Observable<ResultInfo<SellApply>> observable;
    private Observable<Result<SellAdv>> sellObservable;
    private Observable<Result_comment> commentObservable;

    @Override
    public void applySell(HashMap<String, RequestBody> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST)
                .createApi(CarApi.class)
                .applySell(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<SellApply>, ResultInfo<SellApply>>() {
                    @Override
                    public ResultInfo<SellApply> call(ResultInfo<SellApply> sellApplyResultInfo) {
                        return sellApplyResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo<SellApply>> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void sellData() {
        sellObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST)
                .createApi(CarApi.class)
                .sellData()
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<SellAdv>, Result<SellAdv>>() {
                    @Override
                    public Result<SellAdv> call(Result<SellAdv> resultInfo) {
                        return resultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subSell(Subscriber<Result<SellAdv>> subscriber) {
        sellObservable.subscribe(subscriber);
    }

    @Override
    public void advCount(int id) {
        commentObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST)
                .createApi(CarApi.class)
                .advCount(id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result_comment, Result_comment>() {
                    @Override
                    public Result_comment call(Result_comment result_comment) {
                        return result_comment;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subAdv(Subscriber<Result_comment> subscriber) {
        commentObservable.subscribe(subscriber);
    }
}
