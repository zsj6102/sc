package com.colpencil.secondhandcar.Views.Imples.Buy;

import com.colpencil.secondhandcar.Bean.Response.BrandList;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/4.
 */
public interface BrandView extends ColpencilBaseView{

    void brandList(ResultInfo<BrandList> result);

    void loadError(String message);
}
