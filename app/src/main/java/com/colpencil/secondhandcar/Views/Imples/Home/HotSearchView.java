package com.colpencil.secondhandcar.Views.Imples.Home;

import com.colpencil.secondhandcar.Bean.Response.HotSearch;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/5.
 */
public interface HotSearchView extends ColpencilBaseView {

    void hotBrand(Result<HotSearch> result);

    void loadError(String message);
}
