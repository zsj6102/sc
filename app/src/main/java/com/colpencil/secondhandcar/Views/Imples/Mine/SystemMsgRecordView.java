package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.MessageInfo;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface SystemMsgRecordView extends ColpencilBaseView{

    void systemMsgRecord(Result<MessageInfo> result);

    void loadMore(Result<MessageInfo> result);

    void loadError(String message);
}
