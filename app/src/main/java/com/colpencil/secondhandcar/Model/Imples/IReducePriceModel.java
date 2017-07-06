package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.MineRemind;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface IReducePriceModel {

    void reducePrice(HashMap<String, String> params);

    void sub(Subscriber<ResultInfo<MineRemind>> subscriber);

    void deleteReduce(HashMap<String, String> params);

    void subDelete(Subscriber<Result_comment> subscriber);

    void cleanRecord(HashMap<String, String> params);

    void subClean(Subscriber<Result_comment> subscriber);
}
