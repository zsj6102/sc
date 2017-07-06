package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Collection;
import com.colpencil.secondhandcar.Bean.Response.PersonalGroom;
import com.colpencil.secondhandcar.Bean.Result;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/10.
 */
public interface ICollectionModel {

    void collectionRecord(HashMap<String, String> params);

    void sub(Subscriber<Result<PersonalGroom>> subscriber);

    void deleteCollection(HashMap<String, String> params);

    void subDelete(Subscriber<Collection> subscriber);

    void cleanCollection(HashMap<String, String> params);

    void subClean(Subscriber<Collection> subscriber);
}
