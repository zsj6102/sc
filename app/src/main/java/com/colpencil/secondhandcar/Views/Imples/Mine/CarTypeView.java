package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.CarResult;
import com.colpencil.secondhandcar.Bean.Response.CarResultRes;
import com.colpencil.secondhandcar.Bean.Response.CarType;
import com.colpencil.secondhandcar.Bean.Response.MineSub;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/11.
 */
public interface CarTypeView extends ColpencilBaseView {

    void carType(Result<CarType> resultInfo);

    void typeError(String message);

    void addSunscribe(MineSub mineSub);

    void addError(String message);

    void searchResult(ResultInfo<CarResultRes> result);

    void resultError(String message);
}
