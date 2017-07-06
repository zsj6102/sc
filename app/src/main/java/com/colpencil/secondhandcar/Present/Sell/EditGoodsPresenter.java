package com.colpencil.secondhandcar.Present.Sell;

import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Model.Imples.IEditGoodsModel;
import com.colpencil.secondhandcar.Model.Sell.EditGoodsModel;
import com.colpencil.secondhandcar.Views.Imples.Sell.EditGoodsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/17.
 */
public class EditGoodsPresenter extends ColpencilPresenter<EditGoodsView> {

    private IEditGoodsModel editGoodsModel;

    public EditGoodsPresenter(){
        editGoodsModel = new EditGoodsModel();
    }

    public void editGoods(HashMap<String, RequestBody> params){
        editGoodsModel.editGoods(params);
        Subscriber<Result_comment> subscriber = new Subscriber<Result_comment>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result_comment result_comment) {
                if(result_comment.getCode() == 1){
                    mView.editSuccess(result_comment);
                } else {
                    mView.editFaile(result_comment.getMessage());
                }
            }
        };
        editGoodsModel.sub(subscriber);
    }
}
