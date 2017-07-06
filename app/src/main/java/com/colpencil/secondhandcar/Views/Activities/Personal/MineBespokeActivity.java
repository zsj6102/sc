package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Fragments.Personal.MineCarOrderFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/10.
 * 订单列表
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_bespoke_record)
public class MineBespokeActivity extends ColpencilActivity {

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.vp_order)
    ViewPager vp_order;

    private List<String> titles;
    private int currentPosition = 0;


    @Override
    protected void initViews(View view) {
        tv_title.setText("订单列表");
        currentPosition = getIntent().getExtras().getInt("position");
        titles = new ArrayList<>();
        titles.add("全部");
        titles.add("待付款");
        titles.add("商谈中");
        titles.add("交易完毕");
        titles.add("已转分期");
        for(int i = 0; i < titles.size(); i++){
            tabLayout.addTab(tabLayout.newTab().setText(titles.get(i)));
        }

        vp_order.setAdapter(new OrderViewPagerAdapter(getSupportFragmentManager(), titles));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(vp_order);
        tabLayout.getTabAt(currentPosition).select();

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

    private class OrderViewPagerAdapter extends FragmentPagerAdapter{

        private List<String> title;

        public OrderViewPagerAdapter(FragmentManager fm, List<String> list){
            super(fm);
            this.title = list;
        }

        @Override
        public Fragment getItem(int position) {
            return MineCarOrderFragment.newsInstance(position);
        }

        @Override
        public int getCount() {
            return title.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }
    }
}
