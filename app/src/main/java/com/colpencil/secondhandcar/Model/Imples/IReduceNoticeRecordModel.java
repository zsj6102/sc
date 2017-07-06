package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.MineDepreciateNotice;
import com.colpencil.secondhandcar.Bean.Result;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/15.
 */
public interface IReduceNoticeRecordModel {

    void reduceNoticeRecord(HashMap<String, String> params);

    void sub(Subscriber<Result<MineDepreciateNotice>> subscriber);
}
