package com.colpencil.secondhandcar.Views.Fragments.Personal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.FriendRecommend;
import com.colpencil.secondhandcar.Bean.Response.Home;
import com.colpencil.secondhandcar.Bean.Response.MessageInfo;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.Subscribe;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Bean.RxCityMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Home.RecommendPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Activities.Buy.CarDetailsActivity;
import com.colpencil.secondhandcar.Views.Activities.Home.HomeRecommendActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.BalanceActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.BespokeActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.BillRecordActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.FeedbackActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.InfoActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.Message.MessageCoreActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.MineBespokeActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.MineBrowseActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.MineCollectionActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.MineDepreciateRemindActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.MineSubscribeActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.SettingActivity;
import com.colpencil.secondhandcar.Views.Activities.Sell.LookApplyActivity;
import com.colpencil.secondhandcar.Views.Activities.Sell.SellCarRecordActivity;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Adapter.Home.RecommendAdapter;
import com.colpencil.secondhandcar.Views.Imples.Home.RecommendView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/3/22.
 * 个人中心
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_mine)
public class PersonalFragment extends ColpencilFragment implements View.OnClickListener, RecommendView{

    @Bind(R.id.img_setting)
    ImageView img_setting;

    @Bind(R.id.img_message)
    ImageView img_message;

    @Bind(R.id.img_head)
    ImageView img_head;

    @Bind(R.id.text_login)
    TextView tv_login;

    @Bind(R.id.layout_collection)
    LinearLayout ll_collection;

    @Bind(R.id.list_groom)
    AutoLoadRecylerView lv_froom;

    @Bind(R.id.layout_subscribe)
    LinearLayout ll_subscribe;

    @Bind(R.id.layout_remind)
    LinearLayout ll_remind;

    @Bind(R.id.layout_bespoke)
    LinearLayout ll_bespoke;

    @Bind(R.id.layout_good)
    LinearLayout ll_good;

    @Bind(R.id.layout_order)
    LinearLayout ll_order;

    @Bind(R.id.layout_browse)
    LinearLayout ll_browse;

    @Bind(R.id.rl_feedback)
    RelativeLayout rl_feedback;

    @Bind(R.id.rl_custom)
    RelativeLayout rl_coutom;

    @Bind(R.id.text_more)
    TextView tv_more;

    @Bind(R.id.layout_release)
    LinearLayout ll_release;

    @Bind(R.id.layout_bill)
    LinearLayout ll_bill;

    @Bind(R.id.layout_balance)
    LinearLayout ll_balance;

    @Bind(R.id.layout_goods)
    LinearLayout ll_goods;

    @Bind(R.id.layout_info)
    LinearLayout ll_info;

    @Bind(R.id.layout_progress)
    LinearLayout ll_progress;

    @Bind(R.id.layout_sell_info)
    LinearLayout ll_sell_info;

    @Bind(R.id.layout_i)
    LinearLayout ll_i;

    @Bind(R.id.layout_s)
    LinearLayout ll_s;

    @Bind(R.id.line)
    View line;

    @Bind(R.id.view_info)
    View view_i;

    @Bind(R.id.view_s)
    View view_s;

    private Intent intent;
    private RecommendAdapter groomAdapter;
    private Observable<RxBusMsg> observable;
    private Observable<RxCityMsg> observableCity;
    private Observable<RxBusMsg> msgObservable;
    private RecommendPresenter presenter;

    private int pageNo = 1;
    private int pageSize = 6;
    private List<FriendRecommend> groomList = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        ll_goods.setVisibility(View.GONE);
        lv_froom.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv_froom.setNestedScrollingEnabled(false);
        groomAdapter = new RecommendAdapter(getActivity(), groomList, R.layout.item_home_recommend);
        lv_froom.setAdapter(groomAdapter);
        groomAdapter.setOnItemClickListener(new RecommendAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                intent = new Intent(getActivity(), CarDetailsActivity.class);
                intent.putExtra("carId", groomList.get(position).getGoods_id());
                startActivity(intent);
            }
        });
        if(SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getBoolean("isLogin", false)){
            tv_login.setText(SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getString("mobile"));
            if(SharedPreferencesUtil.getInstance(getActivity()).getInt("is_store") == 1){
                ll_i.setVisibility(View.VISIBLE);
                ll_s.setVisibility(View.VISIBLE);
                view_i.setVisibility(View.VISIBLE);
                view_s.setVisibility(View.VISIBLE);
                ll_sell_info.setVisibility(View.VISIBLE);
            } else {
                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false) && SharedPreferencesUtil.getInstance(getActivity()).getString("isCommit").equals(SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id")+"success")){
                    ll_i.setVisibility(View.VISIBLE);
                    ll_s.setVisibility(View.GONE);
                    view_i.setVisibility(View.VISIBLE);
                    view_s.setVisibility(View.GONE);
                    ll_sell_info.setVisibility(View.GONE);
                } else {
                    ll_i.setVisibility(View.GONE);
                    ll_s.setVisibility(View.GONE);
                    view_i.setVisibility(View.GONE);
                    view_s.setVisibility(View.GONE);
                    ll_sell_info.setVisibility(View.GONE);
                }
            }
        } else {
            ll_i.setVisibility(View.GONE);
            ll_s.setVisibility(View.GONE);
            view_i.setVisibility(View.GONE);
            view_s.setVisibility(View.GONE);
            ll_sell_info.setVisibility(View.GONE);
        }
        presenter.getRecommend(pageNo, pageSize, SharedPreferencesUtil.getInstance(getActivity()).getInt("cityId"));
        initBus();
        initListener();
    }

    private void initBus(){
        observable = RxBus.get().register("rxIsLogin", RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                if(rxBusMsg.isLogin()){
                    tv_login.setText(SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getString("mobile"));
                    if(SharedPreferencesUtil.getInstance(getActivity()).getInt("is_store") == 1){
                        ll_i.setVisibility(View.VISIBLE);
                        ll_s.setVisibility(View.VISIBLE);
                        view_i.setVisibility(View.VISIBLE);
                        view_s.setVisibility(View.VISIBLE);
                        ll_sell_info.setVisibility(View.VISIBLE);
                    } else {
                        if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false) && SharedPreferencesUtil.getInstance(getActivity()).getString("isCommit").equals(SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id")+"success")){
                            ll_i.setVisibility(View.VISIBLE);
                            ll_s.setVisibility(View.GONE);
                            view_i.setVisibility(View.VISIBLE);
                            view_s.setVisibility(View.GONE);
                            ll_sell_info.setVisibility(View.GONE);
                        } else {
                            ll_i.setVisibility(View.GONE);
                            ll_s.setVisibility(View.GONE);
                            view_i.setVisibility(View.GONE);
                            view_s.setVisibility(View.GONE);
                            ll_sell_info.setVisibility(View.GONE);
                        }
                    }
                } else {
                    tv_login.setText("立即登录");
                    ll_i.setVisibility(View.GONE);
                    ll_s.setVisibility(View.GONE);
                    view_i.setVisibility(View.GONE);
                    view_s.setVisibility(View.GONE);
                    ll_sell_info.setVisibility(View.GONE);
                }
            }
        };
        observable.subscribe(subscriber);

        observableCity = RxBus.get().register("city", RxCityMsg.class);
        Subscriber<RxCityMsg> subscriberCity = new Subscriber<RxCityMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxCityMsg rxCityMsg) {
                presenter.getRecommend(pageNo, pageSize, SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getInt("cityId"));
            }
        };
        observableCity.subscribe(subscriberCity);
//        msgObservable = RxBus.get().register("isCommit", RxBusMsg.class);
//        Subscriber<RxBusMsg> busMsgSubscriber = new Subscriber<RxBusMsg>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(RxBusMsg rxBusMsg) {
//                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false) && SharedPreferencesUtil.getInstance(getActivity()).getString("isCommit").equals(SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id")+"success")){
//                    ll_i.setVisibility(View.VISIBLE);
//                    view_i.setVisibility(View.VISIBLE);
//                } else {
//                    ll_i.setVisibility(View.GONE);
//                    view_i.setVisibility(View.GONE);
//                }
//            }
//        };
//        msgObservable.subscribe(busMsgSubscriber);
    }

    /**
     * 初始化监听
     */
    private void initListener(){
        img_setting.setOnClickListener(this);
        img_message.setOnClickListener(this);
        img_head.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        ll_collection.setOnClickListener(this);
        ll_subscribe.setOnClickListener(this);
        ll_remind.setOnClickListener(this);
        ll_bespoke.setOnClickListener(this);
        ll_good.setOnClickListener(this);
        ll_browse.setOnClickListener(this);
        rl_feedback.setOnClickListener(this);
        rl_coutom.setOnClickListener(this);
        ll_order.setOnClickListener(this);
        tv_more.setOnClickListener(this);
        ll_s.setOnClickListener(this);
        ll_bill.setOnClickListener(this);
        ll_balance.setOnClickListener(this);
        ll_i.setOnClickListener(this);
        ll_progress.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new RecommendPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_setting: //设置
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.img_message: //消息
                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), MessageCoreActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.img_head: //登录
                if(!SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.text_login: //登录
                if(!SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.layout_collection: //我的收藏
                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), MineCollectionActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.layout_subscribe: //我的订阅
                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), MineSubscribeActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.layout_remind: //降价提醒
                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), MineDepreciateRemindActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.layout_bespoke: //预约记录
                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), BespokeActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.layout_good: //商品列表
                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), SellCarRecordActivity.class);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.layout_order: //订单列表
                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), MineBespokeActivity.class);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.layout_browse: //浏览记录
                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), MineBrowseActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_feedback: //意见反馈
                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), FeedbackActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_custom: //联系客服
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getString("custom_mobile")));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.text_more: //查看更多
                intent = new Intent(getActivity(), HomeRecommendActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_s: //发布车辆
                if(SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getInt("is_store") == 1){
                    RxBusMsg sellMsg = new RxBusMsg();
                    sellMsg.setCarType(1);
                    sellMsg.setSell(1);
                    RxBus.get().post("rxBusMsg", sellMsg);
                } else {
                    RxBusMsg sellMsg = new RxBusMsg();
                    sellMsg.setCarType(1);
                    sellMsg.setSell(0);
                    RxBus.get().post("rxBusMsg", sellMsg);
                }
                break;
            case R.id.layout_bill: //分期账单
                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), BalanceActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.layout_balance: //结算记录
                intent = new Intent(getActivity(), BillRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_i: //个人信息
                intent = new Intent(getActivity(), InfoActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_progress: //申请进度
                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), LookApplyActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void refresh(Result<FriendRecommend> result) {
        if(result.getData() != null && result.getData().size() > 0){
            ll_goods.setVisibility(View.VISIBLE);
            line.setVisibility(View.GONE);
            groomList.clear();
            groomList.addAll(result.getData());
            groomAdapter.notifyDataSetChanged();
        } else {
            ll_goods.setVisibility(View.GONE);
            line.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadError(String message) {
        ll_goods.setVisibility(View.GONE);
    }

    @Override
    public void loadMore(Result<FriendRecommend> result) {

    }

    @Override
    public void homeInfo(ResultInfo<Home> resultInfo) {

    }

    @Override
    public void subscribeNum(Subscribe subscribe) {

    }

    @Override
    public void loadErrorSubscribe(String message) {

    }

    @Override
    public void advCount(Result_comment result_comment) {

    }

    @Override
    public void countError(String message) {

    }

    @Override
    public void popup(ResultInfo<MessageInfo> resultInfo) {

    }

    @Override
    public void popupError(String message) {

    }

}
