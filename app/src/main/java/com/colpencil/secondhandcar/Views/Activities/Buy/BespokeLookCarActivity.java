package com.colpencil.secondhandcar.Views.Activities.Buy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Bean.Response.CarInfo;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Buy.AddOrderPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.colpencil.secondhandcar.Views.Activities.PayActivity;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Imples.Buy.AddOrderView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.weitu.mylibrary.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;

import static com.umeng.analytics.pro.x.I;

/**
 * Created by Administrator on 2017/4/12.
 * 预约看车
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_bespoke_look_car)
public class BespokeLookCarActivity extends ColpencilActivity implements View.OnClickListener, AddOrderView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.text_info)
    TextView tv_info;

    @Bind(R.id.text_pay)
    TextView tv_pay;

    @Bind(R.id.text_time)
    TextView tv_time;

    @Bind(R.id.img_tag)
    ImageView iv_tag;

    @Bind(R.id.img_car)
    ImageView iv_car;

    @Bind(R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.text_age)
    TextView tv_age;

    @Bind(R.id.text_km)
    TextView tv_km;

    @Bind(R.id.text_address)
    TextView tv_address;

    @Bind(R.id.tv_ma)
    TextView tv_price;

    @Bind(R.id.iv_statue)
    ImageView iv_sale;

    @Bind(R.id.iv_period)
    ImageView iv_period;

    @Bind(R.id.text_subscription)
    TextView tv_subscription;

    @Bind(R.id.text_total_price)
    TextView tv_total_price;

    @Bind(R.id.rl_time)
    RelativeLayout rl_time;

    private double rate = 3.5;
    private TimePickerView pickerView;
    private long time = 0;
    private CarInfo carInfo;
    private AddOrderPresenter presenter;

    @Override
    protected void initViews(View view) {
        carInfo = (CarInfo) getIntent().getSerializableExtra("good");
        tv_title.setText("预约看车");
        tv_info.setText(getResources().getString(R.string.bespoke_look_car, rate));
        initData();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(calendar.get(Calendar.YEAR)+99, 12, 31, 23, 59);
        pickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                time = date.getTime();
                tv_time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
            }
        })      .setCancelText("取消")
                .setSubmitText("确定")
                .setContentSize(18)
                .setTitleSize(20)
                .setRangDate(calendar, endCalendar)
                .setOutSideCancelable(true)
                .setType(new boolean[]{true, true, true, true, true, false})
                .build();

        ll_left.setOnClickListener(this);
        tv_pay.setOnClickListener(this);
        rl_time.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData(){
        iv_tag.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(carInfo.getPicList().get(0).getPic())
                .into(iv_car);
        tv_name.setText(carInfo.getGoods_name());
        tv_age.setText(StringUtils.formatDate(carInfo.getListed_time()));
        tv_km.setText(carInfo.getMileage()+"万公里");
        tv_address.setText(carInfo.getCity_name());
        tv_price.setText(StringUtils.doubleFormat(carInfo.getPrice())+"万");
        if(carInfo.getIs_urgent() == 1){
            iv_sale.setVisibility(View.VISIBLE);
        } else {
            iv_sale.setVisibility(View.GONE);
        }
        if(carInfo.getInstallment() == 1){
            iv_period.setVisibility(View.VISIBLE);
        } else {
            iv_period.setVisibility(View.GONE);
        }
        tv_subscription.setText(carInfo.getDeposit()+"元");
        tv_total_price.setText(carInfo.getDeposit()+"元");
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new AddOrderPresenter();
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
            case R.id.text_pay: //去支付
                if(time <= 0){
                    Toast.makeText(getApplicationContext(), "请选择看车时间", Toast.LENGTH_SHORT).show();
                } else if(time < System.currentTimeMillis()){
                    Toast.makeText(this, "看车时间不能小于当前时间", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
                    params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
                    params.put("goods_id", carInfo.getGoods_id()+"");
                    params.put("look_time", time+"");
                    presenter.addOrder(params);

                }
                break;
            case R.id.rl_time: //选择时间
                pickerView.show();
                break;
        }
    }

    @Override
    public void addSuccess(Result_comment result_comment) {
        Intent intent = new Intent(BespokeLookCarActivity.this, PayActivity.class);
        intent.putExtra("orderTime",result_comment.getData().getLook_time()+"");
        intent.putExtra("orderMount",result_comment.getData().getOrder_amount()+"");
        intent.putExtra("orderNo",result_comment.getData().getSn());
        intent.putExtra("orderId",result_comment.getData().getOrder_id()+"");
        startActivity(intent);
        finish();
    }

    @Override
    public void addFaile(Result_comment result_comment) {
        if(result_comment.getCode() == -1){
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
            Toast.makeText(this, result_comment.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void netfail(String message) {
        Toast.makeText(this, "网络出错", Toast.LENGTH_SHORT).show();
    }
}
