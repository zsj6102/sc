package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Imples.IMsgContentModel;
import com.colpencil.secondhandcar.Model.Mine.MsgContentModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.MsgContentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/15.
 */
public class MsgContentPresenter extends ColpencilPresenter<MsgContentView> {

    private IMsgContentModel msgContentModel;

    public MsgContentPresenter(){
        msgContentModel = new MsgContentModel();
    }

    public void msgContent(int id){
        msgContentModel.msgCntent(id);
        Subscriber<ResultInfo<Url>> subscriber = new Subscriber<ResultInfo<Url>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<Url> resultInfo) {
                if(resultInfo.getCode() == 1){
                    mView.msgContent(resultInfo);
                } else {
                    mView.loadError(resultInfo.getMessage());
                }
            }
        };
        msgContentModel.sub(subscriber);
    }
}
