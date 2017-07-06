package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.Browse;
import com.colpencil.secondhandcar.Bean.Response.PersonalGroom;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/10.
 */
public interface BrowseView extends ColpencilBaseView {

    void browseRecord(Result<PersonalGroom> result);

    void loadMore(Result<PersonalGroom> result);

    void loadError(Result<PersonalGroom> result);

    void deleteBrowse(Browse browse);

    void deleteError(String message);

    void cleanBrowse(Browse browse);

    void cleanError(String message);
}
