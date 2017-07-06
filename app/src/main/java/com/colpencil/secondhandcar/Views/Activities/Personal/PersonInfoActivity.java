package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.PersonInfo;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Mine.PersonInfoPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Imples.Mine.PersonInfoView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;

import java.util.HashMap;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/26.
 */

@ActivityFragmentInject(contentViewId = R.layout.activity_person_info)
public class PersonInfoActivity extends ColpencilActivity implements PersonInfoView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.et_name)
    EditText et_name;

    @Bind(R.id.et_bank)
    EditText et_bank;

    @Bind(R.id.et_bank_)
    EditText et_bank_;

    @Bind(R.id.et_bank_number)
    EditText et_number;

    @Bind(R.id.et_info)
    EditText et_info;

    @Bind(R.id.et_invite)
    TextView et_invite;

    @Bind(R.id.text_change)
    TextView tv_change;

    @Bind(R.id.et_tel)
    EditText et_tel;

    private PersonInfoPresenter presenter;
    private String name;
    private String tel;
    private String bank;
    private String bank_info;
    private String number;
    private String id_number;

    @Override
    protected void initViews(View view) {
        tv_title.setText("修改个人信息");
        showLoading("加载中");
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
        presenter.personInfo(params);

        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });
    }

    /**
     * 确认修改
     */
    private void change(){
        if(et_name.getText() == null){
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            name = et_name.getText().toString();
        }
        if(et_tel.getText() == null && !TextStringUtils.isMobileNO(et_tel.getText().toString())){
            Toast.makeText(this, "电话号码为空或者错误", Toast.LENGTH_SHORT).show();
            return;
        } else {
            tel = et_tel.getText().toString();
        }
        if(et_bank.getText() == null){
            Toast.makeText(this, "银行不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            bank = et_bank.getText().toString();
        }
        if(et_bank_.getText() == null){
            Toast.makeText(this, "开户行不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            bank_info = et_bank_.getText().toString();
        }
        if(et_number.getText() == null){
            Toast.makeText(this, "银行卡号不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            number = et_number.getText().toString();
        }
        if(et_info.getText() == null){
            Toast.makeText(this, "身份证号不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            id_number = et_info.getText().toString();
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
        params.put("store_id", SharedPreferencesUtil.getInstance(this).getInt("store_id")+"");
        params.put("bank_account_name", name);
        params.put("bank_account_number", number);
        params.put("bank_name", bank);
        params.put("bank_place", bank_info);
        params.put("tel", tel);
        params.put("id_number", id_number);
        presenter.changeInfo(params);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new PersonInfoPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void personInfo(ResultInfo<PersonInfo> resultInfo) {
        hideLoading();
//        tv_title.setText(resultInfo.getData().getTel());
        et_name.setText(resultInfo.getData().getBank_account_name());
        et_tel.setText(resultInfo.getData().getTel());
        et_bank.setText(resultInfo.getData().getBank_name());
        et_bank_.setText(resultInfo.getData().getBank_place());
        et_number.setText(resultInfo.getData().getBank_account_number());
        et_info.setText(resultInfo.getData().getId_number());
        if(resultInfo.getData().getInvite_code() != null){
            et_invite.setText(resultInfo.getData().getInvite_code());
        }
    }

    @Override
    public void loadError(ResultInfo<PersonInfo> resultInfo) {
        hideLoading();
        if(resultInfo.getCode() == -1){
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
            Toast.makeText(this, resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void changeInfo(Result_comment result_comment) {
        Toast.makeText(this, result_comment.getMessage(), Toast.LENGTH_SHORT).show();
        RxMsg msg = new RxMsg();
        RxBus.get().post("changeSuccess", msg);
        ColpencilFrame.getInstance().finishActivity(this);
    }

    @Override
    public void changeFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
