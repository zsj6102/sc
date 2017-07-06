package com.colpencil.secondhandcar.Views.Activities.Welcome;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Present.TermPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Imples.Welcome.TermView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.WebViewTool;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/16.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_term)
public class TermActivity extends ColpencilActivity implements TermView, View.OnClickListener{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.web_view)
    WebView webView;

    @Bind(R.id.common_progress)
    ProgressBar bar;

    private WebViewTool tool;
    private TermPresenter presenter;

    @Override
    protected void initViews(View view) {
        tv_title.setText("服务条款");
        bar.setVisibility(View.VISIBLE);
        tool = new WebViewTool();
        presenter.term();
        ll_left.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new TermPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void term(ResultInfo<Url> resultInfo) {
        bar.setVisibility(View.GONE);
        tool.load(webView, resultInfo.getData().getUrl(), bar);
    }

    @Override
    public void loadError(String message) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
