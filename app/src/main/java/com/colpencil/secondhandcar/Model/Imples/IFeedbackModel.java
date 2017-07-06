package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Feedback;
import com.colpencil.secondhandcar.Bean.Result;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/4.
 */
public interface IFeedbackModel {

    void commit(int memberId, String phone, String content, String token);

    void sub(Subscriber<Result<Feedback>> subscriber);
}
