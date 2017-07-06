package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.MineDepreciateNotice;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/15.
 */
public interface ReduceNoticeRecordView extends ColpencilBaseView {

    void reduceNoticeRecord(Result<MineDepreciateNotice> result);

    void loadMore(Result<MineDepreciateNotice> result);

    void loadError(String message);
}
