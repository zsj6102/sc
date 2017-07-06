package com.colpencil.secondhandcar.Views.Activities.Buy.Period;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.PeriodBuy;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Adapter.Buy.PeriodBuyAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/13.
 * 分期购车支付（预约）
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_periodization_buy)
public class PeriodPayActivity extends ColpencilActivity implements View.OnClickListener{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.text_now_price)
    TextView tv_now_price;

    @Bind(R.id.list_entry)
    ListView lv_entry;

    @Bind(R.id.text_look)
    TextView tv_look;

    private PeriodBuyAdapter periodBuyAdapter;
    private List<PeriodBuy> periodBuys;

    private Intent intent;

    @Override
    protected void initViews(View view) {
        tv_title.setText("分期购车支付");

        tv_now_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        periodBuys = new ArrayList<>();
        periodBuys.add(new PeriodBuy());
        periodBuys.add(new PeriodBuy());
        periodBuys.add(new PeriodBuy());
        periodBuys.add(new PeriodBuy());
        periodBuys.add(new PeriodBuy());
        periodBuys.add(new PeriodBuy());
        periodBuys.add(new PeriodBuy());
        periodBuys.add(new PeriodBuy());
        periodBuys.add(new PeriodBuy());
        periodBuys.add(new PeriodBuy());
        periodBuys.add(new PeriodBuy());
        periodBuys.add(new PeriodBuy());
        periodBuys.add(new PeriodBuy());

        periodBuyAdapter = new PeriodBuyAdapter(this, periodBuys, R.layout.item_periodization_buy);
        lv_entry.setAdapter(periodBuyAdapter);
        ll_left.setOnClickListener(this);
        tv_look.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.text_pay: // 去支付
                break;
            case R.id.text_look: //查看支付详情
                intent = new Intent(this, TotalPayActivity.class);
                startActivity(intent);
                break;
        }
    }
}
