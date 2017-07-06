package com.colpencil.secondhandcar.Present.Sell;

import com.colpencil.secondhandcar.Bean.Response.Installment;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Imples.IAddGoodsModel;
import com.colpencil.secondhandcar.Model.Sell.AddGoodsModel;
import com.colpencil.secondhandcar.Views.Imples.Sell.AddGoodsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/17.
 */
public class AddGoodsPresenter extends ColpencilPresenter<AddGoodsView> {

    private IAddGoodsModel goodsModel;

    public AddGoodsPresenter(){
        goodsModel = new AddGoodsModel();
    }

    public void addGoods(HashMap<String, RequestBody> params){
        goodsModel.addGoods(params);
        Subscriber<Result_comment> subscriber = new Subscriber<Result_comment>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result_comment result_comment) {
                if(result_comment.getCode() == 1){
                    mView.addSuccess(result_comment);
                } else {
                    mView.addFaile(result_comment.getMessage());
                }
            }
        };
        goodsModel.sub(subscriber);
    }

    public void getPeriodNum(){
        goodsModel.getPeriodNum();
        Subscriber<Result<Installment>> subscriber = new Subscriber<Result<Installment>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<Installment> result) {
                if(result.getCode() == 1){
                    mView.getPeriodNum(result);
                } else {
                    mView.getFaile(result.getMessage());
                }
            }
        };
        goodsModel.subPeriod(subscriber);
    }
}
