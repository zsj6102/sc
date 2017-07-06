package com.colpencil.secondhandcar.Views.Imples.Sell;

import com.colpencil.secondhandcar.Bean.Response.Province;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/19.
 */
public interface SellProvinceView extends ColpencilBaseView {

    void getProvinceId(ResultInfo<Province> resultInfo);

    void getError(String message);
}
