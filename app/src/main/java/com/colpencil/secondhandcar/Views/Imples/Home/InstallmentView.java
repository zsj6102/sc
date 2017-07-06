package com.colpencil.secondhandcar.Views.Imples.Home;

import com.colpencil.secondhandcar.Bean.Response.Installment;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/10.
 */
public interface InstallmentView extends ColpencilBaseView {

    void installment(Result<Installment> result);

    void loadError(String message);
}
