package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Installment;
import com.colpencil.secondhandcar.Bean.Result;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/10.
 */
public interface IPeriodModel {

    void installment();

    void sub(Subscriber<Result<Installment>> subscriber);
}
