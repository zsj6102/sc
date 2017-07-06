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
import com.colpencil.secondhandcar.Bean.Response.Goods;
import com.colpencil.secondhandcar.Bean.Response.InsList;
import com.colpencil.secondhandcar.Bean.Response.PeriodBuyCar;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Buy.PeriodBuyPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.colpencil.secondhandcar.Ui.AlertDialog;
import com.colpencil.secondhandcar.Views.Activities.Personal.BalanceActivity;
import com.colpencil.secondhandcar.Views.Adapter.Buy.PeriodBuyCarAdapter;
import com.colpencil.secondhandcar.Views.Imples.Buy.PeriodBuyView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/18.
 * 分期购车支付（支付）
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_period_pay)
public class PeriodPayCarActivity extends ColpencilActivity implements View.OnClickListener, PeriodBuyView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.list_period)
    ListView lv_period;

    @Bind(R.id.img_tag)
    ImageView iv_tag;

    @Bind(R.id.img_car)
    ImageView iv_car;

    @Bind(R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.tv_ma)
    TextView tv_price;

    @Bind(R.id.text_year)
    TextView tv_year;

    @Bind(R.id.tv_statue)
    TextView tv_km;

    @Bind(R.id.text_city)
    TextView tv_city;

    @Bind(R.id.text_urgent)
    ImageView iv_urgent;

    @Bind(R.id.img_period)
    ImageView iv_period;

    @Bind(R.id.text_period)
    TextView tv_pay;

    private PeriodBuyPresenter presenter;
    private PeriodBuyCarAdapter periodBuyCarAdapter;
    private List<InsList> periodBuyCars;
    private int orderId;

    @Override
    protected void initViews(View view) {
        tv_title.setText("分期购车");
        showLoading("加载中");
        orderId = getIntent().getIntExtra("order_id", 0);
        periodBuyCars = new ArrayList<>();
        periodBuyCarAdapter = new PeriodBuyCarAdapter(this, periodBuyCars);
        lv_period.setAdapter(periodBuyCarAdapter);

        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
        params.put("order_id", orderId+"");
        presenter.goInstallment(params);
        ll_left.setOnClickListener(this);
        tv_pay.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new PeriodBuyPresenter();
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
            case R.id.text_period:
                if(periodBuyCarAdapter.getSelection() == -1){
                    Toast.makeText(this, "请先选择分期期数", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog(this)
                            .builder()
                            .setCancelable(false)
                            .setMsg("确定要转分期，转分期后将生成分期订单，请确定好转分期付款后再点击确定")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    HashMap<String, String> params = new HashMap<>();
                                    params.put("member_id", SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getInt("member_id")+"");
                                    params.put("token", SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getString("token"));
                                    params.put("order_id", orderId+"");
                                    params.put("ins_num", periodBuyCars.get(periodBuyCarAdapter.getSelection()).getIns_num()+"");
                                    presenter.create(params);
                                }
                            }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
                }
                break;
        }
    }

    @Override
    public void goInstallment(ResultInfo<PeriodBuyCar> resultInfo) {
        hideLoading();
        Goods goods = resultInfo.getData().getGoods();
        if(goods.getNew_shelves() == 1){
            iv_tag.setVisibility(View.VISIBLE);
        } else {
            iv_tag.setVisibility(View.GONE);
        }
        Glide.with(this)
                .load(goods.getPic())
                .into(iv_car);
        tv_name.setText(goods.getName());
        tv_price.setText(goods.getPrice()+"万");
        tv_year.setText(StringUtils.formatYear(goods.getCreate_time()));
        tv_km.setText(goods.getMileage()+"万公里");
        tv_city.setText(goods.getCity_name());
        if(goods.getIs_urgent() == 1){
            iv_urgent.setVisibility(View.VISIBLE);
        } else {
            iv_urgent.setVisibility(View.GONE);
        }
        if(goods.getInstallment() == 1){
            iv_period.setVisibility(View.VISIBLE);
        } else {
            iv_period.setVisibility(View.GONE);
        }
        periodBuyCars.clear();
        periodBuyCars.addAll(resultInfo.getData().getInsList());
        periodBuyCarAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadError(String message) {
        hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createSuccess(Result_comment result_comment) {
        Toast.makeText(this, "创建分期账单成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, BalanceActivity.class);
        startActivity(intent);
        ColpencilFrame.getInstance().finishActivity(this);
    }

    @Override
    public void createFailue(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
