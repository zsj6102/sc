package com.colpencil.secondhandcar.Present.Buy;

import com.colpencil.secondhandcar.Bean.Response.PeriodBuyCar;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Buy.PeriodBuyModel;
import com.colpencil.secondhandcar.Model.Imples.IPeriodBuyModel;
import com.colpencil.secondhandcar.Views.Imples.Buy.PeriodBuyView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/24.
 */
public class PeriodBuyPresenter extends ColpencilPresenter<PeriodBuyView> {

    private IPeriodBuyModel periodBuyModel;

    public PeriodBuyPresenter(){
        periodBuyModel = new PeriodBuyModel();
    }

    public void goInstallment(HashMap<String, String> params){
        periodBuyModel.goInstallment(params);
        Subscriber<ResultInfo<PeriodBuyCar>> subscriber = new Subscriber<ResultInfo<PeriodBuyCar>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<PeriodBuyCar> resultInfo) {
                if(resultInfo.getCode() == 1){
                    mView.goInstallment(resultInfo);
                } else {
                    mView.loadError(resultInfo.getMessage());
                }
            }
        };
        periodBuyModel.sub(subscriber);
    }

    public void create(HashMap<String, String> params){
        periodBuyModel.create(params);
        Subscriber<Result_comment> subscriber = new Subscriber<Result_comment>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result_comment result_comment) {
                if(result_comment .getCode() == 1){
                    mView.createSuccess(result_comment);
                } else {
                    mView.createFailue(result_comment.getMessage());
                }
            }
        };
        periodBuyModel.subCreate(subscriber);
    }
}
