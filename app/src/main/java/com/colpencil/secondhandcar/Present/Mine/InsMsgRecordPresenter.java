package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.MsgPeriod;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Imples.IInsMsgRecordModel;
import com.colpencil.secondhandcar.Model.Mine.InsMsgRecordModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.InsMsgRecordView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/15.
 */
public class InsMsgRecordPresenter extends ColpencilPresenter<InsMsgRecordView> {

    private IInsMsgRecordModel insMsgRecordModel;

    public InsMsgRecordPresenter(){
        insMsgRecordModel = new InsMsgRecordModel();
    }

    public void insMsgRecord(final HashMap<String, String> params){
        insMsgRecordModel.insMsgRecord(params);
        Subscriber<Result<MsgPeriod>> subscriber = new Subscriber<Result<MsgPeriod>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<MsgPeriod> result) {
                if(result.getCode() == 1){
                    if(params.get("pageNo").equals("1")){
                        mView.insMsgRecord(result);
                    } else {
                        mView.loadMore(result);
                    }
                } else {
                    mView.loadError(result.getMessage());
                }
            }
        };
        insMsgRecordModel.sub(subscriber);
    }
}
