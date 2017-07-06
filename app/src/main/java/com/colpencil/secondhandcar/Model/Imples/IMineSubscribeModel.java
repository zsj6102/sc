package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.MineSub;
import com.colpencil.secondhandcar.Bean.Response.MineSubscribe;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Result;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/11.
 */
public interface IMineSubscribeModel {

    void mineSubscribe(HashMap<String, String> params);

    void sub(Subscriber<Result<MineSubscribe>> subscriber);

    void deleteSubscribe(HashMap<String, String> params);

    void subDelete(Subscriber<MineSub> subSubscriber);

    void cleanSubscribe(HashMap<String, String> params);

    void subClean(Subscriber<Result_comment> subscriber);
}
