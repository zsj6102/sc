package com.colpencil.secondhandcar.Views.Imples.Sell;

import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/27.
 */
public interface HtmlUrlView extends ColpencilBaseView{

    void htmlUrl(ResultInfo<Url> resultInfo);

    void loadError(ResultInfo<Url> resultInfo);
}
