package com.colpencil.secondhandcar.Model.Mine;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.MessageInfo;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.ISystemMsgRecordModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/12.
 */
public class SystemMsgRecordModel implements ISystemMsgRecordModel {

    private Observable<Result<MessageInfo>> observable;

    @Override
    public void systemMsgRecord(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .systemMessageRecord(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<MessageInfo>, Result<MessageInfo>>() {
                    @Override
                    public Result<MessageInfo> call(Result<MessageInfo> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<MessageInfo>> subscriber) {
        observable.subscribe(subscriber);
    }
}
