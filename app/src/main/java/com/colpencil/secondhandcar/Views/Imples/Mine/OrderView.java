package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.Order;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/23.
 */
public interface OrderView extends ColpencilBaseView {

    void orderRecord(Result<Order> result);

    void loadMore(Result<Order> result);

    void loadError(Result<Order> result);
}
