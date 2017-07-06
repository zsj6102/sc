package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.MineSub;
import com.colpencil.secondhandcar.Bean.Response.MineSubscribe;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/11.
 */
public interface MineSubscribeView extends ColpencilBaseView {

    void mineSubscribe(Result<MineSubscribe> resultInfo);

    void loadMore(Result<MineSubscribe> resultInfo);

    void loadError(Result<MineSubscribe> result);

    void deleteSubscribe(MineSub mineSub);

    void deleteError(String message);

    void cleanSubscribe(Result_comment result_comment);

    void cleanError(String message);
}
