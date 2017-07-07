package com.colpencil.secondhandcar.Model.Home;

import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.FriendRecommend;
import com.colpencil.secondhandcar.Bean.Response.Home;
import com.colpencil.secondhandcar.Bean.Response.MessageCount;
import com.colpencil.secondhandcar.Bean.Response.MessageInfo;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.Subscribe;
import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Model.Imples.IRecommendModel;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.umeng.analytics.pro.x.F;
import static com.umeng.analytics.pro.x.S;

/**
 * Created by Administrator on 2017/5/3.
 */
public class RecommendModel implements IRecommendModel {

    private Observable<Result<FriendRecommend>> observable;
    private Observable<Subscribe> subscribeObservable;
    private Observable<Result_comment> commentObservable;
    private Observable<ResultInfo<MessageInfo>> popupObservable;
    private Observable<Result<MessageCount>> messageObservable;
    @Override
    public void getRecommend(int pageNo, int pageSize, int cityId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("pageNo", pageNo+"");
        params.put("pageSize", pageSize+"");
        params.put("city_id", cityId+"");
        observable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .getRecommend(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<FriendRecommend>, Result<FriendRecommend>>() {
                    @Override
                    public Result<FriendRecommend> call(Result<FriendRecommend> friendRecommendResult) {
                        return friendRecommendResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void getMessageCount(int memeber_id,String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", memeber_id+"");
        params.put("token", token);
        messageObservable = RetrofitManager.getInstance(1,CarApplication.getInstance(),UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .getMessageCount(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result<MessageCount>,Result<MessageCount>>(){
                    @Override
                    public Result<MessageCount> call(Result<MessageCount> messageCountResult){
                        return  messageCountResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subMessage(Subscriber<Result<MessageCount>> messageResult) {
        messageObservable.subscribe(messageResult);
    }

    @Override
    public void sub(Subscriber<Result<FriendRecommend>> resultSubscriber) {
        observable.subscribe(resultSubscriber);

    }

    private Observable<ResultInfo<Home>> observableHome;

    @Override
    public void homeInfo() {
        observableHome = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST)
                .createApi(CarApi.class)
                .homeInfo()
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<Home>, ResultInfo<Home>>() {
                    @Override
                    public ResultInfo<Home> call(ResultInfo<Home> homeResultInfo) {
                        return homeResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subHome(Subscriber<ResultInfo<Home>> subscriber) {
        observableHome.subscribe(subscriber);
    }

    @Override
    public void subscribeNum(HashMap<String, String> params) {
        subscribeObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .homeSubscribeNum(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Subscribe, Subscribe>() {
                    @Override
                    public Subscribe call(Subscribe subscribe) {
                        return subscribe;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subSubscribe(Subscriber<Subscribe> subscribe) {
        subscribeObservable.subscribe(subscribe);
    }

    @Override
    public void advCount(int id) {
        commentObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST)
                .createApi(CarApi.class)
                .advCount(id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result_comment, Result_comment>() {
                    @Override
                    public Result_comment call(Result_comment result_comment) {
                        return result_comment;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subAdv(Subscriber<Result_comment> subscriber) {
        commentObservable.subscribe(subscriber);
    }

    @Override
    public void popup() {
        popupObservable = RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                .createApi(CarApi.class)
                .popup()
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<MessageInfo>, ResultInfo<MessageInfo>>() {
                    @Override
                    public ResultInfo<MessageInfo> call(ResultInfo<MessageInfo> resultInfo) {
                        return resultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subPopup(Subscriber<ResultInfo<MessageInfo>> subscriber) {
        popupObservable.subscribe(subscriber);
    }
}
