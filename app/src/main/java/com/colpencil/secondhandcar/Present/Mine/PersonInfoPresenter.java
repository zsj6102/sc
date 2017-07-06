package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.PersonInfo;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Imples.IPersonInfoModel;
import com.colpencil.secondhandcar.Model.Mine.PersonInfoModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.PersonInfoView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/26.
 */
public class PersonInfoPresenter extends ColpencilPresenter<PersonInfoView> {

    private IPersonInfoModel personInfoModel;

    public PersonInfoPresenter(){
        personInfoModel = new PersonInfoModel();
    }

    public void personInfo(HashMap<String, String> params){
        personInfoModel.personInfo(params);
        Subscriber<ResultInfo<PersonInfo>> subscriber = new Subscriber<ResultInfo<PersonInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<PersonInfo> resultInfo) {
                if(resultInfo.getCode() == 1){
                    mView.personInfo(resultInfo);
                } else {
                    mView.loadError(resultInfo);
                }
            }
        };
        personInfoModel.sub(subscriber);
    }

    public void changeInfo(HashMap<String, String> params){
        personInfoModel.changeInfo(params);
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
                    mView.changeInfo(result_comment);
                } else {
                    mView.changeFailure(result_comment.getMessage());
                }
            }
        };
        personInfoModel.subChange(subscriber);
    }
}
