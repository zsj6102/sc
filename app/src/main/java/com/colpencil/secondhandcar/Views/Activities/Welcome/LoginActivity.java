package com.colpencil.secondhandcar.Views.Activities.Welcome;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.Login;
import com.colpencil.secondhandcar.Bean.Response.SendCode;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.LoginPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Imples.Welcome.LoginView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeCount;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/1.
 * 登录
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_login)
public class LoginActivity extends ColpencilActivity implements View.OnClickListener, LoginView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.edt_phone)
    EditText et_phone;

    @Bind(R.id.edt_code)
    EditText et_code;

    @Bind(R.id.text_login)
    TextView tv_login;

    @Bind(R.id.text_code)
    TextView tv_code;

    @Bind(R.id.cb_agree)
    CheckBox cb_agree;

    @Bind(R.id.text_term)
    TextView tv_term;

    private LoginPresenter presenter;
    private boolean isCheck = true;

    @Override
    protected void initViews(View view) {
        tv_title.setText("登录");

        SpannableString sp = new SpannableString(tv_term.getText().toString());
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.title_color)), 6, 10, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_term.setText(sp);
        cb_agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isCheck = true;
                } else {
                    isCheck = false;
                }
            }
        });
        ll_left.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        tv_code.setOnClickListener(this);
        tv_term.setOnClickListener(this);
    }

    private void login(){
        String mobile = et_phone.getText().toString();
        String code = et_code.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(this,"手机号不能为空", Toast.LENGTH_SHORT).show();
        } else if (!TextStringUtils.isMobileNO(mobile)) {
            Toast.makeText(this, "请输入正确的手机号",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
        } else if(!isCheck){
            Toast.makeText(this,"请先查看并同意（勾选）我们的服务条款内容", Toast.LENGTH_SHORT).show();
        } else {
            DialogTools.showLoding(this, "登录", "正在登录中。。。");
            presenter.login(mobile, code);
        }
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new LoginPresenter();
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
            case R.id.text_login: //登录
                login();
                break;
            case R.id.text_code:
                if(tv_code.getText().equals("重新获取") || tv_code.getText().equals("验证码")){
                    showLoading("加载中");
                    if (TextUtils.isEmpty(et_phone.getText().toString())) {
                        ToastTools.showShort(this, "手机号码不能为空！");
                        hideLoading();
                        return;
                    } else if (!TextStringUtils.isMobileNO(et_phone.getText().toString())) {
                        hideLoading();
                        ToastTools.showShort(this, "请输入正确的手机号码");
                        return;
                    }
                    presenter.sendCode(et_phone.getText().toString());
                }
                break;
            case R.id.text_term:
                Intent intent = new Intent(this, TermActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void login(ResultInfo<Login> result) {
        DialogTools.dissmiss();
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        String message= result.getMessage();
        if(result.getCode() == 1){
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setBoolean("isLogin", true);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("mobile", result.getData().getMobile());
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("member_id", result.getData().getMember_id());
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("is_store", result.getData().getIs_store());
            SharedPreferencesUtil.getInstance(this).setString("token", result.getData().getToken());
            if(result.getData().getIs_store() == 1){
                SharedPreferencesUtil.getInstance(this).setInt("store_id", result.getData().getStore_id());
            }
            RxBusMsg msg = new RxBusMsg();
            msg.setCarType(1);
            msg.setLogin(true);
            msg.setSell(result.getData().getIs_store());
            RxBus.get().post("rxIsLogin", msg);
            ColpencilFrame.getInstance().finishActivity(this);
        } else {
            ToastTools.showShort(this, message);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setBoolean("isLogin", false);
        }
    }

    @Override
    public void loginFaile(String message) {
        DialogTools.dissmiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setBoolean("isLogin", false);
    }

    @Override
    public void sendSuccess(SendCode code) {
        hideLoading();
        Toast.makeText(this, code.getMessage(), Toast.LENGTH_SHORT).show();
        TimeCount time = new TimeCount(60000, 1000, tv_code);
        time.start();//开启倒计时
    }

    @Override
    public void sendFaile(String message) {
        hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
