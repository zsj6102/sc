package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.MsgPeriod;
import com.colpencil.secondhandcar.Bean.Result;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/15.
 */
public interface IInsMsgRecordModel {

    void insMsgRecord(HashMap<String, String> params);

    void sub(Subscriber<Result<MsgPeriod>> subscriber);
}
