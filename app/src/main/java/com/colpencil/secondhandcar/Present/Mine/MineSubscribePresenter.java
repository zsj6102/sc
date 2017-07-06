package com.colpencil.secondhandcar.Present.Mine;

import android.util.Log;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.MineSub;
import com.colpencil.secondhandcar.Bean.Response.MineSubscribe;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Model.Imples.IMineSubscribeModel;
import com.colpencil.secondhandcar.Model.Mine.MineSubscribeModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.MineSubscribeView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/11.
 */
public class MineSubscribePresenter extends ColpencilPresenter<MineSubscribeView> {

    private IMineSubscribeModel mineSubscribeModel;

    public MineSubscribePresenter(){
        mineSubscribeModel = new MineSubscribeModel();
    }

    public void mineSubscribe(final HashMap<String, String> params){
        mineSubscribeModel.mineSubscribe(params);
        Subscriber<Result<MineSubscribe>> subscriber = new Subscriber<Result<MineSubscribe>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<MineSubscribe> resultInfo) {
                if(resultInfo.getCode() == 1){
                    if(params.get("pageNo").equals("1")){
                        mView.mineSubscribe(resultInfo);
                    }else {
                        mView.loadMore(resultInfo);
                    }
                } else {
                    mView.loadError(resultInfo);
                }
            }
        };
        mineSubscribeModel.sub(subscriber);
    }

    public void deleteSubscribe(HashMap<String, String> params){
        mineSubscribeModel.deleteSubscribe(params);
        Subscriber<MineSub> subSubscriber = new Subscriber<MineSub>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MineSub mineSub) {
                if(mineSub.getCode() == 1){
                    mView.deleteSubscribe(mineSub);
                } else {
                    mView.deleteError(mineSub.getMessage());
                }
            }
        };
        mineSubscribeModel.subDelete(subSubscriber);
    }

    public void cleanSubscribe(HashMap<String, String> params){
        mineSubscribeModel.cleanSubscribe(params);
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
                   mView.cleanSubscribe(result_comment);
                } else {
                    mView.cleanError(result_comment.getMessage());
                }
            }
        };
        mineSubscribeModel.subClean(subscriber);
    }
}
