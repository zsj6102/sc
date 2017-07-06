package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.BillRecord;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Mine.BillRecordPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Adapter.Mine.BillRecordAdapter;
import com.colpencil.secondhandcar.Views.Imples.Mine.BillRecordView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/23.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_bill_activity)
public class BillRecordActivity extends ColpencilActivity implements SwipeRefreshLayout.OnRefreshListener, BillRecordView, AutoLoadRecylerView.loadMoreListener{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.sp_record)
    SwipeRefreshLayout sp;

    @Bind(R.id.list_record)
    AutoLoadRecylerView lv_record;

    @Bind(R.id.empty)
    LinearLayout empty;

    private BillRecordAdapter adapter;
    private List<BillRecord> records = new ArrayList<>();
    private BillRecordPresenter presenter;
    private int pageNo = 1;
    private int pageSize = 10;

    @Override
    protected void initViews(View view) {
        tv_title.setText("结算记录");
        showLoading("加载中");
        empty.setVisibility(View.GONE);
        lv_record.setVisibility(View.GONE);
        sp.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        sp.setOnRefreshListener(this);
        lv_record.setLayoutManager(new LinearLayoutManager(this));
        lv_record.setLoadMoreListener(this);
        adapter = new BillRecordAdapter(this, records, R.layout.item_bill_record);
        lv_record.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(BillRecordActivity.this, BillDetailsActivity.class);
                intent.putExtra("id", records.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
        billRecord();
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void billRecord(){
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
        params.put("pageNo", pageNo+"");
        params.put("pageSize", pageSize+"");
        presenter.billRecord(params);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new BillRecordPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        billRecord();
        sp.setRefreshing(false);
    }

    @Override
    public void billRecord(Result<BillRecord> result) {
        hideLoading();
        if(result.getData() != null && result.getData().size() > 0){
            empty.setVisibility(View.GONE);
            lv_record.setVisibility(View.VISIBLE);
            records.clear();
            records.addAll(result.getData());
            adapter.notifyDataSetChanged();
        } else {
            empty.setVisibility(View.VISIBLE);
            lv_record.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadMore(Result<BillRecord> result) {
        hideLoading();
        lv_record.setLoading(false);
        if(result.getData().size() == 0){

        } else {
            records.addAll(result.getData());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadError(Result<BillRecord> result) {
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
            empty.setVisibility(View.VISIBLE);
            lv_record.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        billRecord();
    }
}
