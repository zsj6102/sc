package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Browse;
import com.colpencil.secondhandcar.Bean.Response.PersonalGroom;
import com.colpencil.secondhandcar.Bean.Result;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/10.
 */
public interface IBrowseModel {

    void browseRecord(HashMap<String, String> params);

    void sub(Subscriber<Result<PersonalGroom>> subscriber);

    void deleteBrowse(HashMap<String, String> params);

    void subDelete(Subscriber<Browse> browseSubscriber);

    void cleanBrowse(HashMap<String, String> params);

    void subClean(Subscriber<Browse> browseSubscriber);
}
