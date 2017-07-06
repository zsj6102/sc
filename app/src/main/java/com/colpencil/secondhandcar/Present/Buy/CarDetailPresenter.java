package com.colpencil.secondhandcar.Present.Buy;

import com.colpencil.secondhandcar.Bean.Response.Browse;
import com.colpencil.secondhandcar.Bean.Response.CarInfo;
import com.colpencil.secondhandcar.Bean.Response.Collection;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Buy.CarDetailModel;
import com.colpencil.secondhandcar.Model.Imples.ICarDetailModel;
import com.colpencil.secondhandcar.Views.Imples.Buy.CarDetailView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/5.
 */
public class CarDetailPresenter extends ColpencilPresenter<CarDetailView> {

    private ICarDetailModel carDetailModel;

    public CarDetailPresenter(){
        carDetailModel = new CarDetailModel();
    }

    public void carDetail(HashMap<String, String> params){
        carDetailModel.carDetail(params);
        Subscriber<ResultInfo<CarInfo>> subscriber = new Subscriber<ResultInfo<CarInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<CarInfo> resultInfo) {
                if(resultInfo.getCode() == 1){
                    mView.carInfo(resultInfo);
                } else {
                    mView.loadError(resultInfo);
                }
            }
        };
        carDetailModel.sub(subscriber);
    }

    public void addBrowse(HashMap<String, String> params){
        carDetailModel.addBrowse(params);
        Subscriber<Browse> browseSubscriber = new Subscriber<Browse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Browse browse) {
                if(browse.getCode() == 1 || browse.getCode() == 2){
                    mView.addBrowse(browse);
                } else {
                    mView.addBrowseError(browse.getMessage());
                }
            }
        };
        carDetailModel.subBrowse(browseSubscriber);
    }

    public void addCollection(HashMap<String, String> params){
        carDetailModel.addCollection(params);
        Subscriber<Collection> subscriber = new Subscriber<Collection>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Collection collection) {
                if(collection.getCode() == 1 || collection.getCode() == 2){
                    mView.addCollection(collection);
                } else {
                    mView.addCollectionError(collection);
                }
            }
        };
        carDetailModel.subCollection(subscriber);
    }

    public void addReducePrice(HashMap<String, String> params){
        carDetailModel.addReducePrice(params);
        Subscriber<Result_comment> subscriber = new Subscriber<Result_comment>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result_comment result_comment) {
                if(result_comment.getCode() == 1 || result_comment.getCode() == 2){
                    mView.addReducePrice(result_comment);
                } else {
                    mView.addReducePriceError(result_comment);
                }
            }
        };
        carDetailModel.subReducePrice(subscriber);
    }
}
