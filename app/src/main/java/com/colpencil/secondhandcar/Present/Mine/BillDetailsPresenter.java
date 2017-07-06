package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.BillDetails;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Imples.IBillDetailsModel;
import com.colpencil.secondhandcar.Model.Mine.BillDetailsModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.BillDetailsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/23.
 */
public class BillDetailsPresenter extends ColpencilPresenter<BillDetailsView> {

    private IBillDetailsModel detailsModel;

    public BillDetailsPresenter(){
        detailsModel = new BillDetailsModel();
    }

    public void billDetails(HashMap<String, String> params){
        detailsModel.billDetails(params);
        Subscriber<ResultInfo<BillDetails>> subscriber = new Subscriber<ResultInfo<BillDetails>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<BillDetails> resultInfo) {
                if(resultInfo.getCode() == 1){
                    mView.billDetails(resultInfo);
                } else {
                    mView.loadError(resultInfo.getMessage());
                }
            }
        };
        detailsModel.sub(subscriber);
    }
}
