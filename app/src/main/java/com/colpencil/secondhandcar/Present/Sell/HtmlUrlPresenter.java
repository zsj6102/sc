package com.colpencil.secondhandcar.Present.Sell;

import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Imples.IHtmlUrlModel;
import com.colpencil.secondhandcar.Model.Sell.HtmlUrlModel;
import com.colpencil.secondhandcar.Views.Imples.Sell.HtmlUrlView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/27.
 */
public class HtmlUrlPresenter extends ColpencilPresenter<HtmlUrlView> {

    private IHtmlUrlModel htmlUrlModel;

    public HtmlUrlPresenter(){
        htmlUrlModel = new HtmlUrlModel();
    }

    public void htmlUrl(HashMap<String, String> params){
        htmlUrlModel.htmlUrl(params);
        Subscriber<ResultInfo<Url>> subscriber = new Subscriber<ResultInfo<Url>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<Url> resultInfo) {
                if(resultInfo.getCode() == 1){
                    mView.htmlUrl(resultInfo);
                } else {
                    mView.loadError(resultInfo);
                }
            }
        };
        htmlUrlModel.sub(subscriber);
    }
}
