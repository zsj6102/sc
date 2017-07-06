package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.SubscribeGood;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Present.Mine.SubscribeRecordPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Ui.FlowTag.FlowTagLayout;
import com.colpencil.secondhandcar.Views.Activities.Buy.CarDetailsActivity;
import com.colpencil.secondhandcar.Views.Adapter.FlowTag.TagAdapter;
import com.colpencil.secondhandcar.Views.Adapter.Mine.MineSubscribeCarAdapter;
import com.colpencil.secondhandcar.Views.Imples.Mine.SubscribeRecordView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/12.
 * 订阅列表
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_subcribe_record)
public class SubscribeDetailsActivity extends ColpencilActivity implements SwipeRefreshLayout.OnRefreshListener, SubscribeRecordView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.br_record)
    SwipeRefreshLayout br_record;

    @Bind(R.id.cl_car)
    ListView lv_car;

    @Bind(R.id.rv_tag)
    FlowTagLayout rv_tag;

    @Bind(R.id.empty)
    LinearLayout empty;

    @Bind(R.id.scroll)
    NestedScrollView scrollView;

    private List<SubscribeGood> carList;
    private List<String> tagList;
    private MineSubscribeCarAdapter subscribeCarAdapter;
    private TagAdapter tagAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SubscribeRecordPresenter presenter;
    private int id;
    private int pageNo = 1;
    private int pageSize = 10;

    @Override
    protected void initViews(View view) {
        tv_title.setText("订阅列表");
        showLoading("加载中");
        empty.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
        id = getIntent().getIntExtra("id", 0);

        br_record.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        br_record.setOnRefreshListener(this);

        carList = new ArrayList<>();
        tagList = new ArrayList<>();
        tagList = getIntent().getStringArrayListExtra("tagList");

        subscribeCarAdapter = new MineSubscribeCarAdapter(this, carList, R.layout.item_child_subscribe_car);
        lv_car.setAdapter(subscribeCarAdapter);
        lv_car.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SubscribeDetailsActivity.this, CarDetailsActivity.class);
                intent.putExtra("carId", carList.get(position).getGoods_id());
                startActivity(intent);
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        tagAdapter = new TagAdapter(this);
        rv_tag.setAdapter(tagAdapter);
        tagAdapter.onlyAddAll(tagList);
        loadSubscribeRecord();

        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadSubscribeRecord(){
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id+"");
        params.put("pageNo", pageNo+"");
        params.put("pageSize", pageSize+"");
        params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
        presenter.subscribeRecord(params);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new SubscribeRecordPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void subscribeRecord(Result<SubscribeGood> result) {
        hideLoading();
        if(result.getData() != null && result.getData().size() > 0){
            empty.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            carList.clear();
            carList.addAll(result.getData());
            subscribeCarAdapter.notifyDataSetChanged();
        } else {
            empty.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadError(String message) {
        hideLoading();
        empty.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        loadSubscribeRecord();
        br_record.setRefreshing(false);
    }
}
