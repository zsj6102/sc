package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Logout;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/5.
 */
public interface ILogoutModel {

    void logout(int memberId, String token);

    void sub(Subscriber<Logout> subscriber);
}
