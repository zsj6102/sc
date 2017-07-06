package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.MineRemind;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Imples.IReducePriceModel;
import com.colpencil.secondhandcar.Model.Mine.ReducePriceModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.ReducePriceView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/12.
 */
public class ReducePricePresenter extends ColpencilPresenter<ReducePriceView> {

    private IReducePriceModel reducePriceModel;

    public ReducePricePresenter(){
        reducePriceModel = new ReducePriceModel();
    }

    public void reducePriceRecord(HashMap<String, String> params){
        reducePriceModel.reducePrice(params);
        Subscriber<ResultInfo<MineRemind>> subscriber = new Subscriber<ResultInfo<MineRemind>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<MineRemind> remindResult) {
                if(remindResult.getCode() == 1){
                    mView.reducePriceRecord(remindResult);
                } else {
                    mView.loadError(remindResult);
                }
            }
        };
        reducePriceModel.sub(subscriber);
    }

    public void cleanRecord(HashMap<String, String> params){
        reducePriceModel.cleanRecord(params);
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
                    mView.cleanRecord(result_comment);
                } else {
                    mView.cleanError(result_comment.getMessage());
                }
            }
        };
        reducePriceModel.subClean(subscriber);
    }

    public void deleteReduce(HashMap<String, String> params){
        reducePriceModel.deleteReduce(params);
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
                    mView.deleteReduce(result_comment);
                } else {
                    mView.deleteError(result_comment.getMessage());
                }
            }
        };
        reducePriceModel.subDelete(subscriber);
    }
}
