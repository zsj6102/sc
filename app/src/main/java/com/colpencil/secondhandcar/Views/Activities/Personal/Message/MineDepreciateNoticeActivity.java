package com.colpencil.secondhandcar.Views.Activities.Personal.Message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.MineDepreciateNotice;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Present.Mine.ReduceNoticeRecordPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Activities.Buy.CarDetailsActivity;
import com.colpencil.secondhandcar.Views.Adapter.Mine.MineDepreciateNoticeAdapter;
import com.colpencil.secondhandcar.Views.Imples.Mine.ReduceNoticeRecordView;
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
 * Created by Administrator on 2017/4/10.
 * 降价通知
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_depreciate_notice)
public class MineDepreciateNoticeActivity extends ColpencilActivity implements SwipeRefreshLayout.OnRefreshListener,ReduceNoticeRecordView, AutoLoadRecylerView.loadMoreListener{

    @Bind(R.id.sp_depreciate)
    SwipeRefreshLayout sp_depreciate;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.list_depreciate)
    AutoLoadRecylerView lv_depreciate;

    @Bind(R.id.empty)
    LinearLayout empty;

    @Bind(R.id.text_title)
    TextView text_title;

    @Bind(R.id.layout_data)
    LinearLayout ll_data;

    private MineDepreciateNoticeAdapter depreciateNoticeAdapter;
    private List<MineDepreciateNotice> depreciateNotices;
    private ReduceNoticeRecordPresenter presenter;
    private int pageNo = 1;
    private int pageSize = 10;

    @Override
    protected void initViews(View view) {
        tv_title.setText("降价通知");
        showLoading("加载中");
        empty.setVisibility(View.GONE);
        ll_data.setVisibility(View.GONE);
        sp_depreciate.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        sp_depreciate.setOnRefreshListener(this);

        depreciateNotices = new ArrayList<>();
        lv_depreciate.setLayoutManager(new LinearLayoutManager(this));
        lv_depreciate.setLoadMoreListener(this);
        depreciateNoticeAdapter = new MineDepreciateNoticeAdapter(this, depreciateNotices, R.layout.item_depreciate_notice);
        lv_depreciate.setAdapter(depreciateNoticeAdapter);
        depreciateNoticeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MineDepreciateNoticeActivity.this, CarDetailsActivity.class);
                intent.putExtra("carId", depreciateNotices.get(position).getGoods_id());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        loadData();
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData(){
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
        params.put("pageNo", pageNo+"");
        params.put("pageSize", pageSize+"");
        presenter.reduceNoticeRecord(params);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new ReduceNoticeRecordPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        loadData();
        sp_depreciate.setRefreshing(false);
    }

    @Override
    public void reduceNoticeRecord(Result<MineDepreciateNotice> result) {
        hideLoading();
        if(result.getData() != null && result.getData().size() > 0){
            empty.setVisibility(View.GONE);
            ll_data.setVisibility(View.VISIBLE);
            text_title.setText(result.getData().get(0).getTitle());
            depreciateNotices.clear();
            depreciateNotices.addAll(result.getData());
            depreciateNoticeAdapter.notifyDataSetChanged();
        } else {
            empty.setVisibility(View.VISIBLE);
            ll_data.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadMore(Result<MineDepreciateNotice> result) {
        hideLoading();
        lv_depreciate.setLoading(false);
        if(result.getData().size() == 0){

        } else {
            depreciateNotices.addAll(result.getData());
            depreciateNoticeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadError(String message) {
        hideLoading();
        empty.setVisibility(View.VISIBLE);
        ll_data.setVisibility(View.GONE);
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        loadData();
    }
}
