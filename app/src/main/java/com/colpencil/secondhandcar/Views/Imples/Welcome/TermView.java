package com.colpencil.secondhandcar.Views.Imples.Welcome;

import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/16.
 */
public interface TermView extends ColpencilBaseView {

    void term(ResultInfo<Url> resultInfo);

    void loadError(String message);
}
