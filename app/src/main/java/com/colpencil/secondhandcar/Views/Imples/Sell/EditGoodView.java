package com.colpencil.secondhandcar.Views.Imples.Sell;

import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/6/1.
 */
public interface EditGoodView extends ColpencilBaseView {

    void editGood(ResultInfo<Url> resultInfo);

    void loadError(String message);
}
