package com.colpencil.secondhandcar.Model.Imples;

import com.colpencil.secondhandcar.Bean.Response.FriendRecommend;
import com.colpencil.secondhandcar.Bean.Response.Home;
import com.colpencil.secondhandcar.Bean.Response.MessageInfo;
import com.colpencil.secondhandcar.Bean.Response.Province;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.Subscribe;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/3.
 */
public interface IRecommendModel {

    void getRecommend(int pageNo, int pageSize, int cityId);

    void sub(Subscriber<Result<FriendRecommend>> resultSubscriber);

    void homeInfo();

    void subHome(Subscriber<ResultInfo<Home>> subscriber);

    void subscribeNum(HashMap<String, String> params);

    void subSubscribe(Subscriber<Subscribe> subscribe);

    void advCount(int id);

    void subAdv(Subscriber<Result_comment> subscriber);

    void popup();

    void subPopup(Subscriber<ResultInfo<MessageInfo>> subscriber);

}
