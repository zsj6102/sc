package com.colpencil.secondhandcar.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Activities.Home.WebViewActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

import static com.colpencil.secondhandcar.R.id.ll_left;

@ActivityFragmentInject(contentViewId = R.layout.activity_pay)
public class PayActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_orderno)
    TextView tvOrderno;
    @Bind(R.id.tv_order_time)
    TextView tvOrderTime;
    @Bind(R.id.text_pay)
    TextView textPay;
    @Bind(R.id.text_total_price)
    TextView textTotalPrice;
    @Bind(ll_left)
    LinearLayout llLeft;

    private String orderNo;
    private String time;
    private String price;
    private String orderId;

    @Override
    protected void initViews(View view) {
        tvTitle.setText("确认支付");
        orderNo = getIntent().getStringExtra("orderNo");
        time = getIntent().getStringExtra("orderTime");
        price = getIntent().getStringExtra("orderMount");
        orderId = getIntent().getStringExtra("orderId");
        tvOrderTime.setText(TimeUtil.getTimeStrFromMillis(Long.parseLong(time)));
        tvOrderno.setText(orderNo);
        textTotalPrice.setText(price);
        textPay.setOnClickListener(this);
        llLeft.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_pay:
                Intent intent = new Intent(PayActivity.this, WebViewActivity.class);
                intent.putExtra("url", "http://39.108.80.234/api/mobile/icbc/pay.do?" + "order_id" + "=" + Integer.parseInt(orderId));
                startActivity(intent);
                finish();
                break;
            case ll_left:
                finish();
                break;

        }
    }
}
