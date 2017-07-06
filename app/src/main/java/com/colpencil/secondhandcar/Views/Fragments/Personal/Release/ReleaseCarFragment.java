package com.colpencil.secondhandcar.Views.Fragments.Personal.Release;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.GoodsInfo;
import com.colpencil.secondhandcar.Bean.Response.Province;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxBrandMsg;
import com.colpencil.secondhandcar.Bean.RxCityMsg;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.Present.Sell.SellProvincePresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.colpencil.secondhandcar.Views.Activities.Buy.BrandClassifyActivity;
import com.colpencil.secondhandcar.Views.Activities.Home.CityChooiceActivity;
import com.colpencil.secondhandcar.Views.Imples.Sell.SellProvinceView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.weitu.mylibrary.OptionsPickerView;
import com.weitu.mylibrary.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/18.
 * 发布车辆fragment
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_release_car)
public class ReleaseCarFragment extends ColpencilFragment implements View.OnClickListener, SellProvinceView{

    @Bind(R.id.layout_type)
    LinearLayout ll_type;

    @Bind(R.id.text_type)
    TextView tv_type;

    @Bind(R.id.et_price)
    EditText et_price;

    @Bind(R.id.et_revenue)
    EditText et_revenue;

    @Bind(R.id.et_name)
    EditText et_name;

    @Bind(R.id.et_address)
    TextView tv_area;

    @Bind(R.id.layout_info)
    LinearLayout ll_info;

    @Bind(R.id.text_info)
    TextView tv_info;

    @Bind(R.id.layout_urgent)
    LinearLayout ll_urgent;

    @Bind(R.id.text_urgent)
    TextView tv_urgent;

    @Bind(R.id.et_km)
    EditText et_km;

    @Bind(R.id.layout_local)
    LinearLayout ll_local;

    @Bind(R.id.text_local)
    TextView tv_local;

    @Bind(R.id.layout_start_time)
    LinearLayout ll_s_time;

    @Bind(R.id.text_start_time)
    TextView tv_s_time;

    @Bind(R.id.layout_color)
    LinearLayout ll_color;

    @Bind(R.id.text_color)
    TextView tv_color;

    @Bind(R.id.layout_survey)
    LinearLayout ll_survey;

    @Bind(R.id.text_survey)
    TextView tv_survey;

    @Bind(R.id.layout_safe)
    LinearLayout ll_safe;

    @Bind(R.id.text_safe)
    TextView tv_safe;

    @Bind(R.id.layout_commercial)
    LinearLayout ll_commercial;

    @Bind(R.id.text_commercial)
    TextView tv_commercial;

    @Bind(R.id.text_area)
    TextView tv_address;

    @Bind(R.id.rl_area)
    RelativeLayout rl_address;

    @Bind(R.id.rl_address)
    RelativeLayout rl_area;

    @Bind(R.id.text_num)
    TextView tv_num;

    @Bind(R.id.text_bill)
    TextView tv_bill;

    @Bind(R.id.text_care)
    TextView tv_care;

    @Bind(R.id.text_modify)
    TextView tv_modify;

    @Bind(R.id.text_attest)
    TextView tv_attest;

    @Bind(R.id.et_info)
    EditText et_info;

    private Intent intent;
    private Observable<RxCityMsg> cityMsgObservable;
    private Observable<RxBrandMsg> brandMsgObservable;
    private Observable<RxCityMsg> carObservable;
    private Observable<RxMsg> observable;
    private TimePickerView timePickerView;
    private TimePickerView surveyPickerView;
    private TimePickerView safePickerView;
    private TimePickerView commercialPickerView;
    private OptionsPickerView carInfoOptionView;
    private OptionsPickerView urgentOptionView;
    private OptionsPickerView localOptionView;
    private OptionsPickerView colorOptionView;
    private OptionsPickerView numOptionView;
    private OptionsPickerView billOptionView;
    private OptionsPickerView careOptionView;
    private OptionsPickerView modifyOptionView;
    private OptionsPickerView attestOptionView;
    private List<String> carInfo = new ArrayList<>();
    private List<String> urgent = new ArrayList<>();
    private List<String> color = new ArrayList<>();
    private List<String> nums = new ArrayList<>();
    private List<String> bills = new ArrayList<>();

    private SellProvincePresenter presenter;
    private int type = 0;
    private GoodsInfo goodsInfo;

    @Override
    protected void initViews(View view) {
        tv_address.setText(SharedPreferencesUtil.getInstance(getActivity()).getString("cityName").substring(0, SharedPreferencesUtil.getInstance(getActivity()).getString("cityName").length() -1));
        tv_area.setText(SharedPreferencesUtil.getInstance(getActivity()).getString("cityName").substring(0, SharedPreferencesUtil.getInstance(getActivity()).getString("cityName").length() -1));
        initListener();
        initTimeData();
        initOptionView();
        getEditData();
        initBus();
    }

    public void setGoodsInfo(GoodsInfo goodsInfo){
        this.goodsInfo = goodsInfo;
        getGoodsInfo();
    }

    /**
     * 获取编辑框数据
     */
    private void getEditData(){
        et_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("goodPrice", s.toString());
            }
        });
        et_revenue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("goodRevenue", s.toString());
            }
        });
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("name", s.toString());
            }
        });
        et_km.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("goodKm", s.toString());
            }
        });
        et_info.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("carInfo", s.toString());
            }
        });
    }

    /**
     * 初始化OptionView
     */
    private void initOptionView(){
        carInfo.add("车况好，没有任何事故");
        carInfo.add("有过少量刮蹭或钣金");
        carInfo.add("有发生过上级骑车主体框架的碰撞事故");
        carInfo.add("有水泡或者火烧历史");
        urgent.add("是");
        urgent.add("否");
        color.add("黑色");
        color.add("白色");
        color.add("银灰色");
        color.add("深灰色");
        color.add("红色");
        color.add("蓝色");
        color.add("橙色");
        color.add("黄色");
        color.add("紫色");
        color.add("绿色");
        color.add("咖啡色");
        color.add("香槟色");
        color.add("多彩色");
        color.add("其他");
        nums.add("0次");
        nums.add("1次");
        nums.add("2次");
        nums.add("3次");
        nums.add("4次");
        nums.add("5次");
        nums.add("5次以上");
        bills.add("有");
        bills.add("无");
        carInfoOptionView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener(){
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("carInfo", carInfo.get(options1));
                tv_info.setText(carInfo.get(options1));
            }
        }).build();
        carInfoOptionView.setPicker(carInfo);
        urgentOptionView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener(){
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("urgent", urgent.get(options1));
                tv_urgent.setText(urgent.get(options1));
            }
        }).build();
        urgentOptionView.setPicker(urgent);
        localOptionView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener(){
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("local", urgent.get(options1));
                tv_local.setText(urgent.get(options1));
            }
        }).build();
        localOptionView.setPicker(urgent);
        colorOptionView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener(){
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("color", color.get(options1));
                tv_color.setText(color.get(options1));
            }
        }).build();
        colorOptionView.setPicker(color);
        numOptionView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener(){
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("ghNumber", nums.get(options1).substring(0,1));
                tv_num.setText(nums.get(options1));
            }
        }).build();
        numOptionView.setPicker(nums);
        billOptionView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener(){
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("bill", bills.get(options1));
                tv_bill.setText(bills.get(options1));
            }
        }).build();
        billOptionView.setPicker(bills);
        careOptionView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener(){
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("care", urgent.get(options1));
                tv_care.setText(urgent.get(options1));
            }
        }).build();
        careOptionView.setPicker(urgent);
        modifyOptionView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener(){
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("modify", bills.get(options1));
                tv_modify.setText(bills.get(options1));
            }
        }).build();
        modifyOptionView.setPicker(bills);
        attestOptionView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener(){
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("attest", bills.get(options1));
                tv_attest.setText(bills.get(options1));
            }
        }).build();
        attestOptionView.setPicker(bills);
    }

    /**
     * 初始化event
     */
    private void initBus(){
        carObservable = RxBus.get().register("carPaiCity", RxCityMsg.class);
        Subscriber<RxCityMsg> subscriberCar = new Subscriber<RxCityMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(RxCityMsg rxCityMsg) {
                tv_area.setText(rxCityMsg.getCity());
                presenter.getProvinceId(rxCityMsg.getCity());
            }
        };
        carObservable.subscribe(subscriberCar);
        cityMsgObservable = RxBus.get().register("carCity", RxCityMsg.class);
        Subscriber<RxCityMsg> subscriber = new Subscriber<RxCityMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxCityMsg rxCityMsg) {
                tv_address.setText(rxCityMsg.getCity());
                presenter.getProvinceId(rxCityMsg.getCity());
            }
        };
        cityMsgObservable.subscribe(subscriber);
        brandMsgObservable = RxBus.get().register("rxBrand", RxBrandMsg.class);
        Subscriber<RxBrandMsg> brandMsgSubscriber = new Subscriber<RxBrandMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBrandMsg rxBrandMsg) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("carName", rxBrandMsg.getCatName());
                SharedPreferencesUtil.getInstance(getActivity()).setInt("sellCarId", rxBrandMsg.getCatId());
                tv_type.setText(rxBrandMsg.getCatName());
            }
        };
        brandMsgObservable.subscribe(brandMsgSubscriber);
        observable = RxBus.get().register("release", RxMsg.class);
        Subscriber<RxMsg> msgSubscriber = new Subscriber<RxMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxMsg rxMsg) {
                tv_type.setText("请选择");
                et_price.setText("");
                et_revenue.setText("");
                et_name.setText("");
                tv_area.setText(SharedPreferencesUtil.getInstance(getActivity()).getString("cityName"));
                tv_address.setText(SharedPreferencesUtil.getInstance(getActivity()).getString("cityName"));
                tv_info.setText("请选择");
                tv_urgent.setText("请选择");
                et_km.setText("");
                tv_local.setText("请选择");
                tv_s_time.setText("请选择");
                tv_color.setText("请选择");
                tv_survey.setText("请选择");
                tv_safe.setText("请选择");
                tv_commercial.setText("请选择");
                tv_num.setText("请选择");
                tv_bill.setText("请选择");
                tv_care.setText("请选择");
                tv_modify.setText("请选择");
                tv_attest.setText("请选择");
                et_info.setText("");
            }
        };
        observable.subscribe(msgSubscriber);
    }

    /**
     * 获取商品信息
     */
    private void getGoodsInfo(){
        SharedPreferencesUtil.getInstance(getActivity()).setInt("clCity", goodsInfo.getCity_id());
        SharedPreferencesUtil.getInstance(getActivity()).setInt("clProvince", goodsInfo.getPrivince_id());
        SharedPreferencesUtil.getInstance(getActivity()).setInt("cpCity", goodsInfo.getCard_city_id());
        SharedPreferencesUtil.getInstance(getActivity()).setInt("cpProvince", goodsInfo.getCard_privince_id());
        tv_type.setText(goodsInfo.getGoods_name());
        et_price.setText(goodsInfo.getPrice()+"");
        et_revenue.setText(goodsInfo.getCost()+"");
        et_name.setText(goodsInfo.getOwner());
        tv_area.setText(goodsInfo.getCity_name());
        tv_address.setText(goodsInfo.getCard_city_name());
        tv_info.setText(goodsInfo.getSituation());
        if(goodsInfo.getIs_urgent() == 1){
            tv_urgent.setText("是");
        } else {
            tv_urgent.setText("否");
        }
        et_km.setText(goodsInfo.getMileage()+"");
        if(goodsInfo.getIs_local_license_plate() == 1){
            tv_local.setText("是");
        } else {
            tv_local.setText("否");
        }
        tv_s_time.setText(StringUtils.formatDate(goodsInfo.getListed_time()));
        if(goodsInfo.getColor() == 1){
            tv_color.setText("黑色");
        } else if(goodsInfo.getColor() == 2){
            tv_color.setText("白色");
        } else if(goodsInfo.getColor() == 3){
            tv_color.setText("银灰色");
        } else if(goodsInfo.getColor() == 4){
            tv_color.setText("深灰色");
        } else if(goodsInfo.getColor() == 5){
            tv_color.setText("红色");
        } else if(goodsInfo.getColor() == 6){
            tv_color.setText("橙色");
        } else if(goodsInfo.getColor() == 7){
            tv_color.setText("绿色");
        } else if(goodsInfo.getColor() == 8){
            tv_color.setText("蓝色");
        } else if(goodsInfo.getColor() == 9){
            tv_color.setText("咖啡色");
        } else if(goodsInfo.getColor() == 10){
            tv_color.setText("紫色");
        } else if(goodsInfo.getColor() == 11){
            tv_color.setText("香槟色");
        } else if(goodsInfo.getColor() == 12){
            tv_color.setText("多彩色");
        } else if(goodsInfo.getColor() == 13){
            tv_color.setText("黄色");
        } else if(goodsInfo.getColor() == 14){
            tv_color.setText("其他");
        }
        tv_survey.setText(StringUtils.formatDate(goodsInfo.getAnnual_inspection_time()));
        tv_safe.setText(StringUtils.formatDate(goodsInfo.getStrong_risk_time()));
        tv_commercial.setText(StringUtils.formatDate(goodsInfo.getBusiness_risk_time()));
        tv_num.setText(goodsInfo.getChange_num()+"次");
        if(goodsInfo.getIs_invoice() == 1){
            tv_bill.setText("有");
        } else {
            tv_bill.setText("无");
        }
        if(goodsInfo.getIs_four_s() == 1){
            tv_care.setText("是");
        } else {
            tv_care.setText("否");
        }
        if(goodsInfo.getIs_modified() == 1){
            tv_modify.setText("有");
        } else {
            tv_modify.setText("无");
        }
        if(goodsInfo.getIs_tax() == 1){
            tv_attest.setText("是");
        } else {
            tv_attest.setText("无");
        }
        et_info.setText(goodsInfo.getIntro());
    }

    /**
     * 初始化监听
     */
    private void initListener(){
        ll_type.setOnClickListener(this);
        ll_info.setOnClickListener(this);
        ll_urgent.setOnClickListener(this);
        ll_local.setOnClickListener(this);
        ll_s_time.setOnClickListener(this);
        ll_color.setOnClickListener(this);
        ll_survey.setOnClickListener(this);
        ll_safe.setOnClickListener(this);
        ll_commercial.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        tv_num.setOnClickListener(this);
        tv_bill.setOnClickListener(this);
        tv_care.setOnClickListener(this);
        tv_modify.setOnClickListener(this);
        tv_attest.setOnClickListener(this);
        rl_area.setOnClickListener(this);
    }

    /**
     * 初始化时间选择器
     */
    private void initTimeData(){
        timePickerView = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("startTime", date.getTime()+"");
                tv_s_time.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
            }
        })      .setCancelText("取消")
                .setSubmitText("确定")
                .setContentSize(18)
                .setTitleSize(20)
                .setOutSideCancelable(true)
                .setType(new boolean[]{true, true, true, false, false, false})
                .build();
        surveyPickerView = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("surveyTime", date.getTime()+"");
                tv_survey.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
            }
        })      .setCancelText("取消")
                .setSubmitText("确定")
                .setContentSize(18)
                .setTitleSize(20)
                .setOutSideCancelable(true)
                .setType(new boolean[]{true, true, true, false, false, false})
                .build();
        safePickerView = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("safeTime", date.getTime()+"");
                tv_safe.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
            }
        })      .setCancelText("取消")
                .setSubmitText("确定")
                .setContentSize(18)
                .setTitleSize(20)
                .setOutSideCancelable(true)
                .setType(new boolean[]{true, true, true, false, false, false})
                .build();
        commercialPickerView = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("commercialTime", date.getTime()+"");
                tv_commercial.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
            }
        })      .setCancelText("取消")
                .setSubmitText("确定")
                .setContentSize(18)
                .setTitleSize(20)
                .setOutSideCancelable(true)
                .setType(new boolean[]{true, true, true, false, false, false})
                .build();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new SellProvincePresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_type: //品牌车系
                intent = new Intent(getActivity(), BrandClassifyActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.layout_info: //车况
                carInfoOptionView.show();
                break;
            case R.id.layout_urgent: //是否急售
                urgentOptionView.show();
                break;
            case R.id.layout_local: //是否当地车牌
                localOptionView.show();
                break;
            case R.id.layout_start_time: //初次上牌时间
                timePickerView.show();
                break;
            case R.id.layout_color: //车身颜色
                colorOptionView.show();
                break;
            case R.id.layout_survey: //年检到期时间
                surveyPickerView.show();
                break;
            case R.id.layout_safe: //交强险时间
                safePickerView.show();
                break;
            case R.id.layout_commercial: //商业险到期时间
                commercialPickerView.show();
                break;
            case R.id.rl_area: //车辆所在地
                type = 0;
                intent = new Intent(getActivity(), CityChooiceActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.rl_address: //车牌所在地
                type = 1;
                intent = new Intent(getActivity(), CityChooiceActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.text_num: //过户次数
                numOptionView.show();
                break;
            case R.id.text_bill: //有无发票
                billOptionView.show();
                break;
            case R.id.text_care: //是否4s店保养
                careOptionView.show();
                break;
            case R.id.text_modify: //有无改装
                modifyOptionView.show();
                break;
            case R.id.text_attest: //车税证明
                attestOptionView.show();
                break;
        }
    }

    @Override
    public void getProvinceId(ResultInfo<Province> resultInfo) {
        if(type == 0){
            SharedPreferencesUtil.getInstance(getActivity()).setInt("clCity", resultInfo.getData().getCity_id());
            SharedPreferencesUtil.getInstance(getActivity()).setInt("clProvince", resultInfo.getData().getRegion_id());
        } else if(type == 1){
            SharedPreferencesUtil.getInstance(getActivity()).setInt("cpCity", resultInfo.getData().getCity_id());
            SharedPreferencesUtil.getInstance(getActivity()).setInt("cpProvince", resultInfo.getData().getRegion_id());
        }
    }

    @Override
    public void getError(String message) {

    }
}
