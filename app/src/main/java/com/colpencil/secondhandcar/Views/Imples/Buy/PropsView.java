package com.colpencil.secondhandcar.Views.Imples.Buy;

import com.colpencil.secondhandcar.Bean.Response.Props;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface PropsView extends ColpencilBaseView{

    void getProps(ResultInfo<Props> resultInfo);

    void propsError(String message);
}
