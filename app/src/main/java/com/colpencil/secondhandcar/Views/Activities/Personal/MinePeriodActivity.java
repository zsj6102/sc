package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.MinePeriod;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Adapter.Mine.MinePeriodAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/10.
 * 我的分期
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_mine_periodization)
public class MinePeriodActivity extends ColpencilActivity implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.swipe_periodization)
    SwipeRefreshLayout sp_period;

    @Bind(R.id.list_periodization)
    ListView lv_period;

    private MinePeriodAdapter periodAdapter;
    private List<MinePeriod> minePeriods;

    @Override
    protected void initViews(View view) {
        tv_title.setText("我的分期");
        sp_period.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        sp_period.setOnRefreshListener(this);

        minePeriods = new ArrayList<>();

        minePeriods.add(new MinePeriod());
        minePeriods.add(new MinePeriod());
        minePeriods.add(new MinePeriod());
        minePeriods.add(new MinePeriod());
        minePeriods.add(new MinePeriod());
        minePeriods.add(new MinePeriod());
        minePeriods.add(new MinePeriod());

//        periodAdapter = new MinePeriodAdapter(this, minePeriods);
//        lv_period.setAdapter(periodAdapter);

        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onRefresh() {
        sp_period.setRefreshing(false);
    }
}
