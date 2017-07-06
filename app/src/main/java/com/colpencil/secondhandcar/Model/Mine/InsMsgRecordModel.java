package com.colpencil.secondhandcar.Model.Mine;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.MsgPeriod;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IInsMsgRecordModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/15.
 */
public class InsMsgRecordModel implements IInsMsgRecordModel {

    private Observable<Result<MsgPeriod>> observable;

    @Override
    public void insMsgRecord(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .insMsgRecord(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<MsgPeriod>, Result<MsgPeriod>>() {
                    @Override
                    public Result<MsgPeriod> call(Result<MsgPeriod> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<MsgPeriod>> subscriber) {
        observable.subscribe(subscriber);
    }
}
