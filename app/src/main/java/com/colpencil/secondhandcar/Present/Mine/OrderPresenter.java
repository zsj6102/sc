package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.Order;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Imples.IOrderModel;
import com.colpencil.secondhandcar.Model.Mine.OrderModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.OrderView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/23.
 */
public class OrderPresenter extends ColpencilPresenter<OrderView> {

    private IOrderModel orderModel;

    public OrderPresenter(){
        orderModel = new OrderModel();
    }

    public void order(final HashMap<String, String> params){
        orderModel.orderRecord(params);
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
                        mView.orderRecord(result);
                    } else {
                        mView.loadMore(result);
                    }
                } else {
                    mView.loadError(result);
                }
            }
        };
        orderModel.sub(subscriber);
    }
}
