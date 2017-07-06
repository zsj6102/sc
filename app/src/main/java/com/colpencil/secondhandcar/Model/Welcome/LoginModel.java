package com.colpencil.secondhandcar.Model.Welcome;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Login;
import com.colpencil.secondhandcar.Bean.Response.SendCode;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.ILoginModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/2.
 */
public class LoginModel implements ILoginModel {

    private Observable<ResultInfo<Login>> observable;
    private Observable<SendCode> codeObservable;

    @Override
    public void login(String mobile, String code) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("mobilecode", code);
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST)
                .createApi(CarApi.class)
                .login(params)
                .map(new Func1<ResultInfo<Login>, ResultInfo<Login>>() {
                    @Override
                    public ResultInfo<Login> call(ResultInfo<Login> loginResult) {
                        return loginResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo<Login>> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void sendCode(String mobile) {
        codeObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST)
                .createApi(CarApi.class)
                .sendCode(mobile)
                .subscribeOn(Schedulers.io())
                .map(new Func1<SendCode, SendCode>() {
                    @Override
                    public SendCode call(SendCode sendCode) {
                        return sendCode;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCode(Subscriber<SendCode> codeSubscriber) {
        codeObservable.subscribe(codeSubscriber);
    }
}
