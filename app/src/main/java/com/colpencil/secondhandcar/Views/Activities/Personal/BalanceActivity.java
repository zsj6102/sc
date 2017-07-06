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
import com.colpencil.secondhandcar.Views.Fragments.Personal.RepayMentFragment;
import com.colpencil.secondhandcar.Views.Fragments.Personal.RepayMentsFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/23.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_balance)
public class BalanceActivity extends ColpencilActivity {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.vp_sell)
    ViewPager vp_sell;

    private List<String> titles;

    @Override
    protected void initViews(View view) {
        tv_title.setText("分期账单");
        titles = new ArrayList<>();
        titles.add("待还款");
        titles.add("已还款");
        for(int i = 0; i < titles.size(); i++){
            tabLayout.addTab(tabLayout.newTab().setText(titles.get(i)));
        }
        vp_sell.setAdapter(new SellViewPagerAdapter(getSupportFragmentManager(), titles));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(vp_sell);

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

    private class SellViewPagerAdapter extends FragmentPagerAdapter {

        private List<String> title;

        public SellViewPagerAdapter(FragmentManager fm, List<String> list_title){
            super(fm);
            this.title = list_title;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if(position == 0){
                fragment = new RepayMentFragment();
            } else if(position == 1){
                fragment = new RepayMentsFragment();
            }
            return fragment;
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
