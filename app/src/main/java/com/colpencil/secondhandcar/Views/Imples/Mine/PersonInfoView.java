package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.PersonInfo;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/26.
 */
public interface PersonInfoView extends ColpencilBaseView {

    void personInfo(ResultInfo<PersonInfo> resultInfo);

    void loadError(ResultInfo<PersonInfo> resultInfo);

    void changeInfo(Result_comment result_comment);

    void changeFailure(String message);
}
