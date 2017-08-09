package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.util.HashMap;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/6/6.
 * 个人信息
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_info)
public class InfoActivity extends ColpencilActivity implements PersonInfoView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_rigth)
    TextView tv_right;

    @Bind(R.id.et_name)
    TextView et_name;

    @Bind(R.id.et_bank)
    TextView et_bank;

    @Bind(R.id.et_bank_)
    TextView et_bank_;

    @Bind(R.id.et_bank_number)
    TextView et_number;

    @Bind(R.id.et_info)
    TextView et_info;

    @Bind(R.id.et_invite)
    TextView et_invite;

    @Bind(R.id.et_tel)
    TextView et_tel;

    private PersonInfoPresenter presenter;
    private Observable<RxMsg> observable;

    @Override
    protected void initViews(View view) {
        tv_title.setText("个人信息");
        showLoading("加载中");
        loadData();
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, PersonInfoActivity.class);
                startActivity(intent);
            }
        });
        initBus();
    }

    private void loadData(){
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
        presenter.personInfo(params);
    }

    private void initBus(){
        observable = RxBus.get().register("changeSuccess", RxMsg.class);
        Subscriber<RxMsg> subscriber = new Subscriber<RxMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxMsg rxMsg) {
                loadData();
            }
        };
        observable.subscribe(subscriber);
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
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("changeSuccess", observable);
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
        }else{
            et_invite.setText("无");
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
            SharedPreferencesUtil.getInstance(this).setString("isCommit", " ");
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

    }

    @Override
    public void changeFailure(String message) {
    }
}
