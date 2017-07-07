package com.colpencil.secondhandcar.Views.Imples.Home;

import com.colpencil.secondhandcar.Bean.Response.FriendRecommend;
import com.colpencil.secondhandcar.Bean.Response.Home;
import com.colpencil.secondhandcar.Bean.Response.MessageCount;
import com.colpencil.secondhandcar.Bean.Response.MessageInfo;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.Subscribe;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/3.
 */
public interface RecommendView extends ColpencilBaseView {

    void refresh(Result<FriendRecommend> result);

    void loadError(String message);

    void loadMsgCount(Result<MessageCount> result);


    void loadMore(Result<FriendRecommend> result);

    void homeInfo(ResultInfo<Home> resultInfo);

    void subscribeNum(Subscribe subscribe);

    void loadErrorSubscribe(String message);

    void advCount(Result_comment result_comment);

    void countError(String message);

    void popup(ResultInfo<MessageInfo> resultInfo);

    void popupError(String message);
}
