package com.colpencil.secondhandcar.Present;

import com.colpencil.secondhandcar.Bean.Response.Login;
import com.colpencil.secondhandcar.Bean.Response.SendCode;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Model.Imples.ILoginModel;
import com.colpencil.secondhandcar.Model.Welcome.LoginModel;
import com.colpencil.secondhandcar.Views.Imples.Welcome.LoginView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/2.
 */
public class LoginPresenter extends ColpencilPresenter<LoginView> {

    private ILoginModel loginModel;

    public LoginPresenter(){
        loginModel = new LoginModel();
    }

    public void login(String mobile, String code){
        loginModel.login(mobile, code);

        Subscriber<ResultInfo<Login>> subscriber = new Subscriber<ResultInfo<Login>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<Login> loginResult) {
                if(loginResult.getCode() == 1){
                    mView.login(loginResult);
                } else {
                    mView.loginFaile(loginResult.getMessage());
                }
            }
        };
        loginModel.sub(subscriber);
    }

    public void sendCode(String mobile){
        loginModel.sendCode(mobile);
        Subscriber<SendCode> codeSubscriber = new Subscriber<SendCode>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SendCode code) {
                if(code.getCode() == 1){
                    mView.sendSuccess(code);
                } else {
                    mView.sendFaile(code.getMessage());
                }
            }
        };
        loginModel.subCode(codeSubscriber);
    }
}
