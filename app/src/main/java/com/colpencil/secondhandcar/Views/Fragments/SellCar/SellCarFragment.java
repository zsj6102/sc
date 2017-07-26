package com.colpencil.secondhandcar.Views.Fragments.SellCar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Base.BaseFragment;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.SellAdv;
import com.colpencil.secondhandcar.Bean.Response.SellApply;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Sell.ApplySellPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.GlideLoader;
import com.colpencil.secondhandcar.Views.Activities.Buy.CarDetailsActivity;
import com.colpencil.secondhandcar.Views.Activities.Home.WebViewActivity;
import com.colpencil.secondhandcar.Views.Activities.Sell.LookApplyActivity;
import com.colpencil.secondhandcar.Views.Activities.Sell.PhotoViewActivity;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Imples.Sell.ApplySellView;
import com.jph.takephoto.model.TResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/3/22.
 * 卖车申请fragment
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_sell)
public class SellCarFragment extends BaseFragment implements View.OnClickListener, ApplySellView{

    @Bind(R.id.banner_car)
    Banner banner;

    @Bind(R.id.text_look)
    TextView tv_look;

    @Bind(R.id.layout_upload_z)
    LinearLayout ll_upload_z;

    @Bind(R.id.layout_upload_f)
    LinearLayout ll_upload_f;

    @Bind(R.id.layout_upload_s)
    LinearLayout ll_upload_s;

    @Bind(R.id.layout_upload_yz)
    LinearLayout ll_upload_yz;

    @Bind(R.id.layout_upload_yf)
    LinearLayout ll_upload_yf;

    @Bind(R.id.rl_z)
    RelativeLayout rl_z;

    @Bind(R.id.img_z)
    ImageView iv_z;

    @Bind(R.id.img_z_d)
    ImageView iv_z_d;

    @Bind(R.id.rl_f)
    RelativeLayout rl_f;

    @Bind(R.id.img_f)
    ImageView iv_f;

    @Bind(R.id.img_f_d)
    ImageView iv_f_d;

    @Bind(R.id.rl_s)
    RelativeLayout rl_s;

    @Bind(R.id.img_s)
    ImageView iv_s;

    @Bind(R.id.img_s_d)
    ImageView iv_s_d;

    @Bind(R.id.rl_bz)
    RelativeLayout rl_bz;

    @Bind(R.id.img_bz)
    ImageView iv_bz;

    @Bind(R.id.img_bz_d)
    ImageView iv_bz_d;

    @Bind(R.id.rl_bf)
    RelativeLayout rl_bf;

    @Bind(R.id.img_bf)
    ImageView iv_bf;

    @Bind(R.id.img_bf_d)
    ImageView iv_bf_d;

    @Bind(R.id.text_apply)
    TextView tv_apply;

    @Bind(R.id.et_name)
    EditText et_name;

    @Bind(R.id.et_tel)
    EditText et_tel;

    @Bind(R.id.et_code)
    EditText et_code;

    @Bind(R.id.et_bank)
    EditText et_bank;

    @Bind(R.id.et_bank_info)
    EditText et_bank_info;

    @Bind(R.id.et_account)
    EditText et_account;

    @Bind(R.id.et_invite)
    EditText et_invite;

    private List<String> images;
    private int flag = 0;

    private Intent intent;
    private ApplySellPresenter presenter;
    private int disabled;
    private String photoZ; //身份证正面
    private String photoF; //身份证反面
    private String photoS; //手持身份证
    private String photoBz; //银行卡正面
    private String photoBf; //银行卡反面

    private Observable<RxBusMsg> observable;
    private List<SellAdv> advs = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        initBannerData();
        initBus();
    }

    private void initBus(){
        observable = RxBus.get().register("isCommit", RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                if(SharedPreferencesUtil.getInstance(getActivity()).getString("isCommit").equals(SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id")+"success")){
                    tv_apply.setText("已提交");
                } else {
                    tv_apply.setText("提交");
                }
            }
        };
        observable.subscribe(subscriber);
    }

    private void initBannerData(){
        images = new ArrayList<>();
        banner.setImageLoader(new GlideLoader());
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                presenter.advCount(advs.get(position).getAid());
                if(advs.get(position).getAdv_type() == 1){
                    intent = new Intent(getActivity(), CarDetailsActivity.class);
                    intent.putExtra("carId", advs.get(position).getGoods_id());
                    startActivity(intent);
                } else {
                    if(advs.get(position).getUrl() != null){
                        intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("url", advs.get(position).getUrl());
                        startActivity(intent);
                    } else {

                    }
                }
            }
        });
//        if(SharedPreferencesUtil.getInstance(getActivity()).getString("isCommit").equals(SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id")+"success")){
//            tv_apply.setText("已提交");
//        } else {
//            tv_apply.setText("提交");
//        }
        tv_apply.setText("提交");
        presenter.sellData();
        initListener();

    }

    /**
     * 初始化监听
     */
    private void initListener(){
        tv_look.setOnClickListener(this);
        ll_upload_z.setOnClickListener(this);
        ll_upload_f.setOnClickListener(this);
        ll_upload_s.setOnClickListener(this);
        ll_upload_yz.setOnClickListener(this);
        ll_upload_yf.setOnClickListener(this);
        tv_apply.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new ApplySellPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    private void commit(){
        if(!SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getBoolean("isLogin", false)){
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextStringUtils.isEmpty(et_name.getText().toString())){
            Toast.makeText(getActivity(), "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextStringUtils.isEmpty(et_code.getText().toString())){
            Toast.makeText(getActivity(), "身份证号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!TextStringUtils.ispersonIdValidation(et_code.getText().toString())){
            Toast.makeText(getActivity(), "身份证号填写错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextStringUtils.isEmpty(photoZ)){
            Toast.makeText(getActivity(), "请上传身份证正面照", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextStringUtils.isEmpty(photoF)){
            Toast.makeText(getActivity(), "请上传身份证反面照", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextStringUtils.isEmpty(photoS)){
            Toast.makeText(getActivity(), "请上传手持身份证照", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextStringUtils.isEmpty(et_bank.getText().toString())){
            Toast.makeText(getActivity(), "请填写银行", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextStringUtils.isEmpty(et_bank_info.getText().toString())){
            Toast.makeText(getActivity(), "请填写银行开户行", Toast.LENGTH_SHORT).show();
           return;
        }
        if(TextStringUtils.isEmpty(et_account.getText().toString())){
            Toast.makeText(getActivity(), "请填写银行卡号", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextStringUtils.isEmpty(photoBz)){
            Toast.makeText(getActivity(), "请上传银行卡正面照", Toast.LENGTH_SHORT).show();
           return;
        }
        if(TextStringUtils.isEmpty(photoBf)){
            Toast.makeText(getActivity(), "请上传银行卡反面照", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, RequestBody> params = new HashMap<>();
        params.put("member_id", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getInt("member_id")+""));
        params.put("token", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getString("token")));
        params.put("bank_account_name", OkhttpUtils.toRequestBody(et_name.getText().toString()));
        params.put("tel", OkhttpUtils.toRequestBody(et_tel.getText().toString()));
        params.put("id_number",  OkhttpUtils.toRequestBody(et_code.getText().toString()));
        params.put("bank_name", OkhttpUtils.toRequestBody(et_bank.getText().toString()));
        params.put("bank_place", OkhttpUtils.toRequestBody(et_bank_info.getText().toString()));
        params.put("bank_account_number", OkhttpUtils.toRequestBody(et_account.getText().toString()));
        params.put("invite_code", OkhttpUtils.toRequestBody(et_invite.getText().toString()));
        params.put("aid_img\";filename=\"1.png", RequestBody.create(MediaType.parse("image/png"), new File(photoZ)));
        params.put("aid_back_img\";filename=\"2.png", RequestBody.create(MediaType.parse("image/png"), new File(photoF)));
        params.put("ahand_id_img\";filename=\"3.png", RequestBody.create(MediaType.parse("image/png"), new File(photoS)));
        params.put("abank_img\";filename=\"4.png", RequestBody.create(MediaType.parse("image/png"), new File(photoBz)));
        params.put("abank_back_img\";filename=\"5.png", RequestBody.create(MediaType.parse("image/png"), new File(photoBf)));
        presenter.applySell(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_look: //查看申请进度
                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), LookApplyActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.layout_upload_z: //上传身份证正面
                openSelect(false, 1);
                flag = 0;
                break;
            case R.id.layout_upload_f: //上传身份证反面
                openSelect(false, 1);
                flag = 1;
                break;
            case R.id.layout_upload_s: //手持身份证
                openSelect(false, 1);
                flag = 2;
                break;
            case R.id.layout_upload_yz: //银行卡正面
                openSelect(false, 1);
                flag = 3;
                break;
            case R.id.layout_upload_yf: //银行卡反面
                openSelect(false, 1);
                flag = 4;
                break;
            case R.id.text_apply: //提交
//                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false) && !SharedPreferencesUtil.getInstance(getActivity()).getString("isCommit").equals(SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id")+"success")){
//                    commit();
//                } else if(!SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
//                    intent = new Intent(getActivity(), LoginActivity.class);
//                    startActivity(intent);
//                } else if(SharedPreferencesUtil.getInstance(getActivity()).getString("isCommit").equals(SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id")+"success")){
//                    Toast.makeText(getActivity(), "您已提交过申请", Toast.LENGTH_SHORT).show();
//                }
                if(!SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    commit();
                }
                break;
        }
    }

    @Override
    public void takeSuccess(final TResult result) {
        super.takeSuccess(result);
        switch (flag){
            case 0: //正面
                ll_upload_z.setVisibility(View.GONE);
                rl_z.setVisibility(View.VISIBLE);
                photoZ = result.getImages().get(0).getCompressPath();
                File file = new File(photoZ);
                Glide.with(getActivity())
                        .load(photoZ)
                        .into(iv_z);
                iv_z.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(getActivity(), PhotoViewActivity.class);
                        intent.putExtra("url", photoZ);
                        startActivity(intent);
                    }
                });
                iv_z_d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll_upload_z.setVisibility(View.VISIBLE);
                        rl_z.setVisibility(View.GONE);
                    }
                });
                break;
            case 1: //反面
                ll_upload_f.setVisibility(View.GONE);
                rl_f.setVisibility(View.VISIBLE);
                photoF = result.getImages().get(0).getCompressPath();
                Glide.with(getActivity())
                        .load(photoF)
                        .into(iv_f);
                iv_f.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(getActivity(), PhotoViewActivity.class);
                        intent.putExtra("url", photoF);
                        startActivity(intent);
                    }
                });
                iv_f_d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll_upload_f.setVisibility(View.VISIBLE);
                        rl_f.setVisibility(View.GONE);
                    }
                });
                break;
            case 2: //手持身份证
                ll_upload_s.setVisibility(View.GONE);
                rl_s.setVisibility(View.VISIBLE);
                photoS = result.getImages().get(0).getCompressPath();
                Glide.with(getActivity())
                        .load(photoS)
                        .into(iv_s);
                iv_s.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(getActivity(), PhotoViewActivity.class);
                        intent.putExtra("url", photoS);
                        startActivity(intent);
                    }
                });
                iv_s_d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll_upload_s.setVisibility(View.VISIBLE);
                        rl_s.setVisibility(View.GONE);
                    }
                });
                break;
            case 3: //银行卡正面
                ll_upload_yz.setVisibility(View.GONE);
                rl_bz.setVisibility(View.VISIBLE);
                photoBz = result.getImages().get(0).getCompressPath();
                Glide.with(getActivity())
                        .load(photoBz)
                        .into(iv_bz);
                iv_bz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(getActivity(), PhotoViewActivity.class);
                        intent.putExtra("url", photoBz);
                        startActivity(intent);
                    }
                });
                iv_bz_d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll_upload_yz.setVisibility(View.VISIBLE);
                        rl_bz.setVisibility(View.GONE);
                    }
                });
                break;
            case 4: //银行卡反面
                ll_upload_yf.setVisibility(View.GONE);
                rl_bf.setVisibility(View.VISIBLE);
                photoBf = result.getImages().get(0).getCompressPath();
                Glide.with(getActivity())
                        .load(photoBf)
                        .into(iv_bf);
                iv_bf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(getActivity(), PhotoViewActivity.class);
                        intent.putExtra("url", photoBf);
                        startActivity(intent);
                    }
                });
                iv_bf_d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll_upload_yf.setVisibility(View.VISIBLE);
                        rl_bf.setVisibility(View.GONE);
                    }
                });
                break;
        }
    }

    /**
     * 清空数据
     */
    private void clearData(){
        et_name.setText("");
        et_tel.setText("");
        et_code.setText("");
        et_bank.setText("");
        et_bank_info.setText("");
        et_account.setText("");
        et_invite.setText("");
        ll_upload_z.setVisibility(View.VISIBLE);
        rl_z.setVisibility(View.GONE);
        ll_upload_f.setVisibility(View.VISIBLE);
        rl_f.setVisibility(View.GONE);
        ll_upload_s.setVisibility(View.VISIBLE);
        rl_s.setVisibility(View.GONE);
        ll_upload_yz.setVisibility(View.VISIBLE);
        rl_bz.setVisibility(View.GONE);
        ll_upload_yf.setVisibility(View.VISIBLE);
        rl_bf.setVisibility(View.GONE);
    }

    @Override
    public void applySell(ResultInfo<SellApply> resultInfo) {
        clearData();
        SharedPreferencesUtil.getInstance(getActivity()).setString("isCommit", SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id")+"success");
        disabled = resultInfo.getData().getDisabled();
        if(disabled == 1){
            Toast.makeText(getActivity(), "恭喜您已经升级为卖家，请重新登录APP", Toast.LENGTH_SHORT).show();
//            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("is_store", 1);
//            RxBusMsg msg = new RxBusMsg();
//            msg.setCarType(1);
//            msg.setSell(1);
//            RxBus.get().post("rxBusMsg", msg);
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
            startActivity(new Intent(CarApplication.getInstance(), LoginActivity.class));
        } else if(disabled == 0){
//            RxBusMsg msg  = new RxBusMsg();
//            RxBus.get().post("isCommit", msg);
            intent = new Intent(getActivity(), LookApplyActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void loadError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sellData(Result<SellAdv> resultInfo) {
        advs = resultInfo.getData();
        images.clear();
        for(int i = 0; i < resultInfo.getData().size(); i++){
            images.add(resultInfo.getData().get(i).getAtturl());
        }
        banner.setImages(images);
        banner.isAutoPlay(true);
        banner.start();
    }

    @Override
    public void sellError(Result<SellAdv> result) {

    }

    @Override
    public void advCount(Result_comment result_comment) {

    }

    @Override
    public void countError(String message) {

    }
}
