package com.colpencil.secondhandcar.Present.Buy;

import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Model.Buy.AddOrderModel;
import com.colpencil.secondhandcar.Model.Imples.IAddOrderModel;
import com.colpencil.secondhandcar.Views.Imples.Buy.AddOrderView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/23.
 */
public class AddOrderPresenter extends ColpencilPresenter<AddOrderView> {

    private IAddOrderModel orderModel;

    public AddOrderPresenter(){
        orderModel = new AddOrderModel();
    }

    public void addOrder(HashMap<String, String> params){
        orderModel.addOrder(params);
        Subscriber<Result_comment> subscriber = new Subscriber<Result_comment>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
              mView.netfail(e.getMessage());
            }

            @Override
            public void onNext(Result_comment result_comment) {
                if(result_comment.getCode() == 1){
                    mView.addSuccess(result_comment);
                } else {
                    mView.addFaile(result_comment);
                }
            }
        };
        orderModel.sub(subscriber);
    }
}
