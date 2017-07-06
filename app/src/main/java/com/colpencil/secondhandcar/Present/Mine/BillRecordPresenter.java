package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.BillRecord;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Imples.IBillRecordModel;
import com.colpencil.secondhandcar.Model.Mine.BillRecordModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.BillRecordView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/23.
 */
public class BillRecordPresenter extends ColpencilPresenter<BillRecordView> {

    private IBillRecordModel recordModel;

    public BillRecordPresenter(){
        recordModel = new BillRecordModel();
    }

    public void billRecord(final HashMap<String, String> params){
        recordModel.billRecord(params);
        Subscriber<Result<BillRecord>> subscriber = new Subscriber<Result<BillRecord>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<BillRecord> recordResult) {
                if(recordResult.getCode() == 1){
                    if(params.get("pageNo").equals("1")){
                        mView.billRecord(recordResult);
                    } else {
                        mView.loadMore(recordResult);
                    }
                } else {
                    mView.loadError(recordResult);
                }
            }
        };
        recordModel.sub(subscriber);
    }
}
