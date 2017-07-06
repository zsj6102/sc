package com.colpencil.secondhandcar.Present.Home;

import com.colpencil.secondhandcar.Bean.Response.PeriodCar;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Home.PeriodCarModel;
import com.colpencil.secondhandcar.Model.Imples.IPeriodCarModel;
import com.colpencil.secondhandcar.Views.Imples.Home.InstallmentCarView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/12.
 */
public class PeriodCarPresenter extends ColpencilPresenter<InstallmentCarView> {

    private IPeriodCarModel carModel;

    public PeriodCarPresenter(){
        carModel = new PeriodCarModel();
    }

    public void installmentCar(int goodsId){
        carModel.installmentCar(goodsId);
        Subscriber<ResultInfo<PeriodCar>> subscriber = new Subscriber<ResultInfo<PeriodCar>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<PeriodCar> resultInfo) {
                if(resultInfo.getCode() == 1){
                    mView.installmentCar(resultInfo);
                } else {
                    mView.loadError(resultInfo);
                }
            }
        };
        carModel.sub(subscriber);
    }

    public void advCount(int id){
        carModel.advCount(id);
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
        carModel.subAdv(subscriber);
    }
}
