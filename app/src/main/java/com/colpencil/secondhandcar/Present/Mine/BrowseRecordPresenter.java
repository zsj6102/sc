package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.Browse;
import com.colpencil.secondhandcar.Bean.Response.PersonalGroom;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Imples.IBrowseModel;
import com.colpencil.secondhandcar.Model.Mine.BrowseModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.BrowseView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/10.
 */
public class BrowseRecordPresenter extends ColpencilPresenter<BrowseView> {

    private IBrowseModel browseModel;

    public BrowseRecordPresenter(){
        browseModel = new BrowseModel();
    }

    public void browseRecord(final HashMap<String, String> params){
        browseModel.browseRecord(params);
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
                        mView.browseRecord(result);
                    } else {
                        mView.loadMore(result);
                    }
                } else {
                    mView.loadError(result);
                }
            }
        };
        browseModel.sub(subscriber);
    }

    public void deleteBrowse(HashMap<String, String> params){
        browseModel.deleteBrowse(params);
        Subscriber<Browse> subscriber = new Subscriber<Browse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Browse browse) {
                if(browse.getCode() == 1){
                    mView.deleteBrowse(browse);
                } else {
                    mView.deleteError(browse.getMessage());
                }
            }
        };
        browseModel.subDelete(subscriber);
    }

    public void cleanBrowse(HashMap<String, String> params){
        browseModel.cleanBrowse(params);
        Subscriber<Browse> subscriber = new Subscriber<Browse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Browse browse) {
                if(browse.getCode() == 1){
                    mView.cleanBrowse(browse);
                } else {
                    mView.cleanError(browse.getMessage());
                }
            }
        };
        browseModel.subClean(subscriber);
    }
}
