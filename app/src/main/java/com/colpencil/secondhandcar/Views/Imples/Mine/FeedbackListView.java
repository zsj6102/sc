package com.colpencil.secondhandcar.Views.Imples.Mine;

import com.colpencil.secondhandcar.Bean.Response.Feedback;
import com.colpencil.secondhandcar.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/5/4.
 */
public interface FeedbackListView extends ColpencilBaseView {

    void feedbackList(Result<Feedback> result);

    void loadError(Result<Feedback> result);
}
