package com.colpencil.secondhandcar.Views.Imples.Sell;

import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/17.
 */
public interface EditGoodsView extends ColpencilBaseView {

    void editSuccess(Result_comment result);

    void editFaile(String message);
}
