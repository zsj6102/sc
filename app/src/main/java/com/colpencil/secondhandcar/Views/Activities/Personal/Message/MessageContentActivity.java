package com.colpencil.secondhandcar.Views.Activities.Personal.Message;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Present.Mine.MsgContentPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Imples.Mine.MsgContentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.WebViewTool;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/1.
 * 消息详情
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_webview)
public class MessageContentActivity extends ColpencilActivity implements MsgContentView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.common_progress)
    ProgressBar bar;

    @Bind(R.id.web_about)
    WebView webView;

    private MsgContentPresenter presenter;
    private WebViewTool tool;
    private int id;

    @Override
    protected void initViews(View view) {
        id = getIntent().getIntExtra("id", 0);
        tv_title.setText("消息详情");
        showLoading("加载中");
        bar.setVisibility(View.VISIBLE);
        tool = new WebViewTool();
        presenter.msgContent(id);

        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MsgContentPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void msgContent(ResultInfo<Url> resultInfo) {
        hideLoading();
        bar.setVisibility(View.GONE);
        tool.load(webView, resultInfo.getData().getUrl(), bar);
    }

    @Override
    public void loadError(String message) {
        hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
