package com.colpencil.secondhandcar.Present.Home;

import com.colpencil.secondhandcar.Bean.City;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Home.GetCityModel;
import com.colpencil.secondhandcar.Model.Imples.ICityModel;
import com.colpencil.secondhandcar.Views.Imples.Home.CityView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/3.
 */
public class CityPresenter extends ColpencilPresenter<CityView> {

    private ICityModel cityModel;

    public CityPresenter(){
        cityModel = new GetCityModel();
    }

    public void getCity(){
        cityModel.getCity();
        Subscriber<Result<City>> subscriber = new Subscriber<Result<City>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<City> result) {
                if(result.getCode() == 1){
                    mView.getCity(result);
                } else {
                    mView.loadError();
                }
            }
        };
        cityModel.sub(subscriber);
    }
}
