package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.Repayment;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Imples.IRepaymentModel;
import com.colpencil.secondhandcar.Model.Mine.RepaymentModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.RepaymentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/23.
 */
public class RepaymentPresenter extends ColpencilPresenter<RepaymentView> {

    private IRepaymentModel repaymentModel;

    public RepaymentPresenter(){
        repaymentModel = new RepaymentModel();
    }

    public void repayment(final HashMap<String, String> params){
        repaymentModel.repayment(params);
        Subscriber<Result<Repayment>> subscriber = new Subscriber<Result<Repayment>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<Repayment> repaymentResult) {
                if(repaymentResult.getCode() == 1){
                    if(params.get("pageNo").equals("1")){
                        mView.repayment(repaymentResult);
                    } else {
                        mView.loadMore(repaymentResult);
                    }
                } else {
                    mView.loadError(repaymentResult);
                }
            }
        };
        repaymentModel.sub(subscriber);
    }
}
