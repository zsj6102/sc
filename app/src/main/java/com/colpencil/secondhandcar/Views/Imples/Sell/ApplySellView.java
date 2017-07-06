package com.colpencil.secondhandcar.Views.Imples.Sell;

import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.SellAdv;
import com.colpencil.secondhandcar.Bean.Response.SellApply;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/4.
 */
public interface ApplySellView extends ColpencilBaseView {

    void applySell(ResultInfo<SellApply> resultInfo);

    void loadError(String message);

    void sellData(Result<SellAdv> resultInfo);

    void sellError(Result<SellAdv> result);

    void advCount(Result_comment result_comment);

    void countError(String message);
}
