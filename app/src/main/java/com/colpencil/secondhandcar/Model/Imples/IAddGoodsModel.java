package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Installment;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Result;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/17.
 */
public interface IAddGoodsModel {

    void addGoods(HashMap<String, RequestBody> params);

    void sub(Subscriber<Result_comment> subscriber);

    void getPeriodNum();

    void subPeriod(Subscriber<Result<Installment>> subscriber);
}
