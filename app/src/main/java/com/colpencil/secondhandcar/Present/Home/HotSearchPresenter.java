package com.colpencil.secondhandcar.Present.Home;

import com.colpencil.secondhandcar.Bean.Response.HotSearch;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Home.HotSearchModel;
import com.colpencil.secondhandcar.Model.Imples.IHotSearchModel;
import com.colpencil.secondhandcar.Views.Imples.Home.HotSearchView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/5.
 */
public class HotSearchPresenter extends ColpencilPresenter<HotSearchView> {

    private IHotSearchModel hotSearchModel;

    public HotSearchPresenter(){
        hotSearchModel = new HotSearchModel();
    }

    public void hotBrand(){
        hotSearchModel.hotSearch();
        Subscriber<Result<HotSearch>> subscriber = new Subscriber<Result<HotSearch>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<HotSearch> result) {
                if(result.getCode() == 1){
                    mView.hotBrand(result);
                } else {
                    mView.loadError(result.getMessage());
                }
            }
        };
        hotSearchModel.sub(subscriber);
    }
}
