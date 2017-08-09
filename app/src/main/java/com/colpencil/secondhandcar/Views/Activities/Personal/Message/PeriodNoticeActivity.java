package com.colpencil.secondhandcar.Views.Activities.Personal.Message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.MsgPeriod;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Present.Mine.InsMsgRecordPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Adapter.Mine.MsgPeriodAdapter;
import com.colpencil.secondhandcar.Views.Imples.Mine.InsMsgRecordView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/12.
 * 分期购车通知
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_period_notice)
public class PeriodNoticeActivity extends ColpencilActivity implements View.OnClickListener,InsMsgRecordView, SwipeRefreshLayout.OnRefreshListener, AutoLoadRecylerView.loadMoreListener{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.swipe)
    SwipeRefreshLayout sp;

    @Bind(R.id.list_notice)
    AutoLoadRecylerView lv_notice;

    @Bind(R.id.empty)
    LinearLayout empty;

    private MsgPeriodAdapter msgPeriodAdapter;
    private List<MsgPeriod> msgPeriods;
    private InsMsgRecordPresenter presenter;
    private int pageNo = 1;
    private int pageSize = 10;

    @Override
    protected void initViews(View view) {
        tv_title.setText("分期购车通知");
        showLoading("加载中");
        empty.setVisibility(View.GONE);
        lv_notice.setVisibility(View.GONE);
        sp.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        sp.setOnRefreshListener(this);
        sp.setRefreshing(true);
        loadData();
        msgPeriods = new ArrayList<>();
        lv_notice.setLayoutManager(new LinearLayoutManager(this));
        lv_notice.setLoadMoreListener(this);
        msgPeriodAdapter = new MsgPeriodAdapter(this, msgPeriods, R.layout.item_periodization_remind);
        lv_notice.setAdapter(msgPeriodAdapter);
        msgPeriodAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(PeriodNoticeActivity.this, MessageContentActivity.class);
                intent.putExtra("id", msgPeriods.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        ll_left.setOnClickListener(this);
    }

    private void loadData(){
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
        params.put("pageNo", pageNo+"");
        params.put("pageSize", pageSize+"");
        presenter.insMsgRecord(params);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new InsMsgRecordPresenter();
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
        }
    }

    @Override
    public void insMsgRecord(Result<MsgPeriod> result) {
        hideLoading();
        sp.setRefreshing(false);
        if(result.getData() != null && result.getData().size() > 0){
            empty.setVisibility(View.GONE);
            lv_notice.setVisibility(View.VISIBLE);
            msgPeriods.clear();
            msgPeriods.addAll(result.getData());
            msgPeriodAdapter.notifyDataSetChanged();
        } else {
            empty.setVisibility(View.VISIBLE);
            lv_notice.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadMore(Result<MsgPeriod> result) {
        hideLoading();
        lv_notice.setLoading(false);
        if(result.getData().size() == 0){

        } else {
            msgPeriods.addAll(result.getData());
            msgPeriodAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadError(String message) {
        hideLoading();
        empty.setVisibility(View.VISIBLE);
        lv_notice.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        loadData();
        sp.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        loadData();
    }
}
