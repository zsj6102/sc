package com.colpencil.secondhandcar.Model.Mine;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Logout;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.ILogoutModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/5.
 */
public class LogoutModel implements ILogoutModel {

    private Observable<Logout> observable;


    @Override
    public void logout(int memberId, String token) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST)
                .createApi(CarApi.class)
                .logout(memberId)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Logout, Logout>() {
                    @Override
                    public Logout call(Logout logout) {
                        return logout;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Logout> subscriber) {
        observable.subscribe(subscriber);
    }
}
