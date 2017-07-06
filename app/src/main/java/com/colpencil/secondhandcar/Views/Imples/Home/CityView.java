package com.colpencil.secondhandcar.Views.Imples.Home;

import com.colpencil.secondhandcar.Bean.City;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/3.
 */
public interface CityView extends ColpencilBaseView {

    void getCity(Result<City> result);

    void loadError();
}
