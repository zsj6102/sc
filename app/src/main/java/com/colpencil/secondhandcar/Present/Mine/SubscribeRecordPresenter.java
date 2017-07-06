package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.SubscribeGood;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Imples.ISubscribeRecordModel;
import com.colpencil.secondhandcar.Model.Mine.SubscribeRecordModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.SubscribeRecordView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/11.
 */
public class SubscribeRecordPresenter extends ColpencilPresenter<SubscribeRecordView> {

    private ISubscribeRecordModel subscribeRecordModel;

    public SubscribeRecordPresenter(){
        subscribeRecordModel = new SubscribeRecordModel();
    }

    public void subscribeRecord(HashMap<String, String> params){
        subscribeRecordModel.subscribeRecord(params);
        Subscriber<Result<SubscribeGood>> subscriber = new Subscriber<Result<SubscribeGood>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<SubscribeGood> result) {
                if(result.getCode() == 1){
                    mView.subscribeRecord(result);
                } else {
                    mView.loadError(result.getMessage());
                }
            }
        };
        subscribeRecordModel.sub(subscriber);
    }
}
