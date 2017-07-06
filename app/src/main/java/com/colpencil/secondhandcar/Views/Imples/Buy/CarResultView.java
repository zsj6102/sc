package com.colpencil.secondhandcar.Views.Imples.Buy;

import com.colpencil.secondhandcar.Bean.Response.CarResult;
import com.colpencil.secondhandcar.Bean.Response.CarResultRes;
import com.colpencil.secondhandcar.Bean.Response.Collection;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/16.
 */
public interface CarResultView extends ColpencilBaseView {

    void carResult(ResultInfo<CarResultRes> result);

    void loadMore(ResultInfo<CarResultRes> result);

    void loadError(String message);

    void collection(Collection collection);

    void collectionError(String message);
}
