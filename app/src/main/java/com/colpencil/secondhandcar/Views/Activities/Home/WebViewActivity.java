package com.colpencil.secondhandcar.Views.Activities.Home;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.colpencil.secondhandcar.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.WebViewTool;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/15.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_web_view)
public class WebViewActivity extends ColpencilActivity {

    @Bind(R.id.web_view)
    WebView webView;

    @Bind(R.id.common_progress)
    ProgressBar bar;

    private WebViewTool tool;
    private String url;

    @Override
    protected void initViews(View view) {
        showLoading("加载中");
        url = getIntent().getStringExtra("url");
        tool = new WebViewTool();
        hideLoading();
        tool.load(webView, url, bar);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
}
