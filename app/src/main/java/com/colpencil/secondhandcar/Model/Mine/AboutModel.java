package com.colpencil.secondhandcar.Model.Mine;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IAboutModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/9.
 */
public class AboutModel implements IAboutModel {

    private Observable<ResultInfo<Url>> observable;

    @Override
    public void about() {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST)
                .createApi(CarApi.class)
                .about()
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<Url>, ResultInfo<Url>>() {
                    @Override
                    public ResultInfo<Url> call(ResultInfo<Url> aboutUrlResultInfo) {
                        return aboutUrlResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo<Url>> subscriber) {
        observable.subscribe(subscriber);
    }

}
