package com.colpencil.secondhandcar.Present.Sell;

import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Imples.IEditGoodModel;
import com.colpencil.secondhandcar.Model.Sell.EditGoodModel;
import com.colpencil.secondhandcar.Views.Imples.Sell.EditGoodView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/6/1.
 */
public class EditGoodPresenter extends ColpencilPresenter<EditGoodView> {

    private IEditGoodModel model;

    public EditGoodPresenter(){
        model = new EditGoodModel();
    }

    public void editGood(HashMap<String, String> params){
        model.editGood(params);
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
                    mView.editGood(resultInfo);
                } else {
                    mView.loadError(resultInfo.getMessage());
                }
            }
        };
        model.sub(subscriber);
    }
}
