package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.MineDepreciateNotice;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Imples.IReduceNoticeRecordModel;
import com.colpencil.secondhandcar.Model.Mine.ReduceNoticeRecordModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.ReduceNoticeRecordView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/15.
 */
public class ReduceNoticeRecordPresenter extends ColpencilPresenter<ReduceNoticeRecordView> {

    private IReduceNoticeRecordModel reduceNoticeRecordModel;

    public ReduceNoticeRecordPresenter(){
        reduceNoticeRecordModel = new ReduceNoticeRecordModel();
    }

    public void reduceNoticeRecord(final HashMap<String, String> params){
        reduceNoticeRecordModel.reduceNoticeRecord(params);
        Subscriber<Result<MineDepreciateNotice>> subscriber = new Subscriber<Result<MineDepreciateNotice>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<MineDepreciateNotice> result) {
                if(result.getCode() == 1){
                    if(params.get("pageNo").equals("1")){
                        mView.reduceNoticeRecord(result);
                    } else {
                        mView.loadMore(result);
                    }
                } else {
                    mView.loadError(result.getMessage());
                }
            }
        };
        reduceNoticeRecordModel.sub(subscriber);
    }
}
