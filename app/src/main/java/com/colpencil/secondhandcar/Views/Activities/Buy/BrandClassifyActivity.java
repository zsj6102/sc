package com.colpencil.secondhandcar.Views.Activities.Buy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.BrandCar;
import com.colpencil.secondhandcar.Bean.Response.BrandList;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Present.Buy.BrandListPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Adapter.Buy.BrandListAdapter;
import com.colpencil.secondhandcar.Views.Fragments.BuyCar.BrandCarFragment;
import com.colpencil.secondhandcar.Views.Imples.Buy.BrandView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/28.
 * 品牌 分类
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_brand_classify)
public class BrandClassifyActivity extends ColpencilActivity implements View.OnClickListener,BrandView{

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.list_classify)
    ListView lv_classify;

    @Bind(R.id.cars_pager)
    ViewPager viewPager;

    private BrandList list = new BrandList();
    private List<BrandCar> carList = new ArrayList<>();
    private CarsAdapter carsAdapter;
    private BrandListAdapter brandAdapter;
    private BrandListPresenter presenter;
    private int type;

    @Override
    protected void initViews(View view) {
        type = getIntent().getIntExtra("type", 0);
        tv_title.setText("品牌选择");
        carsAdapter = new CarsAdapter(getSupportFragmentManager());
        initView();
        presenter.brandList(new HashMap<String, String>());
        ll_left.setOnClickListener(this);
    }


    private void initView(){
        brandAdapter = new BrandListAdapter(this, list);
        lv_classify.setAdapter(brandAdapter);
        brandAdapter.setBrandClickListener(new BrandListAdapter.brandListener() {
            @Override
            public void onBrandClick(int catId, int position) {
                viewPager.setCurrentItem(position);
            }
        });
        lv_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i = 0; i < carList.size(); i++){
                    if(i == position){
                        carList.get(i).setCheck(true);
                    } else {
                        carList.get(i).setCheck(false);
                    }
                }
                brandAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new BrandListPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }

    /**
     * 初始化viewpager
     */
    private void initPager(){
        viewPager.setAdapter(carsAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (viewPager.getCurrentItem() != position)
                    viewPager.setCurrentItem(position,false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void brandList(ResultInfo<BrandList> result) {
        list = result.getData();
        brandAdapter.fillData(list);
        for(int i = 0; i < list.getHot_list().size(); i++){
            carList.add(i, list.getHot_list().get(i));
        }
        for(int j = list.getHot_list().size();j < brandAdapter.mSortList.size() ;j++){
            carList.add(j, brandAdapter.mSortList.get(j));
        }
        brandAdapter.notifyDataSetChanged();
        initPager();
    }

    @Override
    public void loadError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private class CarsAdapter extends FragmentPagerAdapter{

        public CarsAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return BrandCarFragment.newsInstance(carList.get(position).getCat_id(), type);
        }

        @Override
        public int getCount() {
            return list.getHot_list().size()+list.getCat_list().size();
        }
    }
}
