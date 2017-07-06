package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.PeriodBuyCar;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/24.
 */
public interface IPeriodBuyModel {

    void goInstallment(HashMap<String, String> params);

    void sub(Subscriber<ResultInfo<PeriodBuyCar>> subscriber);

    void create(HashMap<String, String> params);

    void subCreate(Subscriber<Result_comment> subscriber);
}
