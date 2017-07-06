package com.colpencil.secondhandcar.Views.Activities.Buy.Period;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Bean.Response.CarInfo;
import com.colpencil.secondhandcar.Bean.Response.Goods;
import com.colpencil.secondhandcar.Bean.Response.InstallmentCar;
import com.colpencil.secondhandcar.Bean.Response.PeriodCar;
import com.colpencil.secondhandcar.Bean.Response.PicList;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Home.PeriodCarPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.GlideLoader;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.colpencil.secondhandcar.Views.Activities.Buy.BespokeLookCarActivity;
import com.colpencil.secondhandcar.Views.Activities.Buy.CarDetailsActivity;
import com.colpencil.secondhandcar.Views.Activities.Home.WebViewActivity;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Adapter.Buy.PeriodCarAdapter;
import com.colpencil.secondhandcar.Views.Imples.Home.InstallmentCarView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/13.
 * 分期购车
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_period_buy_car)
public class PeriodBuyCarActivity extends ColpencilActivity implements View.OnClickListener, InstallmentCarView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.list_period)
    ListView lv_period;

    @Bind(R.id.banner)
    Banner banner;

    @Bind(R.id.text_period)
    TextView tv_period;

    @Bind(R.id.img_tag)
    ImageView iv_tag;

    @Bind(R.id.img_car)
    ImageView iv_car;

    @Bind(R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.tv_ma)
    TextView tv_price;

    @Bind(R.id.text_year)
    TextView tv_time;

    @Bind(R.id.tv_statue)
    TextView tv_mileage;

    @Bind(R.id.text_urgent)
    ImageView iv_urgent;

    @Bind(R.id.img_period)
    ImageView iv_period;

    private PeriodCarAdapter buyCarAdapter;
    private List<InstallmentCar> periodCars = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private Intent intent;
    private int carId;
    private Goods goods;
    private double deposit;
    private PeriodCarPresenter presenter;
    private PeriodCar periodCar = new PeriodCar();

    @Override
    protected void initViews(View view) {
        tv_title.setText("分期购车");
        showLoading("加载中");
//        images = getIntent().getStringArrayListExtra("images");
        deposit = getIntent().getDoubleExtra("deposit", 0);
        carId = getIntent().getIntExtra("carId", 0);
        banner.setImageLoader(new GlideLoader());
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                presenter.advCount(periodCar.getAdvList().get(position).getAid());
                if(periodCar.getAdvList().get(position).getAdv_type() == 0){
                    if(periodCar.getAdvList().get(position).getUrl() != null){
                        intent = new Intent(PeriodBuyCarActivity.this, WebViewActivity.class);
                        intent.putExtra("url", periodCar.getAdvList().get(position).getUrl());
                        startActivity(intent);
                    }
                } else {
                    intent = new Intent(PeriodBuyCarActivity.this, CarDetailsActivity.class);
                    intent.putExtra("carId", carId);
                    startActivity(intent);
                }
            }
        });
        buyCarAdapter = new PeriodCarAdapter(this, periodCars, R.layout.item_period_buy_car);
        lv_period.setAdapter(buyCarAdapter);

        presenter.installmentCar(carId);

        ll_left.setOnClickListener(this);
        tv_period.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new PeriodCarPresenter();
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
            case R.id.text_period: //马上预约
                intent = new Intent(this, BespokeLookCarActivity.class);
                CarInfo carInfo = new CarInfo();
                carInfo.setGoods_id(carId);
                List<PicList> lists = new ArrayList<>();
                PicList picList = new PicList();
                picList.setPic(goods.getPic());
                lists.add(picList);
                carInfo.setPicList(lists);
                carInfo.setGoods_name(goods.getName());
                carInfo.setListed_time(goods.getCreate_time());
                carInfo.setMileage(goods.getMileage());
                carInfo.setCity_name(goods.getCity_name());
                carInfo.setPrice(goods.getPrice());
                carInfo.setIs_urgent(goods.getIs_urgent());
                carInfo.setInstallment(goods.getInstallment());
                carInfo.setDeposit(deposit);
                intent.putExtra("good", carInfo);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void installmentCar(ResultInfo<PeriodCar> resultInfo) {
        hideLoading();
        if(resultInfo.getCode() == -1){
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
            ColpencilFrame.getInstance().finishActivity(this);
            startActivity(new Intent(CarApplication.getInstance(), LoginActivity.class));
        } else {
            periodCar = resultInfo.getData();
            goods = resultInfo.getData().getGoods();
            images.clear();
            periodCars.clear();
            for(int i = 0; i < resultInfo.getData().getAdvList().size(); i++){
                images.add(resultInfo.getData().getAdvList().get(i).getAtturl());
            }
            banner.setImages(images);
            banner.isAutoPlay(true);
            banner.start();
            periodCars.addAll(resultInfo.getData().getInsList());
            buyCarAdapter.notifyDataSetChanged();

            if(resultInfo.getData().getGoods().getNew_shelves() == 1){
                iv_tag.setVisibility(View.VISIBLE);
            } else {
                iv_tag.setVisibility(View.GONE);
            }
            Glide.with(this)
                    .load(resultInfo.getData().getGoods().getPic())
                    .into(iv_car);
            tv_name.setText(resultInfo.getData().getGoods().getName());
            tv_price.setText(resultInfo.getData().getGoods().getPrice()+"万");
            tv_time.setText(StringUtils.formatYear(resultInfo.getData().getGoods().getCreate_time()));
            tv_mileage.setText(resultInfo.getData().getGoods().getMileage()+"万公里");
            if(resultInfo.getData().getGoods().getIs_urgent() == 1){
                iv_urgent.setVisibility(View.VISIBLE);
            } else {
                iv_urgent.setVisibility(View.GONE);
            }
            if(resultInfo.getData().getGoods().getInstallment() == 1){
                iv_period.setVisibility(View.VISIBLE);
            } else {
                iv_period.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void loadError(ResultInfo<PeriodCar> resultInfo) {
        hideLoading();
        if(resultInfo.getCode() == -1){
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
            ColpencilFrame.getInstance().finishActivity(this);
            startActivity(new Intent(CarApplication.getInstance(), LoginActivity.class));
        } else {

        }
    }

    @Override
    public void advCount(Result_comment result_comment) {

    }

    @Override
    public void countError(String message) {

    }
}
