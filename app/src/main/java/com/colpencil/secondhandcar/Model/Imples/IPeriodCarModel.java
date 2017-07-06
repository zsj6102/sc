package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.PeriodCar;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface IPeriodCarModel {

    void installmentCar(int goodsId);

    void sub(Subscriber<ResultInfo<PeriodCar>> subscriber);

    void advCount(int id);

    void subAdv(Subscriber<Result_comment> subscriber);
}
