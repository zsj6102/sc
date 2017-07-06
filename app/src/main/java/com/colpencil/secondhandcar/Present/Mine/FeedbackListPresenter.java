package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.Feedback;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Imples.IFeedbackListModel;
import com.colpencil.secondhandcar.Model.Mine.FeedbackListModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.FeedbackListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/4.
 */
public class FeedbackListPresenter extends ColpencilPresenter<FeedbackListView> {

    private IFeedbackListModel feedbackListModel;

    public FeedbackListPresenter(){
        feedbackListModel = new FeedbackListModel();
    }

    public void feedbackList(int memberId, String token){
        feedbackListModel.feedbackList(memberId, token);
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
                    mView.feedbackList(result);
                } else {
                    mView.loadError(result);
                }
            }
        };
        feedbackListModel.sub(subscriber);
    }
}
