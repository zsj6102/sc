package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.CarResult;
import com.colpencil.secondhandcar.Bean.Response.CarResultRes;
import com.colpencil.secondhandcar.Bean.Response.CarType;
import com.colpencil.secondhandcar.Bean.Response.MineSub;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Imples.ICarTypeModel;
import com.colpencil.secondhandcar.Model.Mine.CarTypeModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.CarTypeView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/11.
 */
public class AddsubscribePresenter extends ColpencilPresenter<CarTypeView> {

    private ICarTypeModel carTypeModel;

    public AddsubscribePresenter(){
        carTypeModel = new CarTypeModel();
    }

    public void carType(){
        carTypeModel.carType();
        Subscriber<Result<CarType>> subscriber = new Subscriber<Result<CarType>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<CarType> resultInfo) {
                if(resultInfo.getCode() == 1){
                    mView.carType(resultInfo);
                } else {
                    mView.typeError(resultInfo.getMessage());
                }
            }
        };
        carTypeModel.sub(subscriber);
    }

    public void addSubscribe(HashMap<String, String> params){
        carTypeModel.addSubscribe(params);
        Subscriber<MineSub> subSubscriber = new Subscriber<MineSub>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MineSub mineSub) {
                if(mineSub.getCode() == 1){
                    mView.addSunscribe(mineSub);
                } else {
                    mView.addError(mineSub.getMessage());
                }
            }
        };
        carTypeModel.subAdd(subSubscriber);
    }

    public void carResult(HashMap<String, String> params){
        carTypeModel.searchResult(params);
        Subscriber<ResultInfo<CarResultRes>> subscriber = new Subscriber<ResultInfo<CarResultRes>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<CarResultRes> resultResult) {
                if(resultResult.getCode() == 1){
                    mView.searchResult(resultResult);
                } else {
                    mView.resultError(resultResult.getMessage());
                }
            }
        };
        carTypeModel.subResult(subscriber);
    }
}
