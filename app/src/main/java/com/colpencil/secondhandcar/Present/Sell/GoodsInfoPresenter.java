package com.colpencil.secondhandcar.Present.Sell;

import com.colpencil.secondhandcar.Bean.Response.GoodsInfo;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Imples.IGoodsInfoModel;
import com.colpencil.secondhandcar.Model.Sell.GoodsInfoModel;
import com.colpencil.secondhandcar.Views.Imples.Sell.GoodsInfoView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/22.
 */
public class GoodsInfoPresenter extends ColpencilPresenter<GoodsInfoView> {

    private IGoodsInfoModel infoModel;

    public GoodsInfoPresenter(){
        infoModel = new GoodsInfoModel();
    }

    public void getGoodsInfo(HashMap<String, String> params){
        infoModel.getGoodsInf(params);
        Subscriber<ResultInfo<GoodsInfo>> subscriber = new Subscriber<ResultInfo<GoodsInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<GoodsInfo> resultInfo) {
                if(resultInfo.getCode() == 1){
                    mView.getGoodsInfo(resultInfo);
                } else {
                    mView.loadError(resultInfo.getMessage());
                }
            }
        };
        infoModel.sub(subscriber);
    }
}
