package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.PersonInfo;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/26.
 */
public interface IPersonInfoModel {

    void personInfo(HashMap<String, String> params);

    void sub(Subscriber<ResultInfo<PersonInfo>> subscriber);

    void changeInfo(HashMap<String, String> params);

    void subChange(Subscriber<Result_comment> subscriber);
}
