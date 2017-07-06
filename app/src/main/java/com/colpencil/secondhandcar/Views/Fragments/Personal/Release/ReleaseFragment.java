package com.colpencil.secondhandcar.Views.Fragments.Personal.Release;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.GoodsInfo;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.Present.Sell.GoodsInfoPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Imples.Sell.GoodsInfoView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/18.
 * 发布车辆信息
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_release_car)
public class ReleaseFragment extends ColpencilFragment implements GoodsInfoView{

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.vp_release)
    ViewPager vp_release;

    private List<String> titles;
    public static GoodsInfo goodsInfo;
    private Observable<RxMsg> observable;
    private GoodsInfoPresenter presenter;
    private ReleaseViewPagerAdapter adapter;
    private ReleaseCarFragment releaseCarFragment;
    private ReleaseCarPictureFragment releaseCarPictureFragment;
    private ReleasePeriodFragment releasePeriodFragment;

    @Override
    protected void initViews(View view) {
        tv_title.setText("发布卖车信息");
        iv_back.setVisibility(View.GONE);

        titles = new ArrayList<>();
        releaseCarFragment = new ReleaseCarFragment();
        releaseCarPictureFragment = new ReleaseCarPictureFragment();
        releasePeriodFragment = new ReleasePeriodFragment();
        titles.add("车子信息");
        titles.add("车子图片信息");
        titles.add("是否可分期");
        for (int i = 0; i < titles.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(titles.get(i)));
        }
        adapter = new ReleaseViewPagerAdapter(getChildFragmentManager(), titles);
        vp_release.setAdapter(adapter);
        vp_release.setOffscreenPageLimit(3);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(vp_release);
        SharedPreferencesUtil.getInstance(getActivity()).setInt("clCity", SharedPreferencesUtil.getInstance(getActivity()).getInt("cityId"));
        SharedPreferencesUtil.getInstance(getActivity()).setInt("cpCity", SharedPreferencesUtil.getInstance(getActivity()).getInt("cityId"));
        SharedPreferencesUtil.getInstance(getActivity()).setInt("clProvince", SharedPreferencesUtil.getInstance(getActivity()).getInt("provinceId"));
        SharedPreferencesUtil.getInstance(getActivity()).setInt("cpProvince", SharedPreferencesUtil.getInstance(getActivity()).getInt("provinceId"));
        initBus();
    }

    /**
     * 初始化event
     */
    private void initBus(){
        observable = RxBus.get().register("change", RxMsg.class);
        Subscriber<RxMsg> subscriber = new Subscriber<RxMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxMsg rxMsg) {
                HashMap<String, String> params = new HashMap<>();
                params.put("goods_id", rxMsg.getCarId()+"");
                params.put("member_id", SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id")+"");
                params.put("token", SharedPreferencesUtil.getInstance(getActivity()).getString("token"));
                presenter.getGoodsInfo(params);
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new GoodsInfoPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void getGoodsInfo(ResultInfo<GoodsInfo> resultInfo) {
        goodsInfo = resultInfo.getData();
        releaseCarFragment.setGoodsInfo(goodsInfo);
        releaseCarPictureFragment.setPictureInfo(goodsInfo);
        releasePeriodFragment.setPeriodInfo(goodsInfo);
    }

    @Override
    public void loadError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private class ReleaseViewPagerAdapter extends FragmentPagerAdapter {

        private List<String> titles;

        public ReleaseViewPagerAdapter(FragmentManager fm, List<String> list) {
            super(fm);
            this.titles = list;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = releaseCarFragment;
                    break;
                case 1:
                    fragment =releaseCarPictureFragment;
                    break;
                case 2:
                    fragment = releasePeriodFragment;
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }
}
