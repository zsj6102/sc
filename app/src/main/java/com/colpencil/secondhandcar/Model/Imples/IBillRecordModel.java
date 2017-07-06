package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.BillRecord;
import com.colpencil.secondhandcar.Bean.Result;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/23.
 */
public interface IBillRecordModel {

    void billRecord(HashMap<String, String> params);

    void sub(Subscriber<Result<BillRecord>> subscriber);
}
