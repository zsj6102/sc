package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Present.Mine.AboutPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Imples.Mine.IAboutView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.WebViewTool;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/9.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_webview)
public class AboutActivity extends ColpencilActivity implements IAboutView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.common_progress)
    ProgressBar bar;

    @Bind(R.id.web_about)
    WebView webView;

    private AboutPresenter presenter;
    private WebViewTool tool;

    @Override
    protected void initViews(View view) {
        tv_title.setText("关于银台");
        showLoading("加载中");
        bar.setVisibility(View.VISIBLE);
        tool = new WebViewTool();
        presenter.about();

        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new AboutPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void about(String url) {
        hideLoading();
        tool.load(webView, url, bar);
    }

    @Override
    public void loadError(String message) {
        hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
