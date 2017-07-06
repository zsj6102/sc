package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.Collection;
import com.colpencil.secondhandcar.Bean.Response.PersonalGroom;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Imples.ICollectionModel;
import com.colpencil.secondhandcar.Model.Mine.CollectionModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.CollectionView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/10.
 */
public class CollectionPresenter extends ColpencilPresenter<CollectionView> {

    private ICollectionModel collectionModel;

    public CollectionPresenter(){
        collectionModel = new CollectionModel();
    }

    public void collection(final HashMap<String, String> params){
        collectionModel.collectionRecord(params);
        Subscriber<Result<PersonalGroom>> subscriber = new Subscriber<Result<PersonalGroom>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<PersonalGroom> result) {
                if(result.getCode() == 1){
                    if(params.get("pageNo").equals("1")){
                        mView.collectionRecord(result);
                    } else {
                        mView.loadMore(result);
                    }
                } else {
                    mView.loadError(result);
                }
            }
        };
        collectionModel.sub(subscriber);
    }

    public void deleteCollection(HashMap<String, String> params){
        collectionModel.deleteCollection(params);
        Subscriber<Collection> subscriber = new Subscriber<Collection>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Collection collection) {
                if(collection.getCode() == 1){
                    mView.deleteCollection(collection);
                } else {
                    mView.deleteError(collection.getMessage());
                }
            }
        };
        collectionModel.subDelete(subscriber);
    }

    public void cleanCollection(HashMap<String, String> params){
        collectionModel.cleanCollection(params);
        Subscriber<Collection> subscriber = new Subscriber<Collection>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Collection collection) {
                if(collection.getCode() == 1){
                    mView.cleanCollection(collection);
                } else {
                    mView.cleanError(collection.getMessage());
                }
            }
        };
        collectionModel.subClean(subscriber);
    }
}
