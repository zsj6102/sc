package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Result_comment;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/17.
 */
public interface IEditGoodsModel {

    void editGoods(HashMap<String, RequestBody> params);

    void sub(Subscriber<Result_comment> subscriber);
}
