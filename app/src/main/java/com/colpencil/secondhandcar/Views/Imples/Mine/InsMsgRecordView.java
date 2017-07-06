package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.MsgPeriod;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/15.
 */
public interface InsMsgRecordView extends ColpencilBaseView {

    void insMsgRecord(Result<MsgPeriod> result);

    void loadMore(Result<MsgPeriod> result);

    void loadError(String message);
}
