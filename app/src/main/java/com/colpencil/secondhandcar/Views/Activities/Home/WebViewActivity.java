package com.colpencil.secondhandcar.Views.Activities.Home;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.colpencil.secondhandcar.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.WebViewTool;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/5/15.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_web_view)
public class WebViewActivity extends ColpencilActivity {

    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.common_progress)
    ProgressBar bar;

    private WebViewTool tool;
    private String url;

    @Override
    protected void initViews(View view) {
        tv_title.setText("确认支付");
        hideLoading();
        url = getIntent().getStringExtra("url");
        tool = new WebViewTool();
        bar.setVisibility(View.VISIBLE);
        tool.load(webView, url, bar);
    }

    @OnClick(R.id.ll_left)
    void back() {
        finish();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
}
