package com.colpencil.secondhandcar.Present.Buy;

import com.colpencil.secondhandcar.Bean.Response.CarResult;
import com.colpencil.secondhandcar.Bean.Response.CarResultRes;
import com.colpencil.secondhandcar.Bean.Response.Collection;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Buy.CarResultModel;
import com.colpencil.secondhandcar.Model.Imples.ICarResultModel;
import com.colpencil.secondhandcar.Views.Imples.Buy.CarResultView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/16.
 */
public class CarResultPresenter extends ColpencilPresenter<CarResultView> {

    private ICarResultModel carResultModel;

    public CarResultPresenter(){
        carResultModel = new CarResultModel();
    }

    public void carResult(final HashMap<String, String> params){
        carResultModel.carResult(params);
        Subscriber<ResultInfo<CarResultRes>> subscriber = new Subscriber<ResultInfo<CarResultRes>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e(e.getMessage());
            }

            @Override
            public void onNext(ResultInfo<CarResultRes> resultResult) {
                if(resultResult.getCode() == 1 || resultResult.getCode() == 2){
                    if(params.get("pageNo").equals("1")){
                        mView.carResult(resultResult);
                    } else {
                        mView.loadMore(resultResult);
                    }
                } else {
                    mView.loadError(resultResult.getMessage());
                }
            }
        };
        carResultModel.sub(subscriber);
    }

    public void collection(HashMap<String, String> params){
        carResultModel.collection(params);
        Subscriber<Collection> subscriber = new Subscriber<Collection>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Collection collection) {
                if(collection.getCode() == 1 || collection.getCode() == 2){
                    mView.collection(collection);
                } else {
                    mView.collectionError(collection.getMessage());
                }
            }
        };
        carResultModel.subCollection(subscriber);
    }
}
