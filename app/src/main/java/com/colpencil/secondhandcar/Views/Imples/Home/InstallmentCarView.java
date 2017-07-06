package com.colpencil.secondhandcar.Views.Imples.Home;

import com.colpencil.secondhandcar.Bean.Response.PeriodCar;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface InstallmentCarView extends ColpencilBaseView {

    void installmentCar(ResultInfo<PeriodCar> resultInfo);

    void loadError(ResultInfo<PeriodCar> resultInfo);

    void advCount(Result_comment result_comment);

    void countError(String message);
}
