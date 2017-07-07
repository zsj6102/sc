package com.colpencil.secondhandcar.Views.Fragments.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.FriendRecommend;
import com.colpencil.secondhandcar.Bean.Response.Home;
import com.colpencil.secondhandcar.Bean.Response.KeyWord;
import com.colpencil.secondhandcar.Bean.Response.MessageCount;
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
import com.colpencil.secondhandcar.Tools.GlideLoader;
import com.colpencil.secondhandcar.Ui.AlertDialog;
import com.colpencil.secondhandcar.Views.Activities.Buy.BrandClassifyActivity;
import com.colpencil.secondhandcar.Views.Activities.Buy.CarDetailsActivity;
import com.colpencil.secondhandcar.Views.Activities.Home.CityChooiceActivity;
import com.colpencil.secondhandcar.Views.Activities.Home.HomeRecommendActivity;
import com.colpencil.secondhandcar.Views.Activities.Home.SearchActivity;
import com.colpencil.secondhandcar.Views.Activities.Home.WebViewActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.Message.MessageContentActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.MineSubscribeActivity;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Adapter.Home.HomeParameterAdapter;
import com.colpencil.secondhandcar.Views.Adapter.Home.RecommendAdapter;
import com.colpencil.secondhandcar.Views.Imples.Home.RecommendView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/3/22.
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_home)
public class HomeFragment extends ColpencilFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, RecommendView{

    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe_home;

    @Bind(R.id.layout_location)
    LinearLayout lv_location;

    @Bind(R.id.rv_classify)
    GridView rv_classify;

    @Bind(R.id.lv_recommend)
    AutoLoadRecylerView lv_recommend;

    @Bind(R.id.text_all)
    TextView tv_all;

    @Bind(R.id.banner)
    Banner banner;

    @Bind(R.id.layout_buy)
    RelativeLayout lv_buy;

    @Bind(R.id.layout_sell)
    RelativeLayout lv_sell;

    @Bind(R.id.rl_new)
    RelativeLayout lv_new;

    @Bind(R.id.rl_recommend)
    RelativeLayout ll_recommend;

    @Bind(R.id.text_more)
    TextView tv_more;

    @Bind(R.id.img_tel)
    ImageView iv_tel;

    @Bind(R.id.layout_search)
    LinearLayout lv_search;

    @Bind(R.id.text_address)
    TextView tv_address;

    @Bind(R.id.text_num)
    TextView tv_num;

    @Bind(R.id.layout_good)
    LinearLayout ll_good;

    private List<KeyWord> parameterList;
    private HomeParameterAdapter adapter;
    private RecommendAdapter recommendAdapter;
    private List<FriendRecommend> friendRecommends;
    private List<String> images = new ArrayList<>();
    private Intent intent;
    private Observable<RxCityMsg> observable;
    private RecommendPresenter presenter;
    private Home home = new Home();
    private int pageNo = 1;
    private int pageSize = 6;

    @Override
    protected void initViews(View view) {
        tv_num.setVisibility(View.GONE);
        ll_good.setVisibility(View.GONE);
        swipe_home.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipe_home.setOnRefreshListener(this);

        initBus();
        tv_address.setText(SharedPreferencesUtil.getInstance(getActivity()).getString("cityName"));
        parameterList = new ArrayList<>();
        friendRecommends = new ArrayList<>();
        banner.setImageLoader(new GlideLoader());
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                presenter.advCount(home.getAdvList().get(position).getAid());
                if(home.getAdvList().get(position).getAdv_type() == 1){ //内部
                    intent = new Intent(getActivity(), CarDetailsActivity.class);
                    intent.putExtra("carId", home.getAdvList().get(position).getGoods_id());
                    startActivity(intent);
                } else { //外部
                    if(home.getAdvList().get(position).getUrl() != null){
                        intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("url", home.getAdvList().get(position).getUrl());
                        startActivity(intent);
                    } else {

                    }
                }
            }
        });
//        rv_classify.setHasFixedSize(true);
//        rv_classify.setLayoutManager(new GridLayoutManager(getActivity(), 4));
//        rv_classify.addItemDecoration(new SupportGridItemDecoration(getActivity()));
        adapter = new HomeParameterAdapter(getActivity(), parameterList, R.layout.item_home_parameter);
        rv_classify.setAdapter(adapter);
//        adapter.setOnitemclicklistener(new OnRvItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position, Object data) {
//                KeyWord keyWord = parameterList.get(position);
//                if(position == parameterList.size() - 1){ //全部
//                    intent = new Intent(getActivity(), BrandClassifyActivity.class);
//                    startActivity(intent);
//                } else{
//                    RxBusMsg busMsg = new RxBusMsg();
//                    if(position >= 0 && position < 4){ //价格
//                        busMsg.setCarType(0);
//                        busMsg.setType(2);
//                        busMsg.setKeyword(keyWord.getKey_name());
//                    } else if(position >= 4 && position < 7){ //车辆类型
//                        busMsg.setCarType(0);
//                        busMsg.setType(1);
//                        busMsg.setKeyword(keyWord.getKey_name());
//                        busMsg.setTypeId(keyWord.getType_id());
//                    } else { //品牌
//                        busMsg.setCarType(0);
//                        busMsg.setType(3);
//                        busMsg.setKeyword(keyWord.getKey_name());
//                        busMsg.setCarId(keyWord.getCat_id());
//                    }
//                    RxBus.get().post("rxBusMsg", busMsg);
//                }
//            }
//        });
        rv_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KeyWord keyWord = parameterList.get(position);
                RxBusMsg busMsg = new RxBusMsg();
                if(position >= 0 && position < 4){ //价格
                    busMsg.setCarType(0);
                    busMsg.setType(2);
                    busMsg.setKeyword(keyWord.getKey_name());
                } else if(position >= 4 && position < 8){ //车辆类型
                    busMsg.setCarType(0);
                    busMsg.setType(1);
                    busMsg.setKeyword(keyWord.getKey_name());
                    busMsg.setTypeId(keyWord.getType_id());
                } else { //品牌
                    busMsg.setCarType(0);
                    busMsg.setType(3);
                    busMsg.setKeyword(keyWord.getKey_name());
                    busMsg.setCarId(keyWord.getCat_id());
                }
                RxBus.get().post("rxBusMsg", busMsg);
            }
        });
        lv_recommend.setNestedScrollingEnabled(false);
        lv_recommend.setLayoutManager(new LinearLayoutManager(getActivity()));
        recommendAdapter = new RecommendAdapter(getActivity(), friendRecommends, R.layout.item_home_recommend);
        lv_recommend.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(new RecommendAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                intent = new Intent(getActivity(), CarDetailsActivity.class);
                intent.putExtra("carId", friendRecommends.get(position).getGoods_id());
                startActivity(intent);
            }
        });
        presenter.getRecommend(pageNo, pageSize, SharedPreferencesUtil.getInstance(getActivity()).getInt("cityId"));
        presenter.homeInfo();
        if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
            HashMap<String, String> params = new HashMap<>();
            params.put("member_id", SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getInt("member_id")+"");
            params.put("token", SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getString("token"));
            presenter.subscribeNum(params);
        }
        presenter.popup();
        initListener();
    }

    private void initBus(){
        observable = RxBus.get().register("city", RxCityMsg.class);
        Subscriber<RxCityMsg> subscriber = new Subscriber<RxCityMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxCityMsg rxCityMsg) {
                tv_address.setText(rxCityMsg.getCity());
                onRefresh();
            }
        };
        observable.subscribe(subscriber);
    }

    /**
     * 初始化listener
     */
    private void initListener(){
        lv_location.setOnClickListener(this);
        lv_buy.setOnClickListener(this);
        lv_sell.setOnClickListener(this);
        lv_new.setOnClickListener(this);
        ll_recommend.setOnClickListener(this);
        tv_more.setOnClickListener(this);
        iv_tel.setOnClickListener(this);
        lv_search.setOnClickListener(this);
        tv_all.setOnClickListener(this);
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
    public void onRefresh() {
        pageNo = 1;
        presenter.getRecommend(pageNo, pageSize, SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getInt("cityId"));
        presenter.homeInfo();
        swipe_home.setRefreshing(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_location: //城市选择
                intent = new Intent(getActivity(), CityChooiceActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
                break;
            case R.id.text_all: //全部车源
                intent = new Intent(getActivity(), BrandClassifyActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_buy: //买车
                RxBusMsg buyMsg = new RxBusMsg();
                buyMsg.setCarType(0);
                buyMsg.setCarClassify("厦门");
                RxBus.get().post("rxBusMsg", buyMsg);
                break;
            case R.id.layout_sell: //卖车
                RxBusMsg sellMsg = new RxBusMsg();
                sellMsg.setCarType(1);
                RxBus.get().post("rxBusMsg", sellMsg);
                break;
            case R.id.rl_new: //新上车辆
                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), MineSubscribeActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_recommend: //好车推荐
                intent = new Intent(getActivity(), HomeRecommendActivity.class);
                startActivity(intent);
                break;
            case R.id.text_more: //查看更多
                intent = new Intent(getActivity(), HomeRecommendActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_search: //搜索
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.img_tel: //客服
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getString("custom_mobile")));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void refresh(Result<FriendRecommend> result) {
        if(result.getData() != null && result.getData().size() > 0){
            ll_good.setVisibility(View.VISIBLE);
            friendRecommends.clear();
            friendRecommends.addAll(result.getData());
            recommendAdapter.notifyDataSetChanged();
        } else {
            ll_good.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadError(String message) {
        ll_good.setVisibility(View.GONE);
    }

    @Override
    public void loadMsgCount(Result<MessageCount> result) {

    }

    @Override
    public void loadMore(Result<FriendRecommend> result) {

    }

    @Override
    public void homeInfo(ResultInfo<Home> resultInfo) {
        home = resultInfo.getData();
        SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("custom_mobile",resultInfo.getData().getCustom_phone());
        images.clear();
        for(int i = 0; i < resultInfo.getData().getAdvList().size(); i++){
            images.add(resultInfo.getData().getAdvList().get(i).getAtturl());
        }
        banner.setImages(images);
        banner.isAutoPlay(true);
        banner.start();
        //参数
        parameterList.clear();
        parameterList.add(0, new KeyWord("5万以下"));
        parameterList.add(1, new KeyWord("5-10万"));
        parameterList.add(2, new KeyWord("10-15万"));
        parameterList.add(3, new KeyWord("15-20万"));

        parameterList.addAll(resultInfo.getData().getKeywordList());
//        parameterList.add(new KeyWord("全部品牌 >"));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void subscribeNum(Subscribe subscribe) {
        if(subscribe.getData() == 0){
            tv_num.setVisibility(View.GONE);
        } else {
            tv_num.setVisibility(View.VISIBLE);
            tv_num.setText("新上"+subscribe.getData()+"辆");
        }
    }

    @Override
    public void loadErrorSubscribe(String message) {
        tv_num.setVisibility(View.GONE);
    }

    @Override
    public void advCount(Result_comment result_comment) {

    }

    @Override
    public void countError(String message) {

    }

    @Override
    public void popup(final ResultInfo<MessageInfo> resultInfo) {
        if(resultInfo.getData().getId() != SharedPreferencesUtil.getInstance(getActivity()).getInt("msgId")){
            showDialog(resultInfo.getData());
        } else {
        }
    }

    @Override
    public void popupError(String message) {

    }

    private void showDialog(final MessageInfo resultInfo){
        new AlertDialog(getActivity())
                .builder()
                .setTitle("紧急消息")
                .setCancelable(false)
                .setMsg(resultInfo.getTitle())
                .setPositiveButton("查看消息", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resultInfo.setLook(true);
                        SharedPreferencesUtil.getInstance(getActivity()).setInt("msgId", resultInfo.getId());
                        intent = new Intent(getActivity(), MessageContentActivity.class);
                        intent.putExtra("id", resultInfo.getId());
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }
}
