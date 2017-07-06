package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Login;
import com.colpencil.secondhandcar.Bean.Response.SendCode;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/2.
 */
public interface ILoginModel {

    void login(String mobile, String code);

    void sub(Subscriber<ResultInfo<Login>> subscriber);

    void sendCode(String mobile);

    void subCode(Subscriber<SendCode> codeSubscriber);
}
