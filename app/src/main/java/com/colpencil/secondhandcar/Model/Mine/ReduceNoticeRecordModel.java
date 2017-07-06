package com.colpencil.secondhandcar.Model.Mine;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.MineDepreciateNotice;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IReduceNoticeRecordModel;
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
public class ReduceNoticeRecordModel implements IReduceNoticeRecordModel {

    private Observable<Result<MineDepreciateNotice>> observable;

    @Override
    public void reduceNoticeRecord(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .reduceRocord(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<MineDepreciateNotice>, Result<MineDepreciateNotice>>() {
                    @Override
                    public Result<MineDepreciateNotice> call(Result<MineDepreciateNotice> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<MineDepreciateNotice>> subscriber) {
        observable.subscribe(subscriber);
    }
}
