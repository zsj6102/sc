package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.MessageInfo;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Imples.ISystemMsgRecordModel;
import com.colpencil.secondhandcar.Model.Mine.SystemMsgRecordModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.SystemMsgRecordView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/12.
 */
public class SystemMsgRecordPresenter extends ColpencilPresenter<SystemMsgRecordView> {

    private ISystemMsgRecordModel systemMsgRecordModel;

    public SystemMsgRecordPresenter(){
        systemMsgRecordModel = new SystemMsgRecordModel();
    }

    public void systemMsgRecord(final HashMap<String, String> params){
        systemMsgRecordModel.systemMsgRecord(params);
        Subscriber<Result<MessageInfo>> subscriber = new Subscriber<Result<MessageInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<MessageInfo> result) {
                if(result.getCode() == 1){
                    if(params.get("pageNo").equals("1")){
                        mView.systemMsgRecord(result);
                    } else {
                        mView.loadMore(result);
                    }
                } else {
                    mView.loadError(result.getMessage());
                }
            }
        };
        systemMsgRecordModel.sub(subscriber);
    }
}
