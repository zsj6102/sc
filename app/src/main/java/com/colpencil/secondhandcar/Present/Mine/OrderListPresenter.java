package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.Order;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Imples.IOrderListModel;
import com.colpencil.secondhandcar.Model.Mine.OrderListModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.OrderListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/23.
 */
public class OrderListPresenter extends ColpencilPresenter<OrderListView> {

    private IOrderListModel orderListModel;

    public OrderListPresenter(){
        orderListModel = new OrderListModel();
    }

    public void orderList(final HashMap<String, String> params){
        orderListModel.orderList(params);
        Subscriber<Result<Order>> subscriber = new Subscriber<Result<Order>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<Order> result) {
                if(result.getCode() == 1){
                    if(params.get("pageNo").equals("1")){
                        mView.order(result);
                    } else {
                        mView.loadMore(result);
                    }
                } else {
                    mView.loadError(result);
                }
            }
        };
        orderListModel.sub(subscriber);
    }
}
