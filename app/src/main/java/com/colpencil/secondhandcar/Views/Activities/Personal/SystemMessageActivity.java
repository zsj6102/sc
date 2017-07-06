package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.MessageInfo;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Present.Mine.SystemMsgRecordPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Activities.Personal.Message.MessageContentActivity;
import com.colpencil.secondhandcar.Views.Adapter.Mine.SystemMsgAdapter;
import com.colpencil.secondhandcar.Views.Imples.Mine.SystemMsgRecordView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/12.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_system_message)
public class SystemMessageActivity extends ColpencilActivity implements SystemMsgRecordView, AutoLoadRecylerView.loadMoreListener, SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.swipe)
    SwipeRefreshLayout sp;

    @Bind(R.id.list_system)
    AutoLoadRecylerView lv_system;

    @Bind(R.id.empty)
    LinearLayout empty;

    private SystemMsgRecordPresenter presenter;
    private List<MessageInfo> systemMessages = new ArrayList<>();
    private SystemMsgAdapter adapter;
    private int pageNo = 1;
    private int pageSize = 10;

    @Override
    protected void initViews(View view) {
        tv_title.setText("系统消息");
        lv_system.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        sp.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        sp.setOnRefreshListener(this);
        lv_system.setLayoutManager(new LinearLayoutManager(this));
        lv_system.setLoadMoreListener(this);
        adapter = new SystemMsgAdapter(this, systemMessages, R.layout.item_system_msg_record);
        lv_system.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                systemMessages.get(position).setLook(true);
                Intent intent = new Intent(SystemMessageActivity.this, MessageContentActivity.class);
                intent.putExtra("id", systemMessages.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
//        lv_system.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                systemMessages.get(position).setLook(true);
//                Intent intent = new Intent(SystemMessageActivity.this, MessageContentActivity.class);
//                intent.putExtra("id", systemMessages.get(position).getId());
//                startActivity(intent);
//            }
//        });

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
        params.put("pageNo", pageNo+"");
        params.put("pageSize", pageSize+"");
        presenter.systemMsgRecord(params);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new SystemMsgRecordPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void systemMsgRecord(Result<MessageInfo> result) {
        empty.setVisibility(View.GONE);
        lv_system.setVisibility(View.VISIBLE);
        systemMessages.clear();
        systemMessages.addAll(result.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(Result<MessageInfo> result) {
        hideLoading();
        lv_system.setLoading(false);
        if(result.getData().size() == 0){

        } else {
            systemMessages.addAll(result.getData());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadError(String message) {
        empty.setVisibility(View.VISIBLE);
        lv_system.setVisibility(View.GONE);
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        loadData();
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        loadData();
        sp.setRefreshing(false);
    }
}
