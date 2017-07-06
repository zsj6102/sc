package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.GoodsInfo;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/22.
 */
public interface IGoodsInfoModel {

    void getGoodsInf(HashMap<String, String> params);

    void sub(Subscriber<ResultInfo<GoodsInfo>> subscriber);
}
