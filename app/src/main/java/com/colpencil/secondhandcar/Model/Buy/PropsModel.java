package com.colpencil.secondhandcar.Model.Buy;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Props;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IPropsModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/12.
 */
public class PropsModel implements IPropsModel {

    private Observable<ResultInfo<Props>> observable;

    @Override
    public void getProps(int typeId) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .getProps(typeId)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<Props>, ResultInfo<Props>>() {
                    @Override
                    public ResultInfo<Props> call(ResultInfo<Props> resultInfo) {
                        return resultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo<Props>> subscriber) {
        observable.subscribe(subscriber);
    }
}
