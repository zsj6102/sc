package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Imples.IAboutModel;
import com.colpencil.secondhandcar.Model.Mine.AboutModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.IAboutView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/9.
 */
public class AboutPresenter extends ColpencilPresenter<IAboutView> {

    private IAboutModel aboutModel;

    public AboutPresenter(){
        aboutModel = new AboutModel();
    }

    public void about(){
        aboutModel.about();
        Subscriber<ResultInfo<Url>> subscriber = new Subscriber<ResultInfo<Url>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<Url> aboutUrlResultInfo) {
                if(aboutUrlResultInfo.getCode() == 1){
                    mView.about(aboutUrlResultInfo.getData().getUrl());
                } else {
                    mView.loadError(aboutUrlResultInfo.getMessage());
                }
            }
        };
        aboutModel.sub(subscriber);
    }
}
