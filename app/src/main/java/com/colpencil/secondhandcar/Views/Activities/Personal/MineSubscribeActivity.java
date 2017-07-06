package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.MineSub;
import com.colpencil.secondhandcar.Bean.Response.MineSubscribe;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Mine.MineSubscribePresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Ui.AlertDialog;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Adapter.Mine.MineSubscribeAdapter;
import com.colpencil.secondhandcar.Views.Imples.Mine.MineSubscribeView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/3/23.
 * 我的订阅
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_mine_subscribe)
public class MineSubscribeActivity extends ColpencilActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener, MineSubscribeView, AutoLoadRecylerView.loadMoreListener{

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.ll_rigth)
    RelativeLayout rl_right;

    @Bind(R.id.tv_rigth)
    ImageView iv_right;

    @Bind(R.id.swipe)
    SwipeRefreshLayout sp_sub;

    @Bind(R.id.list_subscribe)
    AutoLoadRecylerView lv_sub;

    @Bind(R.id.layout_subscribe)
    LinearLayout ll_subscribe;

    @Bind(R.id.empty)
    LinearLayout empty;

    @Bind(R.id.sv)
    LinearLayout sv;

    private MineSubscribeAdapter subscribeAdapter;
    private List<MineSubscribe> subscribeList = new ArrayList<>();

    private Intent intent;
    private MineSubscribePresenter presenter;
    private int pageNo = 1;
    private int pageSize = 10;
    private Observable<RxMsg> observable;

    @Override
    protected void initViews(View view) {
        tv_title.setText("我的订阅");
        showLoading("加载中");
        iv_right.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
        sv.setVisibility(View.GONE);

        sp_sub.setOnRefreshListener(this);
        sp_sub.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        lv_sub.setLayoutManager(new LinearLayoutManager(this));
        lv_sub.setLoadMoreListener(this);
        subscribeAdapter = new MineSubscribeAdapter(this, subscribeList, R.layout.item_parent_subscribe);
        lv_sub.setAdapter(subscribeAdapter);

        subscribeAdapter.setListener(new MineSubscribeAdapter.clickListener() {
            @Override
            public void onClick(int position) {
                HashMap<String, String> params = new HashMap<>();
                params.put("member_id", SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getInt("member_id")+"");
                params.put("token", SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getString("token"));
                params.put("id", subscribeList.get(position).getId()+"");
                presenter.deleteSubscribe(params);
            }
        });

        loadSubscribe();
        ll_left.setOnClickListener(this);
        ll_subscribe.setOnClickListener(this);
        rl_right.setOnClickListener(this);
        initBus();
    }

    /**
     * 初始化event
     */
    private void initBus(){
        observable = RxBus.get().register("rxAdd", RxMsg.class);
        Subscriber<RxMsg> subscriber = new Subscriber<RxMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxMsg rxMsg) {
                if(rxMsg.getIsSuccess() == 1){
                    onRefresh();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    private void loadSubscribe(){
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
        params.put("pageNo", pageNo+"");
        params.put("pageSize", pageSize+"");
        presenter.mineSubscribe(params);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MineSubscribePresenter();
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
            case R.id.layout_subscribe: //添加订阅
                intent = new Intent(this, AddSubscribeActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_rigth: //清空订阅
                if(subscribeList.size() > 0 && subscribeList != null){
                    new AlertDialog(this)
                            .builder()
                            .setCancelable(false)
                            .setMsg("确定清空所有订阅")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    HashMap<String, String> params = new HashMap<>();
                                    params.put("member_id", SharedPreferencesUtil.getInstance(MineSubscribeActivity.this).getInt("member_id")+"");
                                    params.put("token", SharedPreferencesUtil.getInstance(MineSubscribeActivity.this).getString("token"));
                                    presenter.cleanSubscribe(params);
                                }
                            }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
                }
                break;
        }
    }

    @Override
    public void mineSubscribe(Result<MineSubscribe> resultInfo) {
        hideLoading();
        if(resultInfo.getData() != null && resultInfo.getData().size() > 0){
            sv.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            subscribeList.clear();
            subscribeList.addAll(resultInfo.getData());
            subscribeAdapter.notifyDataSetChanged();
        } else {
            sv.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadMore(Result<MineSubscribe> resultInfo) {
        hideLoading();
        lv_sub.setLoading(false);
        if(resultInfo.getData().size() == 0){

        } else {
            subscribeList.addAll(resultInfo.getData());
            subscribeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadError(Result<MineSubscribe> result) {
        hideLoading();
        if(result.getCode() == -1){
            Toast.makeText(CarApplication.getInstance(), "身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setBoolean("isLogin", false);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("member_id", 0);
            SharedPreferencesUtil.getInstance(this).setString("isCommit", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("token", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("is_store", 0);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("mobile", " ");
            RxBusMsg msg = new RxBusMsg();
            msg.setCarType(1);
            msg.setLogin(false);
            RxBus.get().post("rxIsLogin", msg);
            ColpencilFrame.getInstance().finishActivity(this);
            startActivity(new Intent(CarApplication.getInstance(), LoginActivity.class));
        } else {
            empty.setVisibility(View.VISIBLE);
            sv.setVisibility(View.GONE);
        }
    }

    @Override
    public void deleteSubscribe(MineSub mineSub) {
        Toast.makeText(this, mineSub.getMessage(), Toast.LENGTH_SHORT).show();
        onRefresh();
    }

    @Override
    public void deleteError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cleanSubscribe(Result_comment result_comment) {
        Toast.makeText(this, result_comment.getMessage(), Toast.LENGTH_SHORT).show();
        onRefresh();
    }

    @Override
    public void cleanError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxAdd", observable);
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        loadSubscribe();
        sp_sub.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        loadSubscribe();
    }
}
