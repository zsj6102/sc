package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.SubscribeGood;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/11.
 */
public interface SubscribeRecordView extends ColpencilBaseView {

    void subscribeRecord(Result<SubscribeGood> result);

    void loadError(String message);
}
