package com.colpencil.secondhandcar.Model.Mine;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.BillDetails;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IBillDetailsModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/23.
 */
public class BillDetailsModel implements IBillDetailsModel {

    private Observable<ResultInfo<BillDetails>> observable;

    @Override
    public void billDetails(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .billDetails(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<BillDetails>, ResultInfo<BillDetails>>() {
                    @Override
                    public ResultInfo<BillDetails> call(ResultInfo<BillDetails> resultInfo) {
                        return resultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo<BillDetails>> subscriber) {
        observable.subscribe(subscriber);
    }
}
