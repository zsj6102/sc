package com.colpencil.secondhandcar.Views.Imples.Buy;

import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/23.
 */
public interface AddOrderView extends ColpencilBaseView {

    void addSuccess(Result_comment result_comment);

    void addFaile(Result_comment result_comment);

    void netfail(String message);
}
