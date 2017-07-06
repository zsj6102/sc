package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.Feedback;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Mine.FeedbackListPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Adapter.Mine.FeedbackRecordAdapter;
import com.colpencil.secondhandcar.Views.Imples.Mine.FeedbackListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/8.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_feedback_record)
public class FeedbackRecordActivity extends ColpencilActivity implements View.OnClickListener,FeedbackListView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.list_record)
    ListView lv_record;

    @Bind(R.id.layout_empty)
    LinearLayout ll_empty;

    @Bind(R.id.text_refresh)
    TextView tv_refresh;

    @Bind(R.id.text_error)
    TextView tv_error;

    private FeedbackListPresenter presenter;
    private List<Feedback> feedBacks = new ArrayList<>();
    private FeedbackRecordAdapter adapter;

    @Override
    protected void initViews(View view) {
        tv_title.setText("意见记录");
        ll_empty.setVisibility(View.GONE);
        lv_record.setVisibility(View.GONE);
        showLoading("加载中");
        adapter = new FeedbackRecordAdapter(this, feedBacks );
        lv_record.setAdapter(adapter);
        presenter.feedbackList(SharedPreferencesUtil.getInstance(this).getInt("member_id"), SharedPreferencesUtil.getInstance(this).getString("token"));
        ll_left.setOnClickListener(this);
//        tv_refresh.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new FeedbackListPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
//            case R.id.text_refresh: //刷新
//                presenter.feedbackList(SharedPreferencesUtil.getInstance(this).getInt("member_id"), SharedPreferencesUtil.getInstance(this).getString("token"));
//                break;
        }
    }

    @Override
    public void feedbackList(Result<Feedback> result) {
        hideLoading();
        if(result.getData().size() > 0 && result.getData() != null){
            lv_record.setVisibility(View.VISIBLE);
            ll_empty.setVisibility(View.GONE);
            feedBacks.clear();
            feedBacks.addAll(result.getData());
            adapter.notifyDataSetChanged();
        } else {
            lv_record.setVisibility(View.GONE);
            ll_empty.setVisibility(View.VISIBLE);
            tv_error.setText("您还没反馈记录");
        }
    }

    @Override
    public void loadError(Result<Feedback> result) {
        hideLoading();
        if(result.getCode() == -1){
            Toast.makeText(CarApplication.getInstance(), "身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setBoolean("isLogin", false);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("member_id", 0);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("token", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("is_store", 0);
            SharedPreferencesUtil.getInstance(this).setString("isCommit", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("mobile", " ");
            RxBusMsg msg = new RxBusMsg();
            msg.setCarType(1);
            msg.setLogin(false);
            RxBus.get().post("rxIsLogin", msg);
            ColpencilFrame.getInstance().finishActivity(this);
            startActivity(new Intent(CarApplication.getInstance(), LoginActivity.class));
        } else {
//            Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
            lv_record.setVisibility(View.GONE);
            ll_empty.setVisibility(View.VISIBLE);
            tv_error.setText("获取数据失败，请检查网络连接");
        }
    }
}
