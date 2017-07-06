package com.colpencil.secondhandcar.Views.Activities.Sell;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Fragments.SellCar.SellCarRecordFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/3/30.
 * 卖车列表
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_sell_record)
public class SellCarRecordActivity extends ColpencilActivity {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.vp_sell)
    ViewPager vp_sell;

    private List<String> titles;
    private Observable<RxMsg> observable;
    private SellViewPagerAdapter adapter;
    private int currentPosition = 0;
    private boolean flag = false;

    @Override
    protected void initViews(View view) {
        tv_title.setText("商品列表");
        currentPosition = getIntent().getExtras().getInt("position");
        titles = new ArrayList<>();
        titles.add("销售中");
        titles.add("仓库中");
        titles.add("已售出");
        for(int i = 0; i < titles.size(); i++){
            tabLayout.addTab(tabLayout.newTab().setText(titles.get(i)));
        }
        adapter = new SellViewPagerAdapter(getSupportFragmentManager(), titles);
        vp_sell.setAdapter(adapter);
        tabLayout.setupWithViewPager(vp_sell);
        tabLayout.getTabAt(currentPosition).select();

        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initBus();
    }

    private void initBus(){
        observable = RxBus.get().register("shelves", RxMsg.class);
        Subscriber<RxMsg> subscriber = new Subscriber<RxMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxMsg rxMsg) {
                tabLayout.getTabAt(rxMsg.getTypeId()).select();
                flag = true;
                adapter.notifyDataSetChanged();
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("shelves", observable);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    private class SellViewPagerAdapter extends FragmentPagerAdapter{

        private List<String> title;
        FragmentManager fm;

        public SellViewPagerAdapter(FragmentManager fm, List<String> list_title){
            super(fm);
            this.title = list_title;
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            return SellCarRecordFragment.newsInstance(position);
        }

        @Override
        public int getCount() {
            return title.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //得到缓存的fragment
            Fragment fragment = (Fragment) super.instantiateItem(container,
                    position);
            //得到tag，这点很重要
            String fragmentTag = fragment.getTag();
            if(position == 0 && flag){
                flag = false;
                FragmentTransaction ft = fm.beginTransaction();
                //移除旧的fragment
                ft.remove(fragment);
                //换成新的fragment
                fragment = SellCarRecordFragment.newsInstance(0);
                //添加新fragment时必须用前面获得的tag，这点很重要
                ft.add(container.getId(), fragment, fragmentTag);
                ft.attach(fragment);
                ft.commitNowAllowingStateLoss();
            } else if(position == 1 && flag){
                flag = false;
                FragmentTransaction ft = fm.beginTransaction();
                //移除旧的fragment
                ft.remove(fragment);
                //换成新的fragment
                fragment = SellCarRecordFragment.newsInstance(1);
                //添加新fragment时必须用前面获得的tag，这点很重要
                ft.add(container.getId(), fragment, fragmentTag);
                ft.attach(fragment);
                ft.commitNowAllowingStateLoss();
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }
}
