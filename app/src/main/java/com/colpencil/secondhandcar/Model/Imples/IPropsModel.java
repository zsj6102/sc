package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Props;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface IPropsModel {

    void getProps(int typeId);

    void sub(Subscriber<ResultInfo<Props>> subscriber);
}
