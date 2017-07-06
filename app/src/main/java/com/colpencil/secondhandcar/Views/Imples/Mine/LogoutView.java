package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.Logout;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/5.
 */
public interface LogoutView extends ColpencilBaseView {

    void logout(Logout logout);

    void loadError(Logout logout);
}
