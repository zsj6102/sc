package com.colpencil.secondhandcar.Views.Activities.Sell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.ApplyRate;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Sell.ApplyRatePresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Imples.Sell.ApplyRateView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/12.
 * 申请进度
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_apply_commit)
public class LookApplyActivity extends ColpencilActivity implements View.OnClickListener, ApplyRateView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.text_back_car)
    TextView tv_back;

    @Bind(R.id.layout_empty)
    LinearLayout empty;

    @Bind(R.id.layout_success)
    LinearLayout ll_success;

    @Bind(R.id.layout_faile)
    LinearLayout ll_faile;

    @Bind(R.id.tv_title)
    TextView tv_title;

    private int disabled;
    private TextView tv_reason;
    private TextView tv_name;
    private TextView tv_time;
    private TextView tv_tel;
    private TextView tv_id;
    private TextView tv_bank;
    private TextView tv_apply;

    private ApplyRatePresenter presenter;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.text_back_car:
                finish();
                break;
        }
    }

    @Override
    public void applyRate(ResultInfo<ApplyRate> resultInfo) {
        hideLoading();
        disabled = resultInfo.getData().getDisabled();
        if(disabled == 0){
            empty.setVisibility(View.GONE);
            ll_success.setVisibility(View.VISIBLE);
            ll_faile.setVisibility(View.GONE);
            tv_apply = (TextView) findViewById(R.id.text_apply);
            tv_name = (TextView) findViewById(R.id.text_name);
            tv_time = (TextView) findViewById(R.id.text_time);
            tv_tel = (TextView) findViewById(R.id.text_phone);
            tv_id = (TextView) findViewById(R.id.text_msg);
            tv_bank = (TextView) findViewById(R.id.text_bank);
            tv_apply.setText("提交成功");
            tv_name.setText(resultInfo.getData().getBank_account_name());
            tv_tel.setText(resultInfo.getData().getTel());
            tv_time.setText(StringUtils.formatDate(resultInfo.getData().getApply_time()));
            tv_id.setText(resultInfo.getData().getId_number());
            tv_bank.setText(resultInfo.getData().getBank_account_number());
        } else if(disabled == 2){
            empty.setVisibility(View.GONE);
            ll_success.setVisibility(View.GONE);
            ll_faile.setVisibility(View.VISIBLE);
            tv_apply = (TextView) findViewById(R.id.text_apply);
            tv_reason = (TextView) findViewById(R.id.text_faile_reason);
            tv_apply.setText("申请失败");
            tv_reason.setText("原因：" + resultInfo.getData().getRefuse_reason());
        } else if(disabled == -1){
            empty.setVisibility(View.VISIBLE);
            ll_success.setVisibility(View.GONE);
            ll_faile.setVisibility(View.GONE);
        } else if(disabled == 1){
            empty.setVisibility(View.GONE);
            ll_success.setVisibility(View.VISIBLE);
            ll_faile.setVisibility(View.GONE);
            tv_apply = (TextView) findViewById(R.id.text_apply);
            tv_name = (TextView) findViewById(R.id.text_name);
            tv_time = (TextView) findViewById(R.id.text_time);
            tv_tel = (TextView) findViewById(R.id.text_phone);
            tv_id = (TextView) findViewById(R.id.text_msg);
            tv_bank = (TextView) findViewById(R.id.text_bank);
            tv_reason = (TextView) findViewById(R.id.text_reason);
            tv_apply.setText("申请成功");
            tv_reason.setVisibility(View.GONE);
            tv_name.setText(resultInfo.getData().getBank_account_name());
            tv_tel.setText(resultInfo.getData().getTel());
            tv_time.setText(StringUtils.formatTime(resultInfo.getData().getApply_time()));
            tv_id.setText(resultInfo.getData().getId_number());
            tv_bank.setText(resultInfo.getData().getBank_account_number());
        }
    }

    @Override
    public void loadError(ResultInfo<ApplyRate> resultInfo) {
        if(resultInfo.getCode() == -1){
            Toast.makeText(CarApplication.getInstance(), "身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setBoolean("isLogin", false);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("member_id", 0);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("token", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("is_store", 0);
            SharedPreferencesUtil.getInstance(this).setString("isCommit", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("mobile", " ");
            RxBusMsg msg = new RxBusMsg();
            msg.setCarType(1);
            msg.setLogin(false);
            RxBus.get().post("rxIsLogin", msg);
            ColpencilFrame.getInstance().finishActivity(this);
            startActivity(new Intent(CarApplication.getInstance(), LoginActivity.class));
        } else {
            empty.setVisibility(View.VISIBLE);
            ll_success.setVisibility(View.GONE);
            ll_faile.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initViews(View view) {
        tv_title.setText("申请进度");
        showLoading("加载中");
        empty.setVisibility(View.GONE);
        ll_faile.setVisibility(View.GONE);
        ll_success.setVisibility(View.GONE);
        HashMap<String, String> params = new HashMap<>();
        params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
        params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
        presenter.applyRate(params);

        ll_left.setOnClickListener(this);
        tv_back.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new ApplyRatePresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
}
