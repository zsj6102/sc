package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.BillDetails;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Present.Mine.BillDetailsPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.colpencil.secondhandcar.Views.Imples.Mine.BillDetailsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/23.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_bill_details)
public class BillDetailsActivity extends ColpencilActivity implements BillDetailsView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.text_number)
    TextView tv_number;

    @Bind(R.id.text_price)
    TextView tv_price;

    @Bind(R.id.text_name)
    TextView tv_name;

    @Bind(R.id.text_bank_number)
    TextView tv_bank_number;

    @Bind(R.id.text_bank)
    TextView tv_bank;

    @Bind(R.id.text_cost)
    TextView tv_cost;

    @Bind(R.id.text_time)
    TextView tv_time;

    @Bind(R.id.layout_data)
    LinearLayout ll_data;

    @Bind(R.id.empty)
    LinearLayout empty;

    private BillDetailsPresenter presenter;
    private int id;

    @Override
    protected void initViews(View view) {
        id = getIntent().getIntExtra("id", 0);
        tv_title.setText("详细记录");
        showLoading("加载中");
        empty.setVisibility(View.GONE);
        ll_data.setVisibility(View.GONE);
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
        params.put("id", id+"");
        presenter.billDetails(params);
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new BillDetailsPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void billDetails(ResultInfo<BillDetails> resultInfo) {
        hideLoading();
        ll_data.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
        tv_number.setText(resultInfo.getData().getSn());
        tv_price.setText(StringUtils.doubleFormat(resultInfo.getData().getPrice())+"元");
        tv_bank_number.setText(resultInfo.getData().getBank_card());
        tv_bank.setText(resultInfo.getData().getBank());
        tv_name.setText(resultInfo.getData().getName());
        tv_cost.setText(StringUtils.doubleFormat(resultInfo.getData().getSet_money())+"元");
        tv_time.setText(StringUtils.format(resultInfo.getData().getSet_time()));
    }

    @Override
    public void loadError(String message) {
        hideLoading();
        empty.setVisibility(View.VISIBLE);
        ll_data.setVisibility(View.GONE);
    }
}
