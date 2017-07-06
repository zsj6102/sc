package com.colpencil.secondhandcar.Views.Imples.Buy;

import com.colpencil.secondhandcar.Bean.Response.BrandCar;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/9.
 */
public interface CarView extends ColpencilBaseView {

    void carList(Result<BrandCar> result);

    void loadError(String message);
}
