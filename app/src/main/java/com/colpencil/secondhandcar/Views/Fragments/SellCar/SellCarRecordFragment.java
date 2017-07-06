package com.colpencil.secondhandcar.Views.Fragments.SellCar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.SellCarRecord;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Sell.StoreGoodsPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Ui.AlertDialog;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Adapter.Sell.SellCarRecordAdapter;
import com.colpencil.secondhandcar.Views.Imples.Sell.StoreGoodsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/30.
 * 卖车列表数据列表
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_sell_vp_record)
public class SellCarRecordFragment extends ColpencilFragment implements SwipeRefreshLayout.OnRefreshListener,StoreGoodsView, AutoLoadRecylerView.loadMoreListener{

    @Bind(R.id.list_sell)
    AutoLoadRecylerView lv_sell;

    @Bind(R.id.swipe_record)
    SwipeRefreshLayout sp_record;

    @Bind(R.id.layout_empty)
    LinearLayout empty;

    private SellCarRecordAdapter recordAdapter;
    private List<SellCarRecord> records;
    private StoreGoodsPresenter presenter;

    private int type;
    private int enable;
    private int pageNo = 1;
    private int pageSize = 10;
    private int market_anable = 0;

    public static SellCarRecordFragment newsInstance(int type){
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        SellCarRecordFragment fragment = new SellCarRecordFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        type = getArguments().getInt("type");
        if(type == 0){
            enable = 1;
        } else if(type == 1){
            enable = 0;
        } else if(type == 2){
            enable = 3;
        }
        showLoading("加载中");
        lv_sell.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        sp_record.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        sp_record.setOnRefreshListener(this);
        records = new ArrayList<>();
        lv_sell.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv_sell.setLoadMoreListener(this);
        recordAdapter = new SellCarRecordAdapter(getActivity(), records, R.layout.item_sell_record_list, type);
        lv_sell.setAdapter(recordAdapter);
        recordAdapter.setUpListener(new SellCarRecordAdapter.ClickListener() {
            @Override
            public void click(final int position) {
                new AlertDialog(getActivity())
                        .builder()
                        .setCancelable(false)
                        .setMsg("是否确定上架该商品")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                market_anable = 1;
                                HashMap<String, String> params = new HashMap<>();
                                params.put("goods_id", records.get(position).getGoods_id()+"");
                                params.put("member_id", SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id")+"");
                                params.put("token", SharedPreferencesUtil.getInstance(getActivity()).getString("token"));
                                params.put("market_enable", market_anable+"");
                                presenter.shelves(params);
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
            }
        });
        recordAdapter.setDownListener(new SellCarRecordAdapter.ClickListener() {
            @Override
            public void click(final int position) {
                new AlertDialog(getActivity())
                        .builder()
                        .setCancelable(false)
                        .setMsg("是否确定下架该商品")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                market_anable = 0;
                                HashMap<String, String> params = new HashMap<>();
                                params.put("goods_id", records.get(position).getGoods_id()+"");
                                params.put("member_id", SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id")+"");
                                params.put("token", SharedPreferencesUtil.getInstance(getActivity()).getString("token"));
                                params.put("market_enable", market_anable+"");
                                presenter.shelves(params);
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
            }
        });
        loadRecord();
    }

    /**
     * 请求数据
     */
    private void loadRecord(){
        HashMap<String, String> params = new HashMap<>();
        params.put("store_id", SharedPreferencesUtil.getInstance(getActivity()).getInt("store_id")+"");
        params.put("pageNo", pageNo+"");
        params.put("pageSize", pageSize+"");
        params.put("market_enable", enable+"");
        params.put("member_id", SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(getActivity()).getString("token"));
        presenter.storeGoods(params);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new StoreGoodsPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        loadRecord();
        sp_record.setRefreshing(false);
    }

    @Override
    public void storeGoods(Result<SellCarRecord> result) {
        hideLoading();
        if(result.getData() != null && result.getData().size() > 0){
            empty.setVisibility(View.GONE);
            lv_sell.setVisibility(View.VISIBLE);
            records.clear();
            records.addAll(result.getData());
            recordAdapter.notifyDataSetChanged();
        } else {
            empty.setVisibility(View.VISIBLE);
            lv_sell.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadMore(Result<SellCarRecord> result) {
        hideLoading();
        lv_sell.setLoading(false);
        if(result.getData().size() == 0){

        } else {
            records.addAll(result.getData());
            recordAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadError(Result<SellCarRecord> result) {
        hideLoading();
        if(result.getCode() == -1){
            if(enable == 1){
                Toast.makeText(CarApplication.getInstance(), "身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
                SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setBoolean("isLogin", false);
                SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("member_id", 0);
                SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("token", " ");
                SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("is_store", 0);
                SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("mobile", " ");
                SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setBoolean("isLogin", false);
                RxBusMsg msg = new RxBusMsg();
                msg.setCarType(1);
                msg.setLogin(false);
                RxBus.get().post("rxIsLogin", msg);
                ColpencilFrame.getInstance().finishActivity(getActivity());
                startActivity(new Intent(CarApplication.getInstance(), LoginActivity.class));
            }
        } else {
            empty.setVisibility(View.VISIBLE);
            lv_sell.setVisibility(View.GONE);
        }
    }

    @Override
    public void success(Result_comment result_comment) {
        Toast.makeText(getActivity(), result_comment.getMessage(), Toast.LENGTH_SHORT).show();
        onRefresh();
        RxMsg msg = new RxMsg();
        if(market_anable == 1){
            msg.setTypeId(0);
        } else if(market_anable == 0){
            msg.setTypeId(1);
        }
        RxBus.get().post("shelves", msg);
    }

    @Override
    public void faile(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        loadRecord();
    }
}
