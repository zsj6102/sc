package com.colpencil.secondhandcar.Views.Activities.Personal.Message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.Message;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Bean.RxClickMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Mine.MessagePresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.colpencil.secondhandcar.Views.Activities.Personal.SystemMessageActivity;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Imples.Mine.MessageView;
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
 * Created by Administrator on 2017/4/1.
 * 消息中心
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_message_core)
public class MessageCoreActivity extends ColpencilActivity implements View.OnClickListener, MessageView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.layout_reduce)
    LinearLayout ll_reduce;

    @Bind(R.id.layout_period)
    LinearLayout ll_period;

    @Bind(R.id.layout_system)
    LinearLayout ll_system;

    @Bind(R.id.syatem_recommand)
    TextView syatem_recommand;

    @Bind(R.id.period_recommand)
    TextView period_recommand;

    @Bind(R.id.reduce_recommand)
    TextView reduce_recommand;

    @Bind(R.id.empty)
    LinearLayout empty;

    @Bind(R.id.layout)
    LinearLayout ll_message;

    @Bind(R.id.text_reduce_date)
    TextView tv_reduce_time;

    @Bind(R.id.text_period_date)
    TextView tv_period_time;

    @Bind(R.id.text_system_date)
    TextView tv_system_time;

    @Bind(R.id.text_reduce)
    TextView tv_reduce;

    @Bind(R.id.text_period)
    TextView tv_period;

    @Bind(R.id.text_system)
    TextView tv_system;

    @Bind(R.id.img_urgent)
    ImageView iv_urgent;

    private Intent intent;
    private MessagePresenter presenter;
    private boolean show0;
    private boolean show1;
    private boolean show2;

    @Override
    protected void initViews(View view) {
        showLoading("");
        tv_title.setText("消息中心");
        empty.setVisibility(View.GONE);
        ll_message.setVisibility(View.GONE);
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
        presenter.getMessage(params);
        Intent intent = getIntent();
        show0 = intent.getExtras().getBoolean("isShow0");
        show1 = intent.getExtras().getBoolean("isShow1");
        show2 = intent.getExtras().getBoolean("isShow2");
        showRedCircle();
        ll_left.setOnClickListener(this);
        ll_period.setOnClickListener(this);
        ll_reduce.setOnClickListener(this);
        ll_system.setOnClickListener(this);
    }
    private void showRedCircle(){
        if(show0){
            syatem_recommand.setVisibility(View.VISIBLE);
        }else{
            syatem_recommand.setVisibility(View.GONE);
        }
        if(show1){
            period_recommand.setVisibility(View.VISIBLE);
        }else{
            period_recommand.setVisibility(View.GONE);
        }
        if(show2){
            reduce_recommand.setVisibility(View.VISIBLE);
        }else{
            reduce_recommand.setVisibility(View.GONE);
        }
    }
    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MessagePresenter();
        return presenter;
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
            case R.id.layout_reduce: //降价通知
                RxClickMsg msg = new RxClickMsg();
                msg.setClick(true);
                msg.setType(2);
                RxBus.get().post("meClick",msg);
                reduce_recommand.setVisibility(View.GONE);
                intent = new Intent(this, MineDepreciateNoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_period: //分期购车通知
                RxClickMsg msg1 = new RxClickMsg();
                msg1.setClick(true);
                msg1.setType(1);
                RxBus.get().post("meClick",msg1);
                period_recommand.setVisibility(View.GONE);
                intent = new Intent(this, PeriodNoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_system: //系统消息
                RxClickMsg msg0 = new RxClickMsg();
                msg0.setClick(true);
                msg0.setType(0);
                RxBus.get().post("meClick",msg0);
                syatem_recommand.setVisibility(View.GONE);
                intent = new Intent(this, SystemMessageActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getMessage(ResultInfo<Message> resultInfo) {
        hideLoading();
        empty.setVisibility(View.GONE);
        ll_message.setVisibility(View.VISIBLE);
        //降价通知
        if(resultInfo.getData().getReduce_message() == null){
           ll_reduce.setVisibility(View.GONE);
        } else {
            ll_reduce.setVisibility(View.VISIBLE);
            tv_reduce_time.setText(StringUtils.format(resultInfo.getData().getReduce_time()));
            tv_reduce.setText(resultInfo.getData().getReduce_message());
        }
        //分期购车
        if(resultInfo.getData().getIns_message() == null){
            ll_period.setVisibility(View.GONE);
        } else {
            ll_period.setVisibility(View.VISIBLE);
            tv_period_time.setText(StringUtils.format(resultInfo.getData().getIns_time()));
            tv_period.setText(resultInfo.getData().getIns_message());
        }
        //系统消息
        if(resultInfo.getData().getSys_message() == null){
            ll_system.setVisibility(View.GONE);
        } else {
            ll_system.setVisibility(View.VISIBLE);
            tv_system_time.setText(StringUtils.format(resultInfo.getData().getSys_time()));
            tv_system.setText(resultInfo.getData().getSys_message());
            if(resultInfo.getData().getSys_hurry() == 1){
                iv_urgent.setVisibility(View.VISIBLE);
            } else {
                iv_urgent.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void loadError(ResultInfo<Message> resultInfo) {
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
            empty.setVisibility(View.VISIBLE);
            ll_message.setVisibility(View.GONE);
        }
    }
}
