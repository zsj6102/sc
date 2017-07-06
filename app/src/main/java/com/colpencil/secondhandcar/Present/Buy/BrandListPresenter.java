package com.colpencil.secondhandcar.Present.Buy;

import android.util.Log;

import com.colpencil.secondhandcar.Bean.Response.BrandList;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Buy.BrandModel;
import com.colpencil.secondhandcar.Model.Imples.IBrandModel;
import com.colpencil.secondhandcar.Views.Imples.Buy.BrandView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/4.
 */
public class BrandListPresenter extends ColpencilPresenter<BrandView> {

    private IBrandModel brandModel;

    public BrandListPresenter(){
        brandModel = new BrandModel();
    }

    public void brandList(HashMap<String, String> params){
        brandModel.brandList(params);
        Subscriber<ResultInfo<BrandList>> subscriber = new Subscriber<ResultInfo<BrandList>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("pretty",e.getMessage());
            }

            @Override
            public void onNext(ResultInfo<BrandList> result) {
                if(result.getCode() == 1){
                    mView.brandList(result);
                } else {
                    mView.loadError(result.getMessage());
                }
            }
        };
        brandModel.sub(subscriber);
    }
}
