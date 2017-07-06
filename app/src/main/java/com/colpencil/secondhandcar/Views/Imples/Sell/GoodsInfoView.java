package com.colpencil.secondhandcar.Views.Imples.Sell;

import com.colpencil.secondhandcar.Bean.Response.GoodsInfo;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/22.
 */
public interface GoodsInfoView extends ColpencilBaseView {

    void getGoodsInfo(ResultInfo<GoodsInfo> resultInfo);

    void loadError(String message);
}
