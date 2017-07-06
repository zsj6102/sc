package com.colpencil.secondhandcar.Views.Imples.Buy;

import com.colpencil.secondhandcar.Bean.Response.PeriodBuyCar;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/24.
 */
public interface PeriodBuyView extends ColpencilBaseView {

    void goInstallment(ResultInfo<PeriodBuyCar> resultInfo);

    void loadError(String message);

    void createSuccess(Result_comment result_comment);

    void createFailue(String message);
}
