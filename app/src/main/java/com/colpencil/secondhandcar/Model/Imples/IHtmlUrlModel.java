package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/27.
 */
public interface IHtmlUrlModel {

    void htmlUrl(HashMap<String, String> params);

    void sub(Subscriber<ResultInfo<Url>> subscriber);
}
