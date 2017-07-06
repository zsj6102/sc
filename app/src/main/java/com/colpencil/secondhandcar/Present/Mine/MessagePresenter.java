package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.Message;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Imples.IMessageModel;
import com.colpencil.secondhandcar.Model.Mine.MessageModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.MessageView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/12.
 */
public class MessagePresenter extends ColpencilPresenter<MessageView> {

    private IMessageModel messageModel;

    public MessagePresenter(){
        messageModel = new MessageModel();
    }

    public void getMessage(HashMap<String, String> params){
        messageModel.getMessage(params);
        Subscriber<ResultInfo<Message>> subscriber = new Subscriber<ResultInfo<Message>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<Message> resultInfo) {
                if(resultInfo.getCode() == 1){
                    mView.getMessage(resultInfo);
                } else {
                    mView.loadError(resultInfo);
                }
            }
        };
        messageModel.sub(subscriber);
    }
}
