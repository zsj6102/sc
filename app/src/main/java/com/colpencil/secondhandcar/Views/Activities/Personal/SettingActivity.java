package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.Logout;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Mine.LogoutPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Imples.Mine.LogoutView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.AndroidUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DataCleanManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/1.
 * 设置
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_setting)
public class SettingActivity extends ColpencilActivity implements View.OnClickListener, LogoutView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.text_logout)
    TextView tv_logout;

    @Bind(R.id.rl_clear)
    RelativeLayout rl_clear;

    @Bind(R.id.text_cache)
    TextView tv_cache;

    @Bind(R.id.text_version)
    TextView tv_version;

    @Bind(R.id.rl_about)
    RelativeLayout rl_about;

    private LogoutPresenter presenter;

    @Override
    protected void initViews(View view) {
        tv_title.setText("设置");

        if(SharedPreferencesUtil.getInstance(this).getBoolean("isLogin", false)){
            tv_logout.setVisibility(View.VISIBLE);
        } else {
            tv_logout.setVisibility(View.GONE);
        }

        tv_version.setText("V"+AndroidUtils.getVersionName(this));
        try {
            tv_cache.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ll_left.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
        rl_clear.setOnClickListener(this);
        rl_about.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new LogoutPresenter();
        return presenter;
    }

    private void logout(){
        presenter.logout(SharedPreferencesUtil.getInstance(this).getInt("member_id"),
                SharedPreferencesUtil.getInstance(this).getString("token"));
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
            case R.id.text_logout: //登出
                logout();
                break;
            case R.id.rl_clear:
                DataCleanManager.clearAllCache(this);
                try {
                    tv_cache.setText(DataCleanManager.getTotalCacheSize(this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
    }

    @Override
    public void logout(Logout logout) {
        Toast.makeText(this, logout.getMessage(), Toast.LENGTH_SHORT).show();
        SharedPreferencesUtil.getInstance(this).setBoolean("isLogin", false);
        SharedPreferencesUtil.getInstance(this).setString("isCommit", " ");
        SharedPreferencesUtil.getInstance(this).setInt("member_id", 0);
        SharedPreferencesUtil.getInstance(this).setString("token", " ");
        SharedPreferencesUtil.getInstance(this).setInt("is_store", 0);
        SharedPreferencesUtil.getInstance(this).setString("mobile", " ");
        SharedPreferencesUtil.getInstance(this).setInt("store_id", 0);
        RxBusMsg msg = new RxBusMsg();
        msg.setCarType(1);
        msg.setLogin(false);
        RxBus.get().post("rxIsLogin", msg);
        ColpencilFrame.getInstance().finishActivity(this);
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void loadError(Logout logout) {
        if(logout.getCode() == -1){
            Toast.makeText(this, "退出成功", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setBoolean("isLogin", false);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("member_id", 0);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("token", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("is_store", 0);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("isCommit", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("mobile", " ");
            RxBusMsg msg = new RxBusMsg();
            msg.setCarType(1);
            msg.setLogin(false);
            RxBus.get().post("rxIsLogin", msg);
            ColpencilFrame.getInstance().finishActivity(this);
            startActivity(new Intent(CarApplication.getInstance(), LoginActivity.class));
        } else {
            Toast.makeText(this, logout.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
