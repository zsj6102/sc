package com.colpencil.secondhandcar.Views.Imples.Sell;

import com.colpencil.secondhandcar.Bean.Response.Installment;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/17.
 */
public interface AddGoodsView extends ColpencilBaseView {

    void addSuccess(Result_comment result);

    void addFaile(String message);

    void getPeriodNum(Result<Installment> result);

    void getFaile(String message);
}
