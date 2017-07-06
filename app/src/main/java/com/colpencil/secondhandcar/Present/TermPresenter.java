package com.colpencil.secondhandcar.Present;

import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Imples.ITermModel;
import com.colpencil.secondhandcar.Model.Welcome.TermModel;
import com.colpencil.secondhandcar.Views.Imples.Welcome.TermView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/16.
 */
public class TermPresenter extends ColpencilPresenter<TermView> {

    private ITermModel termModel;

    public TermPresenter(){
        termModel = new TermModel();
    }

    public void term(){
        termModel.term();
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
                    mView.term(resultInfo);
                } else {
                    mView.loadError(resultInfo.getMessage());
                }
            }
        };
        termModel.sub(subscriber);
    }
}
