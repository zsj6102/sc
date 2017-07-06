package com.colpencil.secondhandcar.Views.Activities.Buy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.Browse;
import com.colpencil.secondhandcar.Bean.Response.CarInfo;
import com.colpencil.secondhandcar.Bean.Response.Collection;
import com.colpencil.secondhandcar.Bean.Response.PicList;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Buy.CarDetailPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.GlideLoader;
import com.colpencil.secondhandcar.Tools.ImagPagerUtil;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.colpencil.secondhandcar.Ui.ScrollView.GradationScrollView;
import com.colpencil.secondhandcar.Views.Activities.Buy.Period.PeriodBuyCarActivity;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Adapter.Buy.CarDetailsAdapter;
import com.colpencil.secondhandcar.Views.Imples.Buy.CarDetailView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/10.
 * 车辆详情
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_car_details)
public class CarDetailsActivity extends ColpencilActivity implements View.OnClickListener, GradationScrollView.ScrollViewListener,CarDetailView{

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.layout_back)
    LinearLayout ll_back;

    @Bind(R.id.iv_good_detail_share)
    ImageView iv_share;

    @Bind(R.id.rl_good_detail)
    RelativeLayout rl_details;

    @Bind(R.id.banner_details)
    Banner banner;

    @Bind(R.id.gs_details)
    GradationScrollView gs_details;

    @Bind(R.id.cl_details)
    ListView lv_details;

    @Bind(R.id.text_tax_price)
    TextView tv_tax_price;

    @Bind(R.id.text_look)
    TextView tv_bespoke;

    @Bind(R.id.text_custom)
    TextView tv_custom;

    @Bind(R.id.rl_period)
    RelativeLayout rl_period;

    @Bind(R.id.text_car_number)
    TextView tv_number;

    @Bind(R.id.text_price)
    TextView tv_price;

    @Bind(R.id.text_car_info)
    TextView tv_info;

    @Bind(R.id.img_sale)
    ImageView iv_sale;

    @Bind(R.id.img_period)
    ImageView iv_period;

    @Bind(R.id.text_earnest)
    TextView tv_earnest;

    @Bind(R.id.text_period_)
    TextView tv_period_;

    @Bind(R.id.text_city)
    TextView tv_city;

    @Bind(R.id.text_transmission)
    TextView tv_transmission;

    @Bind(R.id.text_displacement)
    TextView tv_displacement;

    @Bind(R.id.text_name)
    TextView tv_owner;

    @Bind(R.id.text_address)
    TextView tv_address;

    @Bind(R.id.text_intro)
    TextView tv_intro;

    @Bind(R.id.text_survey)
    TextView tv_survey;

    @Bind(R.id.text_tci)
    TextView tv_tci;

    @Bind(R.id.text_commercial)
    TextView tv_commercial;

    @Bind(R.id.text_qcellcode)
    TextView tv_q;

    @Bind(R.id.text_number)
    TextView tv_change_num;

    @Bind(R.id.text_bill)
    TextView tv_bill;

    @Bind(R.id.text_maintain)
    TextView tv_main;

    @Bind(R.id.text_modify)
    TextView tv_modify;

    @Bind(R.id.text_prove)
    TextView tv_prove;

    @Bind(R.id.text_color)
    TextView tv_color;

    @Bind(R.id.text_emissions)
    TextView tv_;

    @Bind(R.id.text_listed_time)
    TextView tv_emissions;

    @Bind(R.id.text_mileage)
    TextView tv_mileage;

    @Bind(R.id.layout_collection)
    LinearLayout ll_collection;

    @Bind(R.id.layout_depreciate)
    LinearLayout ll_reduce;

    @Bind(R.id.layout_empty)
    LinearLayout ll_empty;

    @Bind(R.id.text_refresh)
    TextView tv_refresh;

    @Bind(R.id.rl_detail)
    RelativeLayout rl_detail;

    @Bind(R.id.text_car_time)
    TextView tv_car_time;

    @Bind(R.id.img_collection)
    ImageView iv_collection;

    @Bind(R.id.text_collection)
    TextView tv_collection;

    @Bind(R.id.text_title)
    TextView tv_title;

    private int height = 0;
    private ArrayList<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private CarDetailsAdapter carDetailsAdapter;
    private ArrayList<PicList> carInfos = new ArrayList<>();
    private CarDetailPresenter presenter;
    private int carId = 0;
    private int isPeriod;
    private boolean isCollection;

    private Intent intent;
    private CarInfo carInfo;

    @Override
    protected void initViews(View view) {
        carId = getIntent().getExtras().getInt("carId");
        showLoading("加载中");
        rl_detail.setVisibility(View.GONE);
        ll_empty.setVisibility(View.GONE);
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        banner.setImageLoader(new GlideLoader());
        banner.isAutoPlay(true);
        carDetailsAdapter = new CarDetailsAdapter(this, carInfos, R.layout.item_car_info);
        lv_details.setAdapter(carDetailsAdapter);

        loadData();
        initListener();
        //设置中划线
        tv_tax_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ImagPagerUtil pagerUtil = new ImagPagerUtil(CarDetailsActivity.this, images);
                pagerUtil.show();
            }
        });
        carDetailsAdapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagPagerUtil pagerUtil = new ImagPagerUtil(CarDetailsActivity.this, images);
                pagerUtil.show();
            }
        });
    }

    private void loadData(){
        HashMap<String, String> params = new HashMap<>();
        if(SharedPreferencesUtil.getInstance(this).getBoolean("isLogin", false)){
            params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
            params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
            params.put("goods_id", carId+"");
        } else {
            params.put("member_id", "");
            params.put("token", "");
            params.put("goods_id", carId+"");
        }
        presenter.carDetail(params);
    }

    /**
     * 初始化监听
     */
    private void initListener(){
        ll_back.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        tv_bespoke.setOnClickListener(this);
        tv_custom.setOnClickListener(this);
        rl_period.setOnClickListener(this);
        ll_collection.setOnClickListener(this);
        ll_reduce.setOnClickListener(this);
        tv_refresh.setOnClickListener(this);

        ViewTreeObserver vto = banner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rl_details.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = 250;
                gs_details.setScrollViewListener(CarDetailsActivity.this);
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CarDetailPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_back: //返回
                finish();
                break;
            case R.id.layout_collection: //收藏
                showLoading("");
                if(SharedPreferencesUtil.getInstance(this).getBoolean("isLogin", false)){
                    HashMap<String, String> params = new HashMap<>();
                    params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
                    params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
                    params.put("goods_id", carId+"");
                    presenter.addCollection(params);
                } else {
                    hideLoading();
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.layout_depreciate: //降价通知
                if(!SharedPreferencesUtil.getInstance(this).getBoolean("isLogin", false)){
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    HashMap<String, String> param = new HashMap<>();
                    param.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
                    param.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
                    param.put("goods_id", carId+"");
                    presenter.addReducePrice(param);
                }
                break;
            case R.id.iv_good_detail_share: //分享
                break;
            case R.id.text_look: //预约看车
                if(SharedPreferencesUtil.getInstance(this).getBoolean("isLogin", false)){
                    if(carInfo.getMarket_enable() == 0){
                        Toast.makeText(this, "该商品已下架", Toast.LENGTH_SHORT).show();
                    } else if(carInfo.getMarket_enable() == 3){
                        Toast.makeText(this, "该商品已售出", Toast.LENGTH_SHORT).show();
                    } else {
                        intent = new Intent(this, BespokeLookCarActivity.class);
                        intent.putExtra("good", carInfo);
                        startActivity(intent);
                    }
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.text_custom: //分期购车
                if(SharedPreferencesUtil.getInstance(this).getBoolean("isLogin", false)){
                    if(isPeriod == 1){
                        if(carInfo.getMarket_enable() == 0){
                            Toast.makeText(this, "该商品已下架", Toast.LENGTH_SHORT).show();
                        } else if(carInfo.getMarket_enable() == 3){
                            Toast.makeText(this, "该商品已售出", Toast.LENGTH_SHORT).show();
                        } else {
                            intent = new Intent(this, PeriodBuyCarActivity.class);
                            intent.putExtra("deposit", carInfo.getDeposit());
                            intent.putExtra("carId", carId);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(this, "该商品不支持分期", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_period: //分期购车
                if(SharedPreferencesUtil.getInstance(this).getBoolean("isLogin", false)){
                    if(carInfo.getMarket_enable() == 0){
                        Toast.makeText(this, "该商品已下架", Toast.LENGTH_SHORT).show();
                    } else if(carInfo.getMarket_enable() == 3){
                        Toast.makeText(this, "该商品已售出", Toast.LENGTH_SHORT).show();
                    } else {
                        intent = new Intent(this, PeriodBuyCarActivity.class);
                        intent.putExtra("deposit", carInfo.getDeposit());
                        intent.putExtra("carId", carId);
                        startActivity(intent);
                    }
                }else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.text_refresh: //刷新
                loadData();
                break;
        }
    }

    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
        ColpencilLogger.e("当前y的值="+y +",height="+height);
        // TODO Auto-generated method stub
        if (y <= 0) {   //设置标题的背景颜色
            rl_details.setBackgroundColor(Color.argb(0, 0, 0, 0));
            iv_back.setImageDrawable(getResources().getDrawable(R.mipmap.back_details));
            iv_share.setImageDrawable(getResources().getDrawable(R.mipmap.share));
            tv_title.setVisibility(View.GONE);
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            rl_details.setBackgroundColor(Color.argb((int) alpha, 200,200,200));
            tv_title.setVisibility(View.GONE);
        } else {    //滑动到banner下面设置普通颜色
            rl_details.setBackgroundColor(Color.argb(255, 255, 255, 255));
            tv_title.setVisibility(View.VISIBLE);
            iv_back.setImageDrawable(getResources().getDrawable(R.mipmap.back_blue));
            iv_share.setImageResource(R.mipmap.share_blue);
        }
    }

    @Override
    public void carInfo(ResultInfo<CarInfo> resultInfo) {
        hideLoading();
        isPeriod = resultInfo.getData().getInstallment();
        ll_empty.setVisibility(View.GONE);
        rl_detail.setVisibility(View.VISIBLE);
        carInfo = resultInfo.getData();
        tv_earnest.setText("订金金额：" + StringUtils.doubleFormat(resultInfo.getData().getDeposit())+"元"); //订金
        tv_city.setText(resultInfo.getData().getCity_name()); //车辆所在城市
        if(resultInfo.getData().getTransmission() == 1){
            tv_transmission.setText("自动"); //变速箱
        } else {
            tv_transmission.setText("手动"); //变速箱
        }
        tv_displacement.setText(resultInfo.getData().getDisplacement()+"升"); //排量
        tv_owner.setText(resultInfo.getData().getOwner()); //车主
        tv_address.setText(resultInfo.getData().getCity_name()); //地址
        tv_intro.setText(resultInfo.getData().getIntro()); //简介
        tv_survey.setText(StringUtils.formatYear(resultInfo.getData().getAnnual_inspection_time())); //年检到期时间
        tv_tci.setText(StringUtils.formatYear(resultInfo.getData().getStrong_risk_time())); //交强险到期时间
        tv_commercial.setText(StringUtils.formatYear(resultInfo.getData().getBusiness_risk_time())); //商业险到期时间
        tv_q.setText(resultInfo.getData().getCity_name()); //归属地
        tv_change_num.setText(resultInfo.getData().getChange_num()+"次"); //过户次数
        if(resultInfo.getData().getIs_invoice() == 1){
            tv_bill.setText("有"); //有无发票
        } else{
            tv_bill.setText("无");
        }
        if(resultInfo.getData().getIs_four_s() == 1){
            tv_main.setText("是"); //是否4s店保养
        } else {
            tv_main.setText("否");
        }
        if(resultInfo.getData().getIs_modified() == 1){
            tv_modify.setText("有"); // 有无改装
        } else {
            tv_modify.setText("无");
        }
        if(resultInfo.getData().getIs_tax() == 1){
            tv_prove.setText("有"); //车辆购置税
        } else {
            tv_prove.setText("无"); //车辆购置税
        }
        if(resultInfo.getData().getColor() == 1){
            tv_color.setText("黑色"); //颜色
        } else if(resultInfo.getData().getColor() == 2){
            tv_color.setText("白色"); //颜色
        } else if(resultInfo.getData().getColor() == 3){
            tv_color.setText("银灰色"); //颜色
        } else if(resultInfo.getData().getColor() == 4){
            tv_color.setText("深灰色"); //颜色
        } else if(resultInfo.getData().getColor() == 5){
            tv_color.setText("红色"); //颜色
        } else if(resultInfo.getData().getColor() == 6){
            tv_color.setText("橙色"); //颜色
        } else if(resultInfo.getData().getColor() == 7){
            tv_color.setText("绿色"); //颜色
        } else if(resultInfo.getData().getColor() == 8){
            tv_color.setText("蓝色"); //颜色
        } else if(resultInfo.getData().getColor() == 9){
            tv_color.setText("咖啡色"); //颜色
        } else if(resultInfo.getData().getColor() == 10){
            tv_color.setText("紫色"); //颜色
        } else if(resultInfo.getData().getColor() == 11){
            tv_color.setText("香槟色"); //颜色
        } else if(resultInfo.getData().getColor() == 12){
            tv_color.setText("多彩色"); //颜色
        } else if(resultInfo.getData().getColor() == 13){
            tv_color.setText("黄色"); //颜色
        } else if(resultInfo.getData().getColor() == 14){
            tv_color.setText("其他"); //颜色
        }
        if(resultInfo.getData().getEmissions() == 1){
            tv_emissions.setText("国二");//排放标准
        } else if(resultInfo.getData().getEmissions() == 2){
            tv_emissions.setText("国三");//排放标准
        } else if(resultInfo.getData().getEmissions() == 3){
            tv_emissions.setText("国四");//排放标准
        } else if(resultInfo.getData().getEmissions() == 4){
            tv_emissions.setText("国五");//排放标准
        }
        tv_car_time.setText(StringUtils.formatDate(resultInfo.getData().getListed_time()));
        tv_mileage.setText(resultInfo.getData().getMileage()+"万公里");
        for(int i = 0; i < resultInfo.getData().getPicList().size(); i++){
            if(resultInfo.getData().getPicList().get(i).getPic_type() == 7){
                for(int j = 0; j < resultInfo.getData().getPicList().get(i).getOther_pic().size(); j++){
                    images.add(resultInfo.getData().getPicList().get(i).getOther_pic().get(j));
                }
            } else {
                images.add(resultInfo.getData().getPicList().get(i).getPic());
            }
        }
        tv_number.setText(resultInfo.getData().getSn());
        for(int j = 0; j < images.size(); j++){
            titles.add("");
        }
        banner.setImages(images);
        banner.setBannerTitles(titles);
        banner.start();

        tv_tax_price.setText(resultInfo.getData().getCost()+""); //新车含税
        tv_price.setText(resultInfo.getData().getPrice()+""); //价格
        tv_info.setText(resultInfo.getData().getGoods_name()); //车辆名称
        //是否急售
        if(resultInfo.getData().getIs_urgent() == 1){
            iv_sale.setVisibility(View.VISIBLE);
        } else {
            iv_sale.setVisibility(View.GONE);
        }
        //是否分期
        if(resultInfo.getData().getInstallment() == 1){
            iv_period.setVisibility(View.VISIBLE);
            rl_period.setVisibility(View.VISIBLE);
            tv_period_.setText(resultInfo.getData().getIns_describe()); //可支持的分期
        } else {
            iv_period.setVisibility(View.GONE);
            rl_period.setVisibility(View.GONE);
        }
        if(resultInfo.getData().getIs_collection() == 1){ //已收藏
            isCollection = true;
            iv_collection.setImageResource(R.mipmap.collection_check);
            tv_collection.setText("已收藏");
        } else {
            isCollection = false;
            iv_collection.setImageResource(R.mipmap.love_heart);
            tv_collection.setText("收藏");
        }
        //详情列表
        carInfos.clear();
        carInfos.addAll(resultInfo.getData().getPicList());
        carDetailsAdapter.notifyDataSetChanged();
        //添加浏览记录
        if(SharedPreferencesUtil.getInstance(this).getBoolean("isLogin", false)){
            HashMap<String, String> params = new HashMap<>();
            params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
            params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
            params.put("goods_id", resultInfo.getData().getGoods_id()+"");
            presenter.addBrowse(params);
        }
    }

    @Override
    public void loadError(ResultInfo<CarInfo> resultInfo) {
        hideLoading();
        ll_empty.setVisibility(View.VISIBLE);
        rl_detail.setVisibility(View.GONE);
    }

    @Override
    public void addBrowse(Browse browse) {

    }

    @Override
    public void addBrowseError(String message) {
    }

    @Override
    public void addCollection(Collection collection) {
        hideLoading();
        if(collection.getCode() == 1){
            if(isCollection){
                iv_collection.setImageResource(R.mipmap.love_heart);
                tv_collection.setText("收藏");
                isCollection = false;
            } else {
                iv_collection.setImageResource(R.mipmap.collection_check);
                tv_collection.setText("已收藏");
                isCollection = true;
            }
        } else {
            iv_collection.setImageResource(R.mipmap.love_heart);
            tv_collection.setText("收藏");
        }
        RxMsg msg = new RxMsg();
        RxBus.get().post("collection", msg);
    }

    @Override
    public void addCollectionError(Collection collection) {
        hideLoading();
        if(collection.getCode() == -1){
            Toast.makeText(CarApplication.getInstance(), "身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setBoolean("isLogin", false);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("member_id", 0);
            SharedPreferencesUtil.getInstance(this).setString("isCommit", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("token", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("is_store", 0);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("mobile", " ");
            RxBusMsg msg = new RxBusMsg();
            msg.setCarType(1);
            msg.setLogin(false);
            RxBus.get().post("rxIsLogin", msg);
            startActivity(new Intent(CarApplication.getInstance(), LoginActivity.class));
        } else {
            iv_collection.setImageResource(R.mipmap.love_heart);
            tv_collection.setText("收藏");
        }
    }

    @Override
    public void addReducePrice(Result_comment result_comment) {
       Toast.makeText(this, result_comment.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addReducePriceError(Result_comment result_comment) {
        if(result_comment.getCode() == -1){
            Toast.makeText(CarApplication.getInstance(), "身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setBoolean("isLogin", false);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("member_id", 0);
            SharedPreferencesUtil.getInstance(this).setString("isCommit", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("token", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("is_store", 0);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("mobile", " ");
            RxBusMsg msg = new RxBusMsg();
            msg.setCarType(1);
            msg.setLogin(false);
            RxBus.get().post("rxIsLogin", msg);
            startActivity(new Intent(CarApplication.getInstance(), LoginActivity.class));
        } else {
            Toast.makeText(this, result_comment.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
