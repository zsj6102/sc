package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/15.
 */
public interface MsgContentView extends ColpencilBaseView{

    void msgContent(ResultInfo<Url> resultInfo);

    void loadError(String message);
}
