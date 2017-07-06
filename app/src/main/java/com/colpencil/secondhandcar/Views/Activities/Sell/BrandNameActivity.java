package com.colpencil.secondhandcar.Views.Activities.Sell;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.BrandCar;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.RxBrandMsg;
import com.colpencil.secondhandcar.Present.Buy.CarPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Activities.Buy.BrandClassifyActivity;
import com.colpencil.secondhandcar.Views.Adapter.Buy.BrandTwoCarAdapter;
import com.colpencil.secondhandcar.Views.Imples.Buy.CarView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/26.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_brand_name)
public class BrandNameActivity extends ColpencilActivity implements SwipeRefreshLayout.OnRefreshListener, CarView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.sp)
    SwipeRefreshLayout sp;

    @Bind(R.id.lv_brand)
    ListView lv_brand;

    @Bind(R.id.empty)
    LinearLayout empty;

    private int catId;
    private CarPresenter presenter;
    private BrandTwoCarAdapter adapter;
    private List<BrandCar> list = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        catId = getIntent().getIntExtra("catId", 0);
        tv_title.setText("品牌车系");
        showLoading("加载中");
        lv_brand.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        sp.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        sp.setOnRefreshListener(this);
        adapter = new BrandTwoCarAdapter(this, list, R.layout.item_brand_two_car);
        lv_brand.setAdapter(adapter);
        lv_brand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RxBrandMsg msg = new RxBrandMsg();
                msg.setCatId(list.get(position).getCat_id());
                msg.setCatName(list.get(position).getCat_name());
                msg.setTypeId(list.get(position).getType_id());
                msg.setTypeName(list.get(position).getType_name());
                RxBus.get().post("brandName", msg);
                ColpencilFrame.getInstance().finishActivity(BrandNameActivity.this);
                ColpencilFrame.getInstance().finishActivity(BrandClassifyActivity.class);
            }
        });
        getCarName();
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getCarName() {
        HashMap<String, String> params = new HashMap<>();
        params.put("cat_id", catId + "");
        presenter.carList(params);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CarPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onRefresh() {
        getCarName();
        sp.setRefreshing(false);
    }

    @Override
    public void carList(Result<BrandCar> result) {
        hideLoading();
        if(result.getData() != null && result.getData().size() > 0){
            lv_brand.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            list.clear();
            list.addAll(result.getData());
            adapter.notifyDataSetChanged();
        } else {
            lv_brand.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadError(String message) {
        hideLoading();
        lv_brand.setVisibility(View.GONE);
        empty.setVisibility(View.VISIBLE);
    }
}
