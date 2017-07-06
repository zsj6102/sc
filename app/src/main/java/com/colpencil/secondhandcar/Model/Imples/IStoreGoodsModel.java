package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.SellCarRecord;
import com.colpencil.secondhandcar.Bean.Result;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/22.
 */
public interface IStoreGoodsModel {

    void storeGoods(HashMap<String, String> params);

    void sub(Subscriber<Result<SellCarRecord>> subscriber);

    void shelves(HashMap<String, String> params);

    void subShelves(Subscriber<Result_comment> subscriber);
}
