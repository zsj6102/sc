package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.Collection;
import com.colpencil.secondhandcar.Bean.Response.PersonalGroom;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/10.
 */
public interface CollectionView extends ColpencilBaseView {

    void collectionRecord(Result<PersonalGroom> result);

    void loadMore(Result<PersonalGroom> result);

    void loadError(Result<PersonalGroom> result);

    void deleteCollection(Collection collection);

    void deleteError(String message);

    void cleanCollection(Collection collection);

    void cleanError(String message);
}
