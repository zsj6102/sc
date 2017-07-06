package com.colpencil.secondhandcar.Views.Imples.Sell;

import com.colpencil.secondhandcar.Bean.Response.ApplyRate;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/16.
 */
public interface ApplyRateView extends ColpencilBaseView {

    void applyRate(ResultInfo<ApplyRate> resultInfo);

    void loadError(ResultInfo<ApplyRate> resultInfo);
}
