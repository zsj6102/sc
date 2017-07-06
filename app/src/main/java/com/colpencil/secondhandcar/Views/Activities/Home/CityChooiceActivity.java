package com.colpencil.secondhandcar.Views.Activities.Home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.City;
import com.colpencil.secondhandcar.Bean.Response.Province;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxCityMsg;
import com.colpencil.secondhandcar.Config.LocateState;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Home.CityPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.CharacterParser;
import com.colpencil.secondhandcar.Tools.PinYinComparator;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.colpencil.secondhandcar.Ui.BrandSideLetterBar;
import com.colpencil.secondhandcar.Views.Adapter.CityListAdapter;
import com.colpencil.secondhandcar.Views.Adapter.ResultListAdapter;
import com.colpencil.secondhandcar.Views.Imples.Home.CityView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/24.
 * 城市选择
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_search_city)
public class CityChooiceActivity extends ColpencilActivity implements View.OnClickListener, CityView{

    @Bind(R.id.listview_all_city)
    ListView lv_all_city;

    @Bind(R.id.side_letter_bar)
    BrandSideLetterBar sideLetterBar;

    @Bind(R.id.listview_search_result)
    ListView lv_search;

    @Bind(R.id.et_search)
    EditText et_search;

    @Bind(R.id.empty_view)
    ViewGroup empty_view;

    @Bind(R.id.layout_back)
    LinearLayout ll_back;

    @Bind(R.id.iv_search_clear)
    ImageView iv_clear;

    private List<City> cityList = new ArrayList<>();
    private static final String[] b = {"定位", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private List<String> barList;
    private CityListAdapter cityListAdapter;
    private ResultListAdapter resultListAdapter;

    private AMapLocationClient locationClient;
    private CityPresenter presenter;
    private PinYinComparator pinYinComparator;
    private CharacterParser characterParser;
    private int type;

    @Override
    protected void initViews(View view) {
        type = getIntent().getIntExtra("type", 0);
        showLoading("加载中");
        initData();
        initLocation();
    }

    private void initData() {
        pinYinComparator = new PinYinComparator();
        characterParser = new CharacterParser();
        cityListAdapter = new CityListAdapter(this, cityList);
        cityListAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name, int cityId) {
                RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                        .createApi(CarApi.class)
                        .getCityId(name)
                        .subscribeOn(Schedulers.io())
                        .map(new Func1<ResultInfo<Province>, ResultInfo<Province>>() {
                            @Override
                            public ResultInfo<Province> call(ResultInfo<Province> resultInfo) {
                                return resultInfo;
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResultInfo<Province>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(ResultInfo<Province> resultInfo) {
                                if(resultInfo.getCode() == 1){
                                    SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("pId", resultInfo.getData().getRegion_id());
                                } else {
                                    SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("pId", 14);
                                }
                            }
                        });
                if(type == 0){
                    RxCityMsg cityMsg = new RxCityMsg();
                    cityMsg.setCity(name);
                    cityMsg.setCityId(cityId);
                    cityMsg.setProvinceId(SharedPreferencesUtil.getInstance(CityChooiceActivity.this).getInt("pId"));
                    SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("cityId",cityId);
                    RxBus.get().post("city", cityMsg);
                } else if(type == 1){
                    RxCityMsg cityMsg = new RxCityMsg();
                    cityMsg.setCity(name);
                    cityMsg.setCityId(cityId);
                    RxBus.get().post("carCity", cityMsg);
                } else if(type == 2){
                    RxCityMsg cityMsg = new RxCityMsg();
                    cityMsg.setCity(name);
                    cityMsg.setCityId(cityId);
                    cityMsg.setProvinceId(SharedPreferencesUtil.getInstance(CityChooiceActivity.this).getInt("pId"));
                    RxBus.get().post("carPaiCity", cityMsg);
                }
                finish();
            }

            @Override
            public void onLocateClick() {
                cityListAdapter.updateLocateState(LocateState.LOCATING, null, 0);
                locationClient.startLocation();
            }
        });
        cityListAdapter.setSections();
        resultListAdapter = new ResultListAdapter(this, null);
        initView();
        presenter.getCity();
    }

    private void initLocation() {
        locationClient = new AMapLocationClient(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        locationClient.setLocationOption(option);
        locationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String city = aMapLocation.getCity();
                        String district = aMapLocation.getDistrict();
                        String location = StringUtils.extractLocation(city, district);
                        int cityId = getCityId(city);
                        cityListAdapter.updateLocateState(LocateState.SUCCESS, location, cityId);
                    } else {
                        //定位失败
                        cityListAdapter.updateLocateState(LocateState.FAILED, null, 203);
                    }
                }
            }
        });
        locationClient.startLocation();
    }

    /**
     * 根据城市名获取城市id
     * @param city
     * @return
     */
    private int getCityId(String city){
        int cityId = 202;
        for(City name: cityList){
            if(name.getLocal_name().equals(city)){
                cityId = name.getRegion_id();
                return cityId;
            }
        }
        return cityId;
    }

    private void initView() {
        lv_all_city.setAdapter(cityListAdapter);
        barList = new ArrayList<>();
        for(int i = 0; i < b.length; i++){
            barList.add(b[i]);
        }
        sideLetterBar.setBarList(barList);
        sideLetterBar.setOnLetterChangedListener(new BrandSideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = cityListAdapter.getLetterPosition(letter);
                if(position != -1){
                    lv_all_city.setSelection(position);
                }
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    iv_clear.setVisibility(View.GONE);
                    empty_view.setVisibility(View.GONE);
                    lv_search.setVisibility(View.GONE);
                } else {
                    iv_clear.setVisibility(View.VISIBLE);
                    lv_search.setVisibility(View.VISIBLE);
                    List<City> result = searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        empty_view.setVisibility(View.VISIBLE);
                    } else {
                        empty_view.setVisibility(View.GONE);
                        resultListAdapter.changeData(result);
                    }
                }
            }
        });

        lv_search.setAdapter(resultListAdapter);
        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RxCityMsg cityMsg = new RxCityMsg();
                cityMsg.setCity(resultListAdapter.getItem(position).getLocal_name());
                cityMsg.setCityId(resultListAdapter.getItem(position).getRegion_id());
                SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("cityId",resultListAdapter.getItem(position).getRegion_id());
                RxBus.get().post("city", cityMsg);
                finish();
            }
        });
        iv_clear.setOnClickListener(this);
        ll_back.setOnClickListener(this);
    }

    private List<City> searchCity(String keyWord){
        List<City> result = new ArrayList<>();
        for(City city: cityList){
            if(city.getLocal_name().startsWith(keyWord) || city.getLocal_name().endsWith(keyWord)){
                result.add(city);
            }
        }
        return result;
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CityPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_search_clear:
                et_search.setText("");
                iv_clear.setVisibility(View.GONE);
                empty_view.setVisibility(View.GONE);
                lv_search.setVisibility(View.GONE);
                break;
            case R.id.layout_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stopLocation();
    }

    @Override
    public void getCity(Result<City> result) {
        hideLoading();
        cityList.clear();
        cityList.addAll(filledData(result.getData()));
        Collections.sort(cityList, pinYinComparator);
        cityListAdapter.setSections();
        cityListAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadError() {
        hideLoading();
        cityListAdapter.setSections();
    }

    /**
     * 为ListView填充数据
     * @param date
     * @return
     */
    private List<City> filledData(List<City> date){
        List<City> mSortList = new ArrayList<>();

        for(int i = 0; i < date.size(); i++){
            City sortModel = new City();
            sortModel.setRegion_id(date.get(i).getRegion_id());
            sortModel.setLocal_name(date.get(i).getLocal_name());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i).getLocal_name());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                sortModel.setPinyin(sortString.toUpperCase());
            }else{
                sortModel.setPinyin("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }
}
