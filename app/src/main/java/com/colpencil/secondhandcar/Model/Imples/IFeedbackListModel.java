package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Feedback;
import com.colpencil.secondhandcar.Bean.Result;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/4.
 */
public interface IFeedbackListModel {

    void feedbackList(int memberId, String token);

    void sub(Subscriber<Result<Feedback>> subscriber);
}
