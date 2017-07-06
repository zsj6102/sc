package com.colpencil.secondhandcar.Views.Imples.Welcome;

import com.colpencil.secondhandcar.Bean.Response.Login;
import com.colpencil.secondhandcar.Bean.Response.SendCode;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/2.
 */
public interface LoginView extends ColpencilBaseView {

    void login(ResultInfo<Login> result);

    void loginFaile(String message);

    void sendSuccess(SendCode code);

    void sendFaile(String message);
}
