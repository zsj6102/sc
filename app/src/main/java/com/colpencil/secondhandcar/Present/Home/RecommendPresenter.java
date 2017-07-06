package com.colpencil.secondhandcar.Present.Home;

import com.colpencil.secondhandcar.Bean.Response.FriendRecommend;
import com.colpencil.secondhandcar.Bean.Response.Home;
import com.colpencil.secondhandcar.Bean.Response.MessageInfo;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.Subscribe;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Home.RecommendModel;
import com.colpencil.secondhandcar.Model.Imples.IRecommendModel;
import com.colpencil.secondhandcar.Views.Imples.Home.RecommendView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/3.
 */
public class RecommendPresenter extends ColpencilPresenter<RecommendView> {

    private IRecommendModel model;

    public RecommendPresenter(){
        model = new RecommendModel();
    }

    public void getRecommend(final int pageNo, int pageSize, int cityId){
        model.getRecommend(pageNo, pageSize, cityId);
        Subscriber<Result<FriendRecommend>> subscriber = new Subscriber<Result<FriendRecommend>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<FriendRecommend> result) {
                if(result.getCode() == 1){
                    if(pageNo == 1){
                        mView.refresh(result);
                    } else {
                        mView.loadMore(result);
                    }
                } else{
                    mView.loadError(result.getMessage());
                }
            }
        };
        model.sub(subscriber);
    }

    public void homeInfo(){
        model.homeInfo();
        Subscriber<ResultInfo<Home>> subscriber = new Subscriber<ResultInfo<Home>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<Home> resultInfo) {
                if(resultInfo.getCode() == 1){
                    mView.homeInfo(resultInfo);
                } else {
                    mView.loadError(resultInfo.getMessage());
                }
            }
        };
        model.subHome(subscriber);
    }

    public void subscribeNum(HashMap<String, String> params){
        model.subscribeNum(params);
        Subscriber<Subscribe> subscriber = new Subscriber<Subscribe>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Subscribe subscribe) {
                if(subscribe.getCode() == 1){
                    mView.subscribeNum(subscribe);
                } else {
                    mView.loadErrorSubscribe(subscribe.getMessage());
                }
            }
        };
        model.subSubscribe(subscriber);
    }

    public void advCount(int id){
        model.advCount(id);
        Subscriber<Result_comment> subscriber = new Subscriber<Result_comment>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result_comment result_comment) {
                if(result_comment.getCode() == 1){
                    mView.advCount(result_comment);
                } else {
                    mView.countError(result_comment.getMessage());
                }
            }
        };
        model.subAdv(subscriber);
    }

    public void popup(){
        model.popup();
        Subscriber<ResultInfo<MessageInfo>> subscriber = new Subscriber<ResultInfo<MessageInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<MessageInfo> resultInfo) {
                if(resultInfo.getCode() == 1){
                    mView.popup(resultInfo);
                } else {
                    mView.popupError(resultInfo.getMessage());
                }
            }
        };
        model.subPopup(subscriber);
    }
}
