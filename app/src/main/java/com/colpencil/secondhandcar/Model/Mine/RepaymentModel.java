package com.colpencil.secondhandcar.Model.Mine;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Repayment;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IRepaymentModel;
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
public class RepaymentModel implements IRepaymentModel {

    private Observable<Result<Repayment>> observable;

    @Override
    public void repayment(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .repayment(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<Repayment>, Result<Repayment>>() {
                    @Override
                    public Result<Repayment> call(Result<Repayment> repaymentResult) {
                        return repaymentResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<Repayment>> subscriber) {
        observable.subscribe(subscriber);
    }
}
