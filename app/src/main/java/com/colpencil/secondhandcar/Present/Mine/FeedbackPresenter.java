package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.Feedback;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Imples.IFeedbackModel;
import com.colpencil.secondhandcar.Model.Mine.FeedbackModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.FeedbackView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/4.
 */
public class FeedbackPresenter extends ColpencilPresenter<FeedbackView> {

    private IFeedbackModel feedbackModel;

    public FeedbackPresenter(){
        feedbackModel = new FeedbackModel();
    }

    public void commit(int memberId, String phone, String content, String token){
        feedbackModel.commit(memberId, phone, content, token);
        Subscriber<Result<Feedback>> subscriber = new Subscriber<Result<Feedback>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<Feedback> result) {
                if(result.getCode() == 1){
                    mView.commit(result);
                } else {
                    mView.loadError(result.getMessage());
                }
            }
        };
        feedbackModel.sub(subscriber);
    }
}
