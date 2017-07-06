package com.colpencil.secondhandcar.Views.Imples.Sell;

import com.colpencil.secondhandcar.Bean.Response.CarType;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/18.
 */
public interface ReleaseTypeView extends ColpencilBaseView {

    void getType(Result<CarType> resultInfo);

    void getError(String message);

}
