package com.colpencil.secondhandcar.Views.Fragments.Personal.Release;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.Order;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Mine.OrderListPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Adapter.Mine.MinePeriodAdapter;
import com.colpencil.secondhandcar.Views.Imples.Mine.OrderListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
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
 * Created by Administrator on 2017/5/23.
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_car_order)
public class CarOrderFragment extends ColpencilFragment implements SwipeRefreshLayout.OnRefreshListener, OrderListView, AutoLoadRecylerView.loadMoreListener{

    @Bind(R.id.sp_order)
    SwipeRefreshLayout sp_order;

    @Bind(R.id.list_order)
    AutoLoadRecylerView lv_order;

    @Bind(R.id.empty)
    LinearLayout empty;

    private MinePeriodAdapter periodAdapter;
    private List<Order> minePeriods;
    private int status;
    private OrderListPresenter presenter;
    private int type;
    private int pageNo = 1;
    private int pageSize = 10;
    private Observable<RxMsg> observable;

    public static CarOrderFragment newsInstance(int type){
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        CarOrderFragment fragment = new CarOrderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        type = getArguments().getInt("type");
        if(type == 0){
            status = -1;
        } else if(type == 1){
            status = 0;
        } else if(type == 2){
            status = 2;
        } else if(type == 3){
            status = 3;
        } else if(type == 4){
            status = 6;
        }
        empty.setVisibility(View.GONE);
        lv_order.setVisibility(View.GONE);
        sp_order.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        sp_order.setOnRefreshListener(this);
        minePeriods = new ArrayList<>();
        lv_order.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv_order.setLoadMoreListener(this);
        periodAdapter = new MinePeriodAdapter(getActivity(), minePeriods, R.layout.item_yuye);
        lv_order.setAdapter(periodAdapter);
        order();
        initBus();
    }

    private void initBus(){
        observable = RxBus.get().register("orderState", RxMsg.class);
        Subscriber<RxMsg> subscriber = new Subscriber<RxMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxMsg rxMsg) {
                onRefresh();
            }
        };
        observable.subscribe(subscriber);
    }

    private void order(){
        showLoading("加载中");
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(getActivity()).getString("token"));
        params.put("pageNo", pageNo+"");
        params.put("pageSize", pageSize+"");
        params.put("status", status+"");
        presenter.orderList(params);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new OrderListPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        order();
        sp_order.setRefreshing(false);
    }

    @Override
    public void order(Result<Order> result) {
        hideLoading();
        if(result.getData() != null && result.getData().size() > 0){
            empty.setVisibility(View.GONE);
            lv_order.setVisibility(View.VISIBLE);
            minePeriods.clear();
            minePeriods.addAll(result.getData());
            periodAdapter.notifyDataSetChanged();
        } else {
            empty.setVisibility(View.VISIBLE);
            lv_order.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadMore(Result<Order> result) {
        hideLoading();
        lv_order.setLoading(false);
        if(result.getData().size() == 0){

        } else {
            minePeriods.addAll(result.getData());
            periodAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadError(Result<Order> result) {
        hideLoading();
        if(result.getCode() == -1){
            if(status == -1){
                Toast.makeText(CarApplication.getInstance(), "身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
                SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setBoolean("isLogin", false);
                SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("member_id", 0);
                SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("token", " ");
                SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("is_store", 0);
                SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("mobile", " ");
                SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("isCommit", " ");
                RxBusMsg msg = new RxBusMsg();
                msg.setCarType(1);
                msg.setLogin(false);
                RxBus.get().post("rxIsLogin", msg);
                ColpencilFrame.getInstance().finishActivity(getActivity());
                startActivity(new Intent(CarApplication.getInstance(), LoginActivity.class));
            }
        } else {
            empty.setVisibility(View.VISIBLE);
            lv_order.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        order();
    }
}
