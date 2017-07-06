package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.Repayment;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/23.
 */
public interface RepaymentView extends ColpencilBaseView{

    void repayment(Result<Repayment> result);

    void loadMore(Result<Repayment> result);

    void loadError(Result<Repayment> result);
}
