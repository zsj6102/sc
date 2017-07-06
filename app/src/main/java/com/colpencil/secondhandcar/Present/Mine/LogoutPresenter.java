package com.colpencil.secondhandcar.Present.Mine;

import com.colpencil.secondhandcar.Bean.Response.Logout;
import com.colpencil.secondhandcar.Model.Imples.ILogoutModel;
import com.colpencil.secondhandcar.Model.Mine.LogoutModel;
import com.colpencil.secondhandcar.Views.Imples.Mine.LogoutView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/5.
 */
public class LogoutPresenter extends ColpencilPresenter<LogoutView> {

    private ILogoutModel logoutModel;

    public LogoutPresenter(){
        logoutModel = new LogoutModel();
    }

    public void logout(int memberId, String token){
        logoutModel.logout(memberId, token);
        Subscriber<Logout> subscriber = new Subscriber<Logout>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Logout logout) {
                if(logout.getCode() == 1){
                    mView.logout(logout);
                } else {
                    mView.loadError(logout);
                }
            }
        };
        logoutModel.sub(subscriber);
    }
}
