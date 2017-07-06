package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.Message;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface MessageView extends ColpencilBaseView {

    void getMessage(ResultInfo<Message> resultInfo);

    void loadError(ResultInfo<Message> resultInfo);
}
