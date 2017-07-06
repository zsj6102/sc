package com.colpencil.secondhandcar.Present.Sell;

import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.SellAdv;
import com.colpencil.secondhandcar.Bean.Response.SellApply;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Imples.IApplySellModel;
import com.colpencil.secondhandcar.Model.Sell.ApplySellModel;
import com.colpencil.secondhandcar.Views.Imples.Sell.ApplySellView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/4.
 */
public class ApplySellPresenter extends ColpencilPresenter<ApplySellView> {

    private IApplySellModel sellModel;

    public ApplySellPresenter(){
        sellModel = new ApplySellModel();
    }

    public void applySell(HashMap<String, RequestBody> params){
        sellModel.applySell(params);
        Subscriber<ResultInfo<SellApply>> subscriber = new Subscriber<ResultInfo<SellApply>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<SellApply> resultInfo) {
                if(resultInfo.getCode() == 1){
                   mView.applySell(resultInfo);
                } else {
                    mView.loadError(resultInfo.getMessage());
                }
            }
        };
        sellModel.sub(subscriber);
    }

    public void sellData(){
        sellModel.sellData();
        Subscriber<Result<SellAdv>> subscriber = new Subscriber<Result<SellAdv>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<SellAdv> resultInfo) {
                if(resultInfo.getCode() == 1){
                   mView.sellData(resultInfo);
                } else {
                    mView.sellError(resultInfo);
                }
            }
        };
        sellModel.subSell(subscriber);
    }

    public void advCount(int id){
        sellModel.advCount(id);
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
                    mView.advCount(result_comment);
                } else {
                    mView.countError(result_comment.getMessage());
                }
            }
        };
        sellModel.subAdv(subscriber);
    }

}
