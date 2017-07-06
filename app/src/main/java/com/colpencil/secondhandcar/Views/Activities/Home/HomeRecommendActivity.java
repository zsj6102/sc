package com.colpencil.secondhandcar.Views.Activities.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.FriendRecommend;
import com.colpencil.secondhandcar.Bean.Response.Home;
import com.colpencil.secondhandcar.Bean.Response.MessageInfo;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.Subscribe;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Present.Home.RecommendPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Activities.Buy.CarDetailsActivity;
import com.colpencil.secondhandcar.Views.Adapter.Home.HomeRecommendAdapter;
import com.colpencil.secondhandcar.Views.Imples.Home.RecommendView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/11.
 * 好车推荐列表
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_car_recommend)
public class HomeRecommendActivity extends ColpencilActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,AutoLoadRecylerView.loadMoreListener, RecommendView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.br_recommend)
    SwipeRefreshLayout br_recommend;

    @Bind(R.id.list_recommend)
    AutoLoadRecylerView lv_recommend;

    @Bind(R.id.layout_bottom)
    LinearLayout ll_bottom;

    @Bind(R.id.rl_data)
    RelativeLayout rl_data;

    @Bind(R.id.layout_empty)
    LinearLayout ll_empty;

    private List<FriendRecommend> carList = new ArrayList<>();
    private HomeRecommendAdapter carAdapter;
    private RecommendPresenter presenter;
    private LinearLayoutManager manager;
    private int pageNo = 1;
    private int pageSize = 10;

    @Override
    protected void initViews(View view) {
        tv_title.setText("好车推荐");
        showLoading("加载中");
        ll_empty.setVisibility(View.GONE);
        rl_data.setVisibility(View.GONE);
        br_recommend.setOnRefreshListener(this);
        br_recommend.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        manager = new LinearLayoutManager(this);
        lv_recommend.setLayoutManager(manager);
        lv_recommend.setLoadMoreListener(this);
        carAdapter = new HomeRecommendAdapter(this, carList, R.layout.item_home_recommend);
        lv_recommend.setAdapter(carAdapter);
        carAdapter.setOnItemClickListener(new HomeRecommendAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                Intent intent = new Intent(HomeRecommendActivity.this, CarDetailsActivity.class);
                intent.putExtra("carId", carList.get(position).getGoods_id());
                startActivity(intent);
            }
        });

        ll_left.setOnClickListener(this);
        presenter.getRecommend(pageNo, pageSize, SharedPreferencesUtil.getInstance(this).getInt("cityId"));
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new RecommendPresenter();
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
    public void refresh(Result<FriendRecommend> result) {
        hideLoading();
        if(result.getData() != null && result.getData().size() > 0){
            rl_data.setVisibility(View.VISIBLE);
            ll_empty.setVisibility(View.GONE);
            carList.clear();
            carList.addAll(result.getData());
            carAdapter.notifyDataSetChanged();
        } else {
            rl_data.setVisibility(View.GONE);
            ll_empty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadError(String message) {
        hideLoading();
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        rl_data.setVisibility(View.GONE);
        ll_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadMore(Result<FriendRecommend> result) {
        hideLoading();
        rl_data.setVisibility(View.VISIBLE);
        ll_empty.setVisibility(View.GONE);
        lv_recommend.setLoading(false);
        if(result.getData().size() == 0){
            ll_bottom.setVisibility(View.VISIBLE);
        } else {
            carList.addAll(result.getData());
            carAdapter.notifyDataSetChanged();
            ll_bottom.setVisibility(View.GONE);
        }
    }

    @Override
    public void homeInfo(ResultInfo<Home> resultInfo) {

    }

    @Override
    public void subscribeNum(Subscribe subscribe) {

    }

    @Override
    public void loadErrorSubscribe(String message) {

    }

    @Override
    public void advCount(Result_comment result_comment) {

    }

    @Override
    public void countError(String message) {

    }

    @Override
    public void popup(ResultInfo<MessageInfo> resultInfo) {

    }

    @Override
    public void popupError(String message) {

    }


    @Override
    public void onRefresh() {
        pageNo = 1;
        presenter.getRecommend(pageNo, pageSize, SharedPreferencesUtil.getInstance(this).getInt("cityId"));
        br_recommend.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        presenter.getRecommend(pageNo, pageSize, SharedPreferencesUtil.getInstance(this).getInt("cityId"));
    }
}
