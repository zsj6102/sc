package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/9.
 */
public interface IAboutView extends ColpencilBaseView {

    void about(String url);

    void loadError(String message);
}
