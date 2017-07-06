package com.colpencil.secondhandcar.Present.Buy;

import com.colpencil.secondhandcar.Bean.Response.BrandCar;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Buy.CarModel;
import com.colpencil.secondhandcar.Model.Imples.ICarModel;
import com.colpencil.secondhandcar.Views.Imples.Buy.CarView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/9.
 */
public class CarPresenter extends ColpencilPresenter<CarView> {

    private ICarModel carModel;

    public CarPresenter(){
        carModel = new CarModel();
    }

    public void carList(HashMap<String, String> params){
        carModel.carList(params);
        Subscriber<Result<BrandCar>> subscriber = new Subscriber<Result<BrandCar>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<BrandCar> result) {
                if(result.getCode() == 1){
                    mView.carList(result);
                } else {
                    mView.loadError(result.getMessage());
                }
            }
        };
        carModel.sub(subscriber);
    }
}
