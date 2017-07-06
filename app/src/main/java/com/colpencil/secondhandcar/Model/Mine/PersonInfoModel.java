package com.colpencil.secondhandcar.Model.Mine;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.PersonInfo;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IPersonInfoModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/26.
 */
public class PersonInfoModel implements IPersonInfoModel {

    private Observable<ResultInfo<PersonInfo>> observable;
    private Observable<Result_comment> commentObservable;

    @Override
    public void personInfo(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .personInfo(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<PersonInfo>, ResultInfo<PersonInfo>>() {
                    @Override
                    public ResultInfo<PersonInfo> call(ResultInfo<PersonInfo> resultInfo) {
                        return resultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo<PersonInfo>> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void changeInfo(HashMap<String, String> params) {
        commentObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .changeInfo(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result_comment, Result_comment>() {
                    @Override
                    public Result_comment call(Result_comment result_comment) {
                        return result_comment;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subChange(Subscriber<Result_comment> subscriber) {
        commentObservable.subscribe(subscriber);
    }
}
