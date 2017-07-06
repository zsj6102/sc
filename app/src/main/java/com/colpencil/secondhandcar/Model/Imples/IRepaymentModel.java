package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Repayment;
import com.colpencil.secondhandcar.Bean.Result;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/23.
 */
public interface IRepaymentModel {

    void repayment(HashMap<String, String> params);

    void sub(Subscriber<Result<Repayment>> subscriber);
}
