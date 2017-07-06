package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.BillDetails;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/23.
 */
public interface BillDetailsView extends ColpencilBaseView {

    void billDetails(ResultInfo<BillDetails> resultInfo);

    void loadError(String message);
}
