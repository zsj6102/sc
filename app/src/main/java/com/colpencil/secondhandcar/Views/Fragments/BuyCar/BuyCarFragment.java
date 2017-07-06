package com.colpencil.secondhandcar.Views.Fragments.BuyCar;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.CarResult;
import com.colpencil.secondhandcar.Bean.Response.CarResultRes;
import com.colpencil.secondhandcar.Bean.Response.Collection;
import com.colpencil.secondhandcar.Bean.Response.WordType;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Bean.RxCityMsg;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Buy.CarResultPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Ui.FlowTag.FlowTagLayout;
import com.colpencil.secondhandcar.Ui.FlowTag.OnTagClickListener;
import com.colpencil.secondhandcar.Ui.RangeSeekbar;
import com.colpencil.secondhandcar.Views.Activities.Buy.BrandClassifyActivity;
import com.colpencil.secondhandcar.Views.Activities.Buy.CarDetailsActivity;
import com.colpencil.secondhandcar.Views.Activities.Buy.ChooiceCarActivity;
import com.colpencil.secondhandcar.Views.Activities.Home.CityChooiceActivity;
import com.colpencil.secondhandcar.Views.Activities.Home.SearchActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.MineSubscribeActivity;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Adapter.Buy.BuyCarSortAdapter;
import com.colpencil.secondhandcar.Views.Adapter.ChooseCar.KeywordResultAdapter;
import com.colpencil.secondhandcar.Views.Adapter.FlowTag.KeywordTagAdapter;
import com.colpencil.secondhandcar.Views.Imples.Buy.CarResultView;
import com.google.gson.reflect.TypeToken;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

import static com.umeng.analytics.pro.x.I;

/**
 * Created by Administrator on 2017/3/22.
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_buy)
public class BuyCarFragment extends ColpencilFragment implements View.OnClickListener, RangeSeekbar.OnRangeChangedListener,
                            SwipeRefreshLayout.OnRefreshListener, CarResultView, AutoLoadRecylerView.loadMoreListener {

    @Bind(R.id.supplier_list_product_tv)
    TextView tv_sort;

    @Bind(R.id.supplier_list_product)
    LinearLayout ll_sort;

    @Bind(R.id.img_sort)
    ImageView iv_sort;

    @Bind(R.id.supplier_list_activity_tv)
    TextView tv_price;

    @Bind(R.id.img_price)
    ImageView iv_price;

    @Bind(R.id.supplier_list_activity)
    LinearLayout ll_price;

    @Bind(R.id.layout_location)
    LinearLayout ll_location;

    @Bind(R.id.supplier_list_brand)
    LinearLayout ll_brand;

    @Bind(R.id.supplier_list_chooice)
    LinearLayout ll_chooice;

    @Bind(R.id.layout_search)
    LinearLayout ll_search;

    @Bind(R.id.sp_result)
    SwipeRefreshLayout sp_result;

    @Bind(R.id.lv_result)
    AutoLoadRecylerView lv_result;

    @Bind(R.id.layout_keyword)
    LinearLayout lv_keyword;

    @Bind(R.id.fl_keyword)
    FlowTagLayout fl_keyword;

    @Bind(R.id.img_tel)
    ImageView iv_tel;

    @Bind(R.id.text_address)
    TextView tv_address;

    @Bind(R.id.layout_not_result) //没有结果
    RelativeLayout rl_not_result;

    @Bind(R.id.text_add)
    TextView tv_add;

//    @Bind(R.id.vp)
//    LinearLayout vp;

    private Intent intent;
    private Observable<RxCityMsg> observableCity;
    private Observable<RxBusMsg> observableBrand;
    private Observable<RxMsg> typeObservable;
    private BuyCarSortAdapter carSortAdapter;
    private List<String> carSorts;
    private PopupWindow popupWindow; //排序
    private PopupWindow popupWindowPrice; //筛选
    private ListView popListView;
    private RangeSeekbar rangeSeekbar;
    private TextView tv_dialog_price;
    private Button Tv_price_commit;

    private String currentSort = "默认排序"; //当前排序

    private KeywordTagAdapter keywordTagAdapter;
    private KeywordResultAdapter resultAdapter;
    private List<WordType> keywordList;
    private List<CarResult> results;
    private String p = "";

    private CarResultPresenter presenter;
    private int pageNo = 1;
    private int pageSize = 10;
    private String keyword = ""; //关键字
    private String cat = ""; //车系id
    private String type_id = ""; //类型id
    private String price = ""; //价格
    private String sort = "";
    private String mileage = ""; //公里数
    private String car_age = ""; //车龄
    private String displacement = ""; //排量
    private String transmission = "";  //变速
    private String color = ""; //颜色
    private String country = ""; //国别
    private String drive = ""; //驱动
    private String emissions = ""; //排放标准
    private String fuel = ""; //燃料类型

    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void initViews(View view) {
        lv_result.setVisibility(View.GONE);
        rl_not_result.setVisibility(View.GONE);
        sp_result.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        sp_result.setOnRefreshListener(this);
        tv_address.setText(SharedPreferencesUtil.getInstance(getActivity()).getString("cityName"));
        keywordList = new ArrayList<>();
        results = new ArrayList<>();
        keywordList.addAll(SharedPreferencesUtil.getInstance(getActivity()).getDataList("keyWord",new TypeToken<List<WordType>>() {
        }.getType()));
        if(keywordList.size() == 0 || keywordList ==  null){
            lv_keyword.setVisibility(View.GONE);
        } else {
            lv_keyword.setVisibility(View.VISIBLE);
        }
        keywordTagAdapter = new KeywordTagAdapter(getActivity());
        fl_keyword.setAdapter(keywordTagAdapter);
        keywordTagAdapter.onlyAddAll(keywordList);
        fl_keyword.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                String type = keywordList.get(position).getType();
                for(int i = 0; i < keywordList.size(); i++){
                    if(keywordList.get(i).getType().equals(type)){
                        keywordList.remove(i);
                        keywordTagAdapter.clearPosition(i);
                        if(type.equals("关键字")){
                            keyword = "";
                        } else if(type.equals("品牌")){
                            cat = "";
                        } else if(type.equals("车辆类型")){
                            type_id = "";
                        } else if(type.equals("价格")){
                            price = "";
                        } else if(type.equals("公里数")){
                            mileage = "";
                        } else if(type.equals("车龄")){
                            car_age = "";
                        } else if(type.equals("排量")){
                            displacement = "";
                        } else if(type.equals("变速")){
                            transmission = "";
                        } else if(type.equals("颜色")){
                            color = "";
                        } else if(type.equals("国别")){
                            country = "";
                        } else if(type.equals("驱动")){
                            drive = "";
                        } else if(type.equals("排放标准")){
                            emissions = "";
                        } else if(type.equals("燃料类型")){
                            fuel = "";
                        }
                        break;
                    }
                }
                SharedPreferencesUtil.getInstance(getActivity()).setDataList("keyWord", keywordList);
                if(keywordList.size() == 0 || keywordList ==  null){
                    SharedPreferencesUtil.getInstance(getActivity()).remove("keyWord");
                    lv_keyword.setVisibility(View.GONE);
                    keyword = ""; //关键字
                    cat = ""; //车系id
                    type_id = ""; //类型id
                    price = ""; //价格
                    sort = "";
                    mileage = ""; //公里数
                    car_age = ""; //车龄
                    displacement = ""; //排量
                    transmission = "";  //变速
                    color = ""; //颜色
                    country = ""; //国别
                    drive = ""; //驱动
                    emissions = ""; //排放标准
                    fuel = ""; //燃料类型
                }
                onRefresh();
            }
        });
        linearLayoutManager = new LinearLayoutManager(getActivity());
        lv_result.setLayoutManager(linearLayoutManager);
        lv_result.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                sp_result.setEnabled(linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        });
        lv_result.setLoadMoreListener(this);
        lv_result.setNestedScrollingEnabled(false);
        resultAdapter = new KeywordResultAdapter(getActivity(), results, R.layout.item_choose_keyword_result);
        lv_result.setAdapter(resultAdapter);
        resultAdapter.setOnItemClickListener(new KeywordResultAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                intent = new Intent(getActivity(), CarDetailsActivity.class);
                intent.putExtra("carId", results.get(position).getGoods_id());
                startActivity(intent);
            }
        });
        resultAdapter.setClickListener(new KeywordResultAdapter.ClickListener() {
            @Override
            public void onClick(int position) {
                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    HashMap<String, String> params = new HashMap<>();
                    params.put("member_id", SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id")+"");
                    params.put("token", SharedPreferencesUtil.getInstance(getActivity()).getString("token"));
                    params.put("goods_id", results.get(position).getGoods_id()+"");
                    presenter.collection(params);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        initBus();
        initPopWinMenu();
        //刷新
        loadCarResult();
        initListener();
    }

    /**
     * 初始化popwindow
     */
    private void initPopWinMenu(){
        initSortData();
        View view = View.inflate(getActivity(), R.layout.popwin_sort_list, null);
        View priceView = View.inflate(getActivity(), R.layout.pop_price_dialog, null);
        popupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tv_sort.setTextColor(getActivity().getResources().getColor(R.color.text_dark33));
                iv_sort.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.popwindow_uncheck));
            }
        });
        popListView = (ListView) view.findViewById(R.id.popwin_supplier_list_lv);
        popupWindowPrice = new PopupWindow(priceView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindowPrice.setOutsideTouchable(true);
        popupWindowPrice.setBackgroundDrawable(new BitmapDrawable());
        popupWindowPrice.setFocusable(true);
        popupWindowPrice.setAnimationStyle(R.style.popwin_anim_style);
        popupWindowPrice.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tv_price.setTextColor(getActivity().getResources().getColor(R.color.text_dark33));
                iv_price.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.popwindow_uncheck));
            }
        });
        rangeSeekbar = (RangeSeekbar) priceView.findViewById(R.id.seekBar);
        tv_dialog_price = (TextView) priceView.findViewById(R.id.text_price);
        rangeSeekbar.setValue(0, 50);
        rangeSeekbar.setOnRangeChangedListener(this);
        priceView.findViewById(R.id.pop_price_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindowPrice.dismiss();
            }
        });
        Tv_price_commit = (Button) priceView.findViewById(R.id.text_commit);
        view.findViewById(R.id.popwin_supplier_list_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        carSortAdapter =  new BuyCarSortAdapter(getActivity(), carSorts, R.layout.item_popwin_sort);
        popListView.setAdapter(carSortAdapter);
        popListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                popupWindow.dismiss();
                carSortAdapter.setPosition(position);
                currentSort = carSorts.get(position);
                tv_sort.setText(currentSort);
                tv_sort.setTextColor(getActivity().getResources().getColor(R.color.text_dark33));
                if(currentSort.equals("默认排序")){
                    sort = "";
                } else if(currentSort.equals("价格最高")){
                    sort = "price_desc";
                } else if(currentSort.equals("价格最低")){
                    sort = "price_asc";
                } else if(currentSort.equals("最新发布")){
                    sort = "createtime_desc";
                } else if(currentSort.equals("最短里程")){
                    sort = "mileage_asc";
                } else if(currentSort.equals("车龄最短")){
                    sort = "age_desc";
                }
                onRefresh();
            }
        });
    }

    /**
     * 初始化排序
     */
    private void initSortData(){
        carSorts = new ArrayList<>();
        carSorts.add("默认排序");
        carSorts.add("价格最高");
        carSorts.add("价格最低");
        carSorts.add("最新发布");
        carSorts.add("最短里程");
        carSorts.add("车龄最短");
    }

    private void initListener(){
        ll_sort.setOnClickListener(this);
        ll_price.setOnClickListener(this);
        Tv_price_commit.setOnClickListener(this);
        ll_location.setOnClickListener(this);
        ll_brand.setOnClickListener(this);
        ll_chooice.setOnClickListener(this);
        ll_search.setOnClickListener(this);
        iv_tel.setOnClickListener(this);
        tv_add.setOnClickListener(this);
    }

    /**
     * 初始化RxBus
     */
    private void initBus(){
        observableCity = RxBus.get().register("city", RxCityMsg.class);
        Subscriber<RxCityMsg> msgSubscriber = new Subscriber<RxCityMsg>() {
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
        observableCity.subscribe(msgSubscriber);

        observableBrand = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        Subscriber<RxBusMsg> brandSubscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final RxBusMsg rxBrandMsg) {
                if(rxBrandMsg.getType() == 3){ //品牌
                    lv_keyword.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lv_keyword.setVisibility(View.VISIBLE);
                            cat = rxBrandMsg.getCarId()+"";
                            for(int i = 0; i < keywordList.size(); i++){
                                if(keywordList.get(i).getType().equals("品牌")){
                                    keywordList.remove(i);
                                    break;
                                }
                            }
                            WordType wordType = new WordType("品牌", rxBrandMsg.getKeyword());
                            wordType.setId(cat);
                            keywordList.add(0, wordType);
                            SharedPreferencesUtil.getInstance(getActivity()).setDataList("keyWord", keywordList);
                            onRefresh();
                        }
                    },100);
                } else if(rxBrandMsg.getType() == 1){ //车辆类型
                    lv_keyword.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lv_keyword.setVisibility(View.VISIBLE);
                            type_id = rxBrandMsg.getTypeId()+"";
                            for(int i = 0; i < keywordList.size(); i++){
                                if(keywordList.get(i).getType().equals("车辆类型")){
                                    keywordList.remove(i);
                                    break;
                                }
                            }
                            WordType wordType = new WordType("车辆类型", rxBrandMsg.getKeyword());
                            wordType.setId(type_id);
                            keywordList.add(wordType);
                            SharedPreferencesUtil.getInstance(getActivity()).setDataList("keyWord", keywordList);
                            onRefresh();
                        }
                    },100);
                } else if(rxBrandMsg.getType() == 2){ //价格
                    lv_keyword.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(!rxBrandMsg.getKeyword().equals("")){
                                lv_keyword.setVisibility(View.VISIBLE);
                            }
                            for(int i = 0; i < keywordList.size(); i++){
                                if(keywordList.get(i).getType().equals("价格")){
                                    keywordList.remove(i);
                                    break;
                                }
                            }
                            if(!rxBrandMsg.getKeyword().equals("")){
                                keywordList.add(new WordType("价格", rxBrandMsg.getKeyword()));
                            }
                            SharedPreferencesUtil.getInstance(getActivity()).setDataList("keyWord", keywordList);
//                            if(rxBrandMsg.getKeyword().equals("5万以下")){
//                                price = "0_5";
//                            } else if(rxBrandMsg.getKeyword().equals("5-10万")){
//                                price = "5_10";
//                            } else if(rxBrandMsg.getKeyword().equals("15-20万")){
//                                price = "15_20";
//                            } else if(rxBrandMsg.getKeyword().equals("10-15万")){
//                                price = "10_15";
//                            } else {
//                                price = rxBrandMsg.getKeyword().substring(0, rxBrandMsg.getKeyword().length() - 1);
//                            }
                            onRefresh();
                        }
                    },100);
                } else if(rxBrandMsg.getType() == 4){ //关键字
                    //是否有关键字
                    if(rxBrandMsg.getKeyword() != null){
                        lv_keyword.setVisibility(View.VISIBLE);
                        keyword = rxBrandMsg.getKeyword();
                        for (int i = 0; i < keywordList.size(); i++){
                            if(keywordList.get(i).getType().equals("关键字")){
                                keywordList.remove(i);
                                break;
                            }
                        }
                        keywordList.add(new WordType("关键字", keyword));
                        SharedPreferencesUtil.getInstance(getActivity()).setDataList("keyWord", keywordList);
                        onRefresh();
                    }
                }
            }
        };
        observableBrand.subscribe(brandSubscriber);
        typeObservable = RxBus.get().register("typeId", RxMsg.class);
        Subscriber<RxMsg> typeSunscribe = new Subscriber<RxMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final RxMsg rxMsg) {
                lv_keyword.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        keywordList.clear();
                        keywordList.addAll(SharedPreferencesUtil.getInstance(getActivity()).getDataList("keyWord", new TypeToken<List<WordType>>(){}.getType()));
                        if(keywordList.size() > 0 && keywordList != null){
                            lv_keyword.setVisibility(View.VISIBLE);
                            type_id = rxMsg.getType()+"";
                            if(!type_id.equals("0")){
                                for(int i = 0; i < keywordList.size(); i++){
                                    if(keywordList.get(i).getType().equals("车辆类型")){
                                        keywordList.remove(i);
                                        break;
                                    }
                                }

                                WordType wordType = new WordType("车辆类型", rxMsg.getValue());
                                wordType.setId(type_id);
                                keywordList.add(wordType);
                            }
                            SharedPreferencesUtil.getInstance(getActivity()).setDataList("keyWord", keywordList);
                            onRefresh();
                        } else {
                            lv_keyword.setVisibility(View.GONE);
                        }
                    }
                },100);
            }
        };
        typeObservable.subscribe(typeSunscribe);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CarResultPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.supplier_list_product: //排序
                tv_sort.setTextColor(getResources().getColor(R.color.title_color));
                iv_sort.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.popwindow_check));
                popupWindow.showAsDropDown(ll_sort, 0, 2);
                break;
            case R.id.supplier_list_activity: // 价格
                tv_price.setTextColor(getActivity().getResources().getColor(R.color.title_color));
                iv_price.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.popwindow_check));
                popupWindowPrice.showAsDropDown(ll_price, 0, 2);
                break;
            case R.id.text_commit: //提交
                RxBusMsg msg = new RxBusMsg();
                msg.setCarType(0);
                msg.setType(2);
                msg.setKeyword(p);
                RxBus.get().post("rxBusMsg",msg);
                popupWindowPrice.dismiss();
                break;
            case R.id.layout_location: //城市选择
                intent = new Intent(getActivity(), CityChooiceActivity.class);
                intent.putExtra("type", 0);
                getActivity().startActivity(intent);
                break;
            case R.id.supplier_list_brand: //品牌
                intent = new Intent(getActivity(), BrandClassifyActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
                break;
            case R.id.supplier_list_chooice: //筛选
                intent = new Intent(getActivity(), ChooiceCarActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_search: //搜索
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.img_tel: //拨打客服
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getString("custom_mobile")));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.text_add: //添加订阅
                if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)){
                    intent = new Intent(getActivity(), MineSubscribeActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onRangeChanged(RangeSeekbar view, float min, float max, boolean isFromUser) {
        Tv_price_commit.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_home_more));
        Tv_price_commit.setEnabled(true);
        if(min == 0 && max == 50){
            tv_dialog_price.setText("不限价格");
        } else if(max == 50){
            tv_dialog_price.setText((int) min + "万以上");
        } else{
            tv_dialog_price.setText((int)min + "-" + (int)max + "万元");
        }
        if(min == 0 && max == 50){
            price = "";
            p = "";
        }else if(max == 50){
            price = (int)min+"_";
            p = (int)min + "万以上";
        }else{
            price = (int)min + "_" + (int)max;
            p = (int)min + "_" + (int)max + "万";
        }
    }

    private void loadCarResult(){
        showLoading("加载中");
        keywordTagAdapter.onlyAddAll(keywordList);
        for(WordType wordType: keywordList){
            if(wordType.getType().equals("关键字")){
                keyword = wordType.getKeyWord();
            }
            if(wordType.getType().equals("变速")){
                if(wordType.getKeyWord().equals("手动")){
                    transmission = 0+"";
                } else {
                    transmission = 1+"";
                }
            }
            if(wordType.getType().equals("颜色")){
                if(wordType.getKeyWord().equals("黑色")){
                    color = 1+"";
                } else if(wordType.getKeyWord().equals("白色")){
                    color = 2+"";
                } else if(wordType.getKeyWord().equals("银灰色")){
                    color = 3+"";
                } else if(wordType.getKeyWord().equals("红色")){
                    color = 5+"";
                } else if(wordType.getKeyWord().equals("蓝色")){
                    color = 8+"";
                } else if(wordType.getKeyWord().equals("咖啡色")){
                    color = 9+"";
                } else if(wordType.getKeyWord().equals("香槟色")){
                    color = 11+"";
                } else if(wordType.getKeyWord().equals("橙色")){
                    color = 6+"";
                } else if(wordType.getKeyWord().equals("黄色")){
                    color = 13+"";
                } else if(wordType.getKeyWord().equals("紫色")){
                    color = 10+"";
                } else if(wordType.getKeyWord().equals("绿色")){
                    color = 7+"";
                }
            }
            if(wordType.getType().equals("国别")){
                if(wordType.getKeyWord().equals("德系")){
                    country = 1+"";
                } else if(wordType.getKeyWord().equals("日系")){
                    country = 2+"";
                } else if(wordType.getKeyWord().equals("韩系")){
                    country = 5+"";
                } else if(wordType.getKeyWord().equals("美系")){
                    country = 3+"";
                } else if(wordType.getKeyWord().equals("法系")){
                    country = 4+"";
                } else if(wordType.getKeyWord().equals("国产")){
                    country = 6+"";
                } else if(wordType.getKeyWord().equals("其他")){
                    country = 7+"";
                }
            }
            if(wordType.getType().equals("驱动")){
                if(wordType.getKeyWord().equals("两驱")){
                    drive = 1+"";
                } else if(wordType.getKeyWord().equals("四驱")){
                    drive = 2+"";
                }
            }
            if(wordType.getType().equals("排放标准")){
                if(wordType.getKeyWord().equals("国二")){
                    emissions = 1+"";
                } else if(wordType.getKeyWord().equals("国三")){
                    emissions = 2+"";
                } else if(wordType.getKeyWord().equals("国四")){
                    emissions = 3+"";
                } else if(wordType.getKeyWord().equals("国五")){
                    emissions = 4+"";
                }
            }
            if(wordType.getType().equals("燃料类型")){
                if(wordType.getKeyWord().equals("汽油")){
                    fuel = 1+"";
                } else if(wordType.getKeyWord().equals("柴油")){
                    fuel = 2+"";
                } else if(wordType.getKeyWord().equals("电动")){
                    fuel = 3+"";
                } else if(wordType.getKeyWord().equals("油电混合")){
                    fuel = 4+"";
                } else if(wordType.getKeyWord().equals("其他")){
                    fuel = 5+"";
                }
            }
            if(wordType.getType().equals("价格")){
//                price = wordType.getKeyWord().substring(0, wordType.getKeyWord().length() - 1);
//                if(wordType.getKeyWord().equals("5万以下")){
//                    price = "0_5";
//                } else if(wordType.getKeyWord().equals("5-10万")){
//                    price = "5_10";
//                } else if(wordType.getKeyWord().equals("15-20万")){
//                    price = "15_20";
//                } else if(wordType.getKeyWord().equals("10-15万")){
//                    price = "10_15";
//                } else {
//                    price = wordType.getKeyWord().substring(0, wordType.getKeyWord().length() - 1);
//                }
                if(wordType.getKeyWord().equals("")){
                    price = "";
                }else{
                    if(wordType.getKeyWord().endsWith("万以上")){
                        price = wordType.getKeyWord().substring(0,wordType.getKeyWord().indexOf("万"))+"_";
                    }else{
                        price = wordType.getKeyWord().substring(0, wordType.getKeyWord().length() - 1);
                    }
                }
            }
            if(wordType.getType().equals("品牌")){
                cat = wordType.getId();
            }
            if(wordType.getType().equals("车辆类型")){
                type_id = wordType.getId();
            }
            if(wordType.getType().equals("公里数")){
                if(wordType.getKeyWord().endsWith("万公里以上")){
                    mileage = wordType.getKeyWord().substring(0, wordType.getKeyWord().indexOf("万"))+"_";
                }else{
                    mileage = wordType.getKeyWord().substring(0, wordType.getKeyWord().length() - 3);
                }

            }
            if(wordType.getType().equals("车龄")){
                if(wordType.getKeyWord().endsWith("年以上")){
                    car_age = wordType.getKeyWord().substring(0,wordType.getKeyWord().indexOf("年"))+"_";
                }else{
                    car_age = wordType.getKeyWord().substring(0, wordType.getKeyWord().length() - 1);
                }

            }
            if(wordType.getType().equals("排量")){
                if(wordType.getKeyWord().endsWith("升以上")){
                    displacement = wordType.getKeyWord().substring(0,wordType.getKeyWord().indexOf("升"))+"_";
                }else{
                    displacement = wordType.getKeyWord().substring(0, wordType.getKeyWord().length() - 1);
                }

            }
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("keyword", keyword);
        if(cat.equals("0")){
            params.put("cat", "");
        } else {
            params.put("cat", cat);
        }
        if(type_id.equals("0")){
            params.put("type_id", "");
        } else {
            params.put("type_id", type_id);
        }
        params.put("price", price);
        params.put("sort", sort);
        params.put("mileage", mileage);
        params.put("car_age", car_age);
        params.put("pageNo", pageNo+"");
        params.put("pageSize", pageSize+"");
        params.put("displacement", displacement);
        params.put("transmission", transmission);
        params.put("city_id", SharedPreferencesUtil.getInstance(getActivity()).getInt("cityId")+"");
        params.put("color", color);
        params.put("country", country);
        params.put("drive", drive);
        params.put("emissions", emissions);
        params.put("fuel", fuel);
        presenter.carResult(params);
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        loadCarResult();
        sp_result.setRefreshing(false);
    }

    @Override
    public void carResult(ResultInfo<CarResultRes> result) {
        hideLoading();
        if(result.getData() != null && result.getData().getGoodsList().size() > 0){
            rl_not_result.setVisibility(View.GONE);
            lv_result.setVisibility(View.VISIBLE);
            results.clear();
            results.addAll(result.getData().getGoodsList());
            resultAdapter.notifyDataSetChanged();
        } else {
            rl_not_result.setVisibility(View.VISIBLE);
            lv_result.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadMore(ResultInfo<CarResultRes> result) {
        hideLoading();
        lv_result.setLoading(false);
        if(result.getData().getGoodsList().size() == 0){
        } else {
            results.addAll(result.getData().getGoodsList());
            resultAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadError(String message) {
        hideLoading();
        rl_not_result.setVisibility(View.VISIBLE);
        lv_result.setVisibility(View.GONE);
    }

    @Override
    public void collection(Collection collection) {
        Toast.makeText(getActivity(), collection.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void collectionError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        loadCarResult();
    }
}
