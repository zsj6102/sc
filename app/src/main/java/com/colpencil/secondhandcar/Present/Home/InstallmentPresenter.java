package com.colpencil.secondhandcar.Present.Home;

import com.colpencil.secondhandcar.Bean.Response.Installment;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Home.PeriodModel;
import com.colpencil.secondhandcar.Model.Imples.IPeriodModel;
import com.colpencil.secondhandcar.Views.Imples.Home.InstallmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/10.
 */
public class InstallmentPresenter extends ColpencilPresenter<InstallmentView> {

    private IPeriodModel periodModel;

    public InstallmentPresenter(){
        periodModel = new PeriodModel();
    }

    public void instanllment(){
        periodModel.installment();
        Subscriber<Result<Installment>> subscriber = new Subscriber<Result<Installment>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<Installment> result) {
                if(result.getCode() == 1){
                    mView.installment(result);
                } else {
                    mView.loadError(result.getMessage());
                }
            }
        };
        periodModel.sub(subscriber);
    }
}
