package com.colpencil.secondhandcar.Present.Buy;

import com.colpencil.secondhandcar.Bean.Response.Props;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Buy.PropsModel;
import com.colpencil.secondhandcar.Model.Imples.IPropsModel;
import com.colpencil.secondhandcar.Views.Imples.Buy.PropsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/12.
 */
public class PropsPresenter extends ColpencilPresenter<PropsView> {

    private IPropsModel propsModel;

    public PropsPresenter(){
        propsModel = new PropsModel();
    }

    public void getProps(int typeId){
        propsModel.getProps(typeId);
        Subscriber<ResultInfo<Props>> subscriber = new Subscriber<ResultInfo<Props>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<Props> resultInfo) {
                if(resultInfo.getCode() == 1){
                    mView.getProps(resultInfo);
                } else {
                    mView.propsError(resultInfo.getMessage());
                }
            }
        };
        propsModel.sub(subscriber);
    }
}
