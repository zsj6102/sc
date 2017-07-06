package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.SellAdv;
import com.colpencil.secondhandcar.Bean.Response.SellApply;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/4.
 */
public interface IApplySellModel {

    void applySell(HashMap<String, RequestBody> params);

    void sub(Subscriber<ResultInfo<SellApply>> subscriber);

    void sellData();

    void subSell(Subscriber<Result<SellAdv>> subscriber);

    void advCount(int id);

    void subAdv(Subscriber<Result_comment> subscriber);
}
