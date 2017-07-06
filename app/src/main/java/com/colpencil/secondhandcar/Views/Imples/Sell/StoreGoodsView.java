package com.colpencil.secondhandcar.Views.Imples.Sell;

import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.SellCarRecord;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/22.
 */
public interface StoreGoodsView extends ColpencilBaseView {

    void storeGoods(Result<SellCarRecord> result);

    void loadMore(Result<SellCarRecord> result);

    void loadError(Result<SellCarRecord> result);

    void success(Result_comment result_comment);

    void faile(String message);
}
