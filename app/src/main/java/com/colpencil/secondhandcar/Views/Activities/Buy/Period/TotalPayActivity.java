package com.colpencil.secondhandcar.Views.Activities.Buy.Period;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.TotalPay;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Adapter.Buy.TotalPayAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/18.
 * 累计支付详情
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_total_pay)
public class TotalPayActivity extends ColpencilActivity implements View.OnClickListener{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.list_total)
    ListView lv_total;

    private TotalPayAdapter totalPayAdapter;
    private List<TotalPay> totalPays;

    @Override
    protected void initViews(View view) {
        tv_title.setText("累计支付详情");

        totalPays = new ArrayList<>();
        totalPays.add(new TotalPay());
        totalPays.add(new TotalPay());
        totalPays.add(new TotalPay());
        totalPays.add(new TotalPay());
        totalPayAdapter = new TotalPayAdapter(this, totalPays, R.layout.item_total_pay);
        lv_total.setAdapter(totalPayAdapter);

        ll_left.setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
