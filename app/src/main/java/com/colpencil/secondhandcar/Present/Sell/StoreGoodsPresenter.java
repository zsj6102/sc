package com.colpencil.secondhandcar.Present.Sell;

import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.SellCarRecord;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Imples.IStoreGoodsModel;
import com.colpencil.secondhandcar.Model.Sell.StoreGoodsModel;
import com.colpencil.secondhandcar.Views.Imples.Sell.StoreGoodsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/22.
 */
public class StoreGoodsPresenter extends ColpencilPresenter<StoreGoodsView> {

    private IStoreGoodsModel goodsModel;

    public StoreGoodsPresenter(){
        goodsModel = new StoreGoodsModel();
    }

    public void storeGoods(final HashMap<String, String> params){
        goodsModel.storeGoods(params);
        Subscriber<Result<SellCarRecord>> subscriber = new Subscriber<Result<SellCarRecord>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<SellCarRecord> recordResult) {
                if(recordResult.getCode() == 1){
                    if(params.get("pageNo").equals("1")){
                        mView.storeGoods(recordResult);
                    } else {
                        mView.loadMore(recordResult);
                    }
                } else {
                    mView.loadError(recordResult);
                }
            }
        };
        goodsModel.sub(subscriber);
    }

    public void shelves(HashMap<String, String> params){
        goodsModel.shelves(params);
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
                    mView.success(result_comment);
                } else {
                    mView.faile(result_comment.getMessage());
                }
            }
        };
        goodsModel.subShelves(subscriber);
    }
}
