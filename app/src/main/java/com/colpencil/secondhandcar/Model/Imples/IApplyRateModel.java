package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.ApplyRate;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/16.
 */
public interface IApplyRateModel {

    void applyRate(HashMap<String, String> params);

    void sub(Subscriber<ResultInfo<ApplyRate>> subscriber);
}
