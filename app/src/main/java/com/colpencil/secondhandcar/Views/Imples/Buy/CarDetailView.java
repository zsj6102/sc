package com.colpencil.secondhandcar.Views.Imples.Buy;

import com.colpencil.secondhandcar.Bean.Response.Browse;
import com.colpencil.secondhandcar.Bean.Response.CarInfo;
import com.colpencil.secondhandcar.Bean.Response.Collection;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/5.
 */
public interface CarDetailView extends ColpencilBaseView {

    void carInfo(ResultInfo<CarInfo> resultInfo);

    void loadError(ResultInfo<CarInfo> resultInfo);

    void addBrowse(Browse browse);

    void addBrowseError(String message);

    void addCollection(Collection collection);

    void addCollectionError(Collection collection);

    void addReducePrice(Result_comment result_comment);

    void addReducePriceError(Result_comment result_comment);
}
