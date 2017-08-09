package com.colpencil.secondhandcar.Views.Fragments.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.Repayment;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Mine.RepaymentPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.colpencil.secondhandcar.Views.Activities.Home.WebViewActivity;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Adapter.Mine.PeriodAdapter;
import com.colpencil.secondhandcar.Views.Imples.Mine.RepaymentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;


/**
 * Created by Administrator on 2017/5/23.
 * 待还款
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_repayment)
public class RepayMentFragment extends ColpencilFragment implements RepaymentView, SwipeRefreshLayout.OnRefreshListener, AutoLoadRecylerView.loadMoreListener {

    @Bind(R.id.list_repayment)
    AutoLoadRecylerView lv_repayment;

    @Bind(R.id.text_price)
    TextView tv_price;

    @Bind(R.id.text_pay)
    TextView tv_pay;

    @Bind(R.id.empty)
    LinearLayout empty;

    @Bind(R.id.rl_repayment)
    RelativeLayout rl_repayment;

    @Bind(R.id.sp)
    SwipeRefreshLayout sp;

    @Bind(R.id.rl_pay)
    RelativeLayout rl_pay;

    private RepaymentPresenter presenter;
    private PeriodAdapter adapter;
    private int pageNo = 1;
    private int pageSize = 10;
    private List<Repayment> repayments = new ArrayList<>();
    private double price;
    private Observable<RxMsg> observable;

    @Override
    protected void initViews(View view) {
        showLoading("加载中");
        empty.setVisibility(View.GONE);
        lv_repayment.setVisibility(View.GONE);
        rl_pay.setVisibility(View.GONE);
        sp.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        sp.setOnRefreshListener(this);
        lv_repayment.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv_repayment.setLoadMoreListener(this);
        adapter = new PeriodAdapter(getActivity(), repayments );
        lv_repayment.setAdapter(adapter);
        repayment();
        initBus();
    }

    private void initBus() {
        observable = RxBus.get().register("rxMsg", RxMsg.class);
        Subscriber<RxMsg> subscriber = new Subscriber<RxMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxMsg rxMsg) {

                price = 0;
                for (int i = 0; i < repayments.size(); i++) {
                    if (repayments.get(i).isChecked()) {
                        price += repayments.get(i).getPrice();
                    }
                }
                adapter.notifyDataSetChanged();
                tv_price.setText(StringUtils.doubleFormat(price) + "元");
            }
        };
        observable.subscribe(subscriber);
    }

    private void repayment() {

        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(getActivity()).getString("token"));
        params.put("pageNo", pageNo + "");
        params.put("pageSize", pageSize + "");
        params.put("status", 0 + "");
        presenter.repayment(params);
    }

    @OnClick(R.id.text_pay)
    void toPay() {
        int id = -1;
        for(int i = 0; i< repayments.size();i++){
            if(repayments.get(i).isChecked()){
                id = repayments.get(i).getBill_id();
            }
        }
        if(id == -1){
             Toast.makeText(getActivity(),"请选择分期",Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("url", "http://39.108.80.234/api/mobile/icbc/installment_pay.do?" + "bill_id" + "=" + id);
            startActivity(intent);
        }

    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new RepaymentPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void repayment(Result<Repayment> result) {
        hideLoading();
        if (result.getData() != null && result.getData().size() > 0) {
            empty.setVisibility(View.GONE);
            lv_repayment.setVisibility(View.VISIBLE);
            rl_pay.setVisibility(View.VISIBLE);
            repayments.clear();
            repayments.addAll(result.getData());
            adapter.notifyDataSetChanged();
        } else {
            lv_repayment.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
            rl_pay.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadMore(Result<Repayment> result) {
        hideLoading();
        lv_repayment.setLoading(false);
        if (result.getData().size() == 0) {

        } else {
            empty.setVisibility(View.GONE);
            lv_repayment.setVisibility(View.VISIBLE);
            rl_pay.setVisibility(View.VISIBLE);
            repayments.addAll(result.getData());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadError(Result<Repayment> result) {
        hideLoading();
        if (result.getCode() == -1) {
            Toast.makeText(CarApplication.getInstance(), "身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setBoolean("isLogin", false);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("member_id", 0);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("token", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("is_store", 0);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("mobile", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("isCommit", " ");
            RxBusMsg msg = new RxBusMsg();
            msg.setCarType(1);
            msg.setLogin(false);
            RxBus.get().post("rxIsLogin", msg);
            ColpencilFrame.getInstance().finishActivity(getActivity());
            startActivity(new Intent(CarApplication.getInstance(), LoginActivity.class));
        } else {
            empty.setVisibility(View.VISIBLE);
            lv_repayment.setVisibility(View.GONE);
            rl_pay.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        repayment();
        sp.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        repayment();
    }
}
