package com.colpencil.secondhandcar.Present.Sell;

import com.colpencil.secondhandcar.Bean.Response.CarType;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Imples.IReleaseTypeModel;
import com.colpencil.secondhandcar.Model.Sell.ReleaseTypeModel;
import com.colpencil.secondhandcar.Views.Imples.Sell.ReleaseTypeView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/18.
 */
public class ReleaseTypePresenter extends ColpencilPresenter<ReleaseTypeView> {

    private IReleaseTypeModel typeModel;

    public ReleaseTypePresenter(){
        typeModel = new ReleaseTypeModel();
    }

    public void getType(){
        typeModel.getType();
        Subscriber<Result<CarType>> subscriber = new Subscriber<Result<CarType>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<CarType> carTypeResult) {
                if(carTypeResult.getCode() == 1){
                    mView.getType(carTypeResult);
                } else {
                    mView.getError(carTypeResult.getMessage());
                }
            }
        };
        typeModel.sub(subscriber);
    }
}
