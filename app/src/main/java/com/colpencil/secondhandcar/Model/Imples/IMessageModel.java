package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Message;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface IMessageModel {

    void getMessage(HashMap<String, String> params);

    void sub(Subscriber<ResultInfo<Message>> subscriber);
}
