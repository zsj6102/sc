package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.MineRemind;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface ReducePriceView extends ColpencilBaseView {

    void reducePriceRecord(ResultInfo<MineRemind> result);

    void loadError(ResultInfo<MineRemind> remindResultInfo);

    void deleteReduce(Result_comment result_comment);

    void deleteError(String message);

    void cleanRecord(Result_comment result_comment);

    void cleanError(String message);
}
