package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.BillDetails;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/23.
 */
public interface IBillDetailsModel {

    void billDetails(HashMap<String, String> params);

    void sub(Subscriber<ResultInfo<BillDetails>> subscriber);
}
