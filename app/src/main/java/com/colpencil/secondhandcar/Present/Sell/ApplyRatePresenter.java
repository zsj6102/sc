package com.colpencil.secondhandcar.Present.Sell;

import com.colpencil.secondhandcar.Bean.Response.ApplyRate;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Imples.IApplyRateModel;
import com.colpencil.secondhandcar.Model.Sell.ApplyRateModel;
import com.colpencil.secondhandcar.Views.Imples.Sell.ApplyRateView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/16.
 */
public class ApplyRatePresenter extends ColpencilPresenter<ApplyRateView> {

    private IApplyRateModel applyRateModel;

    public ApplyRatePresenter(){
        applyRateModel = new ApplyRateModel();
    }

    public void applyRate(HashMap<String, String> params){
        applyRateModel.applyRate(params);
        Subscriber<ResultInfo<ApplyRate>> subscriber = new Subscriber<ResultInfo<ApplyRate>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<ApplyRate> resultInfo) {
                if(resultInfo.getCode() == 1){
                    mView.applyRate(resultInfo);
                } else {
                    mView.loadError(resultInfo);
                }
            }
        };
        applyRateModel.sub(subscriber);
    }
}
