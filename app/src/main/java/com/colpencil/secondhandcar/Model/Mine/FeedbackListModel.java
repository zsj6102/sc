package com.colpencil.secondhandcar.Model.Mine;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Feedback;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IFeedbackListModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/4.
 */
public class FeedbackListModel implements IFeedbackListModel{

    private Observable<Result<Feedback>> observable;

    @Override
    public void feedbackList(int memberId, String token) {
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST)
                .createApi(CarApi.class)
                .feedbackList(memberId, token)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<Feedback>, Result<Feedback>>() {
                    @Override
                    public Result<Feedback> call(Result<Feedback> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result<Feedback>> subscriber) {
        observable.subscribe(subscriber);
    }
}
