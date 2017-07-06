package com.colpencil.secondhandcar.Model.Sell;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.GoodsInfo;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IGoodsInfoModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/22.
 */
public class GoodsInfoModel implements IGoodsInfoModel {

    private Observable<ResultInfo<GoodsInfo>> observable;

    @Override
    public void getGoodsInf(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .getGoodsInfo(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<GoodsInfo>, ResultInfo<GoodsInfo>>() {
                    @Override
                    public ResultInfo<GoodsInfo> call(ResultInfo<GoodsInfo> resultInfo) {
                        return resultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo<GoodsInfo>> subscriber) {
        observable.subscribe(subscriber);
    }
}
