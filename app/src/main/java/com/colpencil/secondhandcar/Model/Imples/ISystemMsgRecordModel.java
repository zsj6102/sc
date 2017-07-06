package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.MessageInfo;
import com.colpencil.secondhandcar.Bean.Result;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface ISystemMsgRecordModel {

    void systemMsgRecord(HashMap<String, String> params);

    void sub(Subscriber<Result<MessageInfo>> subscriber);
}
