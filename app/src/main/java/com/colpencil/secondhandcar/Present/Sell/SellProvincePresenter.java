package com.colpencil.secondhandcar.Present.Sell;

import com.colpencil.secondhandcar.Bean.Response.Province;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Imples.ISellProvinceModel;
import com.colpencil.secondhandcar.Model.Sell.SellProvinceModel;
import com.colpencil.secondhandcar.Views.Imples.Sell.SellProvinceView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/19.
 */
public class SellProvincePresenter extends ColpencilPresenter<SellProvinceView> {

    private ISellProvinceModel provinceModel;

    public SellProvincePresenter(){
        provinceModel = new SellProvinceModel();
    }

    public void getProvinceId(String name){
        provinceModel.getProvinceId(name);
        Subscriber<ResultInfo<Province>> subscriber = new Subscriber<ResultInfo<Province>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<Province> resultInfo) {
                if(resultInfo.getCode() == 1){
                    mView.getProvinceId(resultInfo);
                } else {
                    mView.getError(resultInfo.getMessage());
                }
            }
        };
        provinceModel.sub(subscriber);
    }
}
