package com.colpencil.secondhandcar.Views.Activities.Buy;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.CarResult;
import com.colpencil.secondhandcar.Bean.Response.CarResultRes;
import com.colpencil.secondhandcar.Bean.Response.CarType;
import com.colpencil.secondhandcar.Bean.Response.ColorClassify;
import com.colpencil.secondhandcar.Bean.Response.MineSub;
import com.colpencil.secondhandcar.Bean.Response.Shift;
import com.colpencil.secondhandcar.Bean.Response.WordType;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.Present.Mine.AddsubscribePresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Ui.RangeSeekbar;
import com.colpencil.secondhandcar.Views.Adapter.ChooseCar.ChooseCarAdapter;
import com.colpencil.secondhandcar.Views.Adapter.ChooseCar.ChooseCarMulAdapter;
import com.colpencil.secondhandcar.Views.Adapter.ChooseCar.ChooseColorAdapter;
import com.colpencil.secondhandcar.Views.Adapter.ChooseCar.CountryAdapter;
import com.colpencil.secondhandcar.Views.Adapter.ChooseCar.DisplacementAdapter;
import com.colpencil.secondhandcar.Views.Adapter.ChooseCar.DriveAdapter;
import com.colpencil.secondhandcar.Views.Adapter.ChooseCar.FuelAdapter;
import com.colpencil.secondhandcar.Views.Adapter.ChooseCar.RecommendAdapter;
import com.colpencil.secondhandcar.Views.Imples.Mine.CarTypeView;
import com.google.gson.reflect.TypeToken;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;


/**
 * Created by Administrator on 2017/3/29.
 * 买车筛选
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_buy_chooice)
public class ChooiceCarActivity extends ColpencilActivity implements RangeSeekbar.OnRangeChangedListener, View.OnClickListener, CarTypeView {

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.gv_classify) //车辆类型
    GridView gv_classify;

    @Bind(R.id.gv_shift) //变速箱
    GridView gv_shift;

    @Bind(R.id.sb_age)
    RangeSeekbar sb_age;

    @Bind(R.id.text_age)
    TextView tv_age;

    @Bind(R.id.sb_km)
    RangeSeekbar sb_km;

    @Bind(R.id.text_km)
    TextView tv_km;

    @Bind(R.id.sb_displacement)
    RangeSeekbar sb_displacement;

    @Bind(R.id.text_displacement)
    TextView tv_displacement;

    @Bind(R.id.gv_displacement) //排放标准
    GridView gv_displacement;

    @Bind(R.id.gv_recommend) //好车推荐
    GridView gv_recommend;

    @Bind(R.id.gv_countries) //国别
    GridView gv_countries;

    @Bind(R.id.gv_fuel) //燃料类型
    GridView gv_fuel;

    @Bind(R.id.gv_drive) //驱动
    GridView gv_drive;

    @Bind(R.id.gv_color) //颜色
    GridView gv_color;

    @Bind(R.id.text_car_number)
    TextView tv_carNumber;

    @Bind(R.id.text_commit)
    TextView tv_commit;

    private ChooseCarAdapter carAdapter; //车辆类型
    private ChooseCarMulAdapter shiftAdapter; //变速箱
    private DisplacementAdapter disAdapter; //排放标准
    private RecommendAdapter recommendAdapter; //好车推荐
    private CountryAdapter countryAdapter; //国别
    private FuelAdapter fuelAdapter; //燃料类型
    private DriveAdapter driveAdapter; //驱动
    private ChooseColorAdapter colorAdapter; //颜色

    private List<CarType> carLists; //车辆类型
    private List<Shift> shiftLists; //变速箱
    private List<String> disList; //排放标准
    private List<String> recommendList; //好车推荐
    private List<String> countriesList; //国别
    private List<String> fuelList; //燃料类型
    private List<String> driveList; //驱动
    private List<ColorClassify> colorList; //颜色

    private String a = "";
    private String k = "";
    private String d = "";

    private DecimalFormat df = new DecimalFormat("0.0");

    private int carNumber = 0;
    private String age = "";
    private String km = "";
    private String dis = "";
    private AddsubscribePresenter presenter;
    private List<WordType> keyWordTypes = new ArrayList<>();
    private Observable<RxMsg> observable;
    private int pageNo = 1;
    private int pageSize = 10;

    @Override
    protected void initViews(View view) {
        tv_title.setText("高级筛选");
        tv_carNumber.setText(getResources().getString(R.string.choose_carNumber, carNumber));
        initData();
        carAdapter = new ChooseCarAdapter(this, carLists);
        shiftAdapter = new ChooseCarMulAdapter(this, shiftLists);
        disAdapter = new DisplacementAdapter(this, disList);
        recommendAdapter = new RecommendAdapter(this, recommendList);
        countryAdapter = new CountryAdapter(this, countriesList);
        fuelAdapter = new FuelAdapter(this, fuelList);
        driveAdapter = new DriveAdapter(this, driveList);
        colorAdapter = new ChooseColorAdapter(this, colorList);
        gv_classify.setAdapter(carAdapter);
        gv_shift.setAdapter(shiftAdapter);
        gv_displacement.setAdapter(disAdapter);
        gv_recommend.setAdapter(recommendAdapter);
        gv_countries.setAdapter(countryAdapter);
        gv_fuel.setAdapter(fuelAdapter);
        gv_drive.setAdapter(driveAdapter);
        gv_color.setAdapter(colorAdapter);

        presenter.carType();

        sb_age.setValue(0, 8);
        sb_km.setValue(0, 15);
        sb_displacement.setValue(0, 5);
        sb_age.setOnRangeChangedListener(this);
        sb_km.setOnRangeChangedListener(this);
        sb_displacement.setOnRangeChangedListener(this);

        ll_left.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
        initBus();
        search();
    }

    private void initBus(){
        observable = RxBus.get().register("search", RxMsg.class);
        Subscriber<RxMsg> subscriber = new Subscriber<RxMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxMsg rxMsg) {
                search();
            }
        };
        observable.subscribe(subscriber);
    }

    private void initData(){
        keyWordTypes.addAll(SharedPreferencesUtil.getInstance(this).getDataList("keyWord", new TypeToken<List<WordType>>(){}.getType()));
        carLists = new ArrayList<>();
        shiftLists = new ArrayList<>();
        disList = new ArrayList<>();
        recommendList = new ArrayList<>();
        countriesList = new ArrayList<>();
        fuelList = new ArrayList<>();
        driveList = new ArrayList<>();
        colorList = new ArrayList<>();

        shiftLists.add(new Shift("自动"));
        shiftLists.add(new Shift("手动"));
        disList.add("国五");
        disList.add("国四");
        disList.add("国三");
        disList.add("国二");
        recommendList.add("急售");
        recommendList.add("新上架");
        countriesList.add("国产");
        countriesList.add("德系");
        countriesList.add("日系");
        countriesList.add("美系");
        countriesList.add("韩系");
        countriesList.add("法系");
        countriesList.add("其他");
        fuelList.add("汽油");
        fuelList.add("柴油");
        fuelList.add("电动");
        fuelList.add("油电混合");
        fuelList.add("其他");
        driveList.add("两驱");
        driveList.add("四驱");
        colorList.add(new ColorClassify(R.mipmap.black, "黑色"));
        colorList.add(new ColorClassify(R.mipmap.white, "白色"));
        colorList.add(new ColorClassify(R.mipmap.gray, "银灰色"));
        colorList.add(new ColorClassify(R.mipmap.red, "红色"));
        colorList.add(new ColorClassify(R.mipmap.blue, "蓝色"));
        colorList.add(new ColorClassify(R.mipmap.sepia, "咖啡色"));
        colorList.add(new ColorClassify(R.mipmap.glod, "香槟色"));
        colorList.add(new ColorClassify(R.mipmap.orange, "橙色"));
        colorList.add(new ColorClassify(R.mipmap.yellow, "黄色"));
        colorList.add(new ColorClassify(R.mipmap.purple, "紫色"));
        colorList.add(new ColorClassify(R.mipmap.green, "绿色"));
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new AddsubscribePresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onRangeChanged(RangeSeekbar view, float min, float max, boolean isFromUser) {
        if(view == sb_age){
            if(min == 0 && max == 8){
                tv_age.setText("不限车龄");
                age = "";
                a = "";
            } else if(max == 8 && min>0){
                tv_age.setText((int) min + "年以上");
                age = (int)min + "_";
                a = (int)min+"年以上";
            } else if(min == 0 && max <8){
                tv_age.setText((int) max + "年以下");
                age = (int)min + "_" + (int)max;
                a = (int)min + "_" + (int)max + "年";
            }else {
                tv_age.setText((int)min + "-" + (int)max + "年");
                age = (int)min + "_" + (int)max;
                a = (int)min + "_" + (int)max + "年";
            }
            //不限的时候传空

        } else if(view == sb_km){
            //不限的时候传空
            if(min == 0 && max == 15){
                tv_km.setText("不限公里");
                km = "";
                k = "";
            } else if(min > 0 && max == 15){
                tv_km.setText((int) min + "万公里以上");
                km = (int)min + "_";
                k = (int)min +"万公里以上";
            }else if(min == 0 && max < 15){
                tv_km.setText((int) max + "万公里以下");
                km = (int)min + "_" + (int)max;
                k = (int)min + "_" + (int)max + "万公里";
            } else {
                tv_km.setText((int) min + "-" + (int) max + "万公里");
                km = (int)min + "_" + (int)max;
                k = (int)min + "_" + (int)max + "万公里";
            }
        } else if(view == sb_displacement){
            if(min == 0 && max == 5){
                tv_displacement.setText("不限排量");
                dis = "";
                d = "";
            } else if(min > 0 && max == 5){
                tv_displacement.setText(df.format(min) + "升以上");
                dis = (int)min + "_" ;
                d = (int)min+"升以上";
            } else if(min == 0 && max < 5){
                tv_displacement.setText(df.format(max) + "升以下");
                dis = (int)min + "_" + (int)max;
                d = (int)min + "_" + (int)max + "升";
            }
            else {
                tv_displacement.setText(df.format(min) + "-" + df.format(max) + "升");
                dis = (int)min + "_" + (int)max;
                d = (int)min + "_" + (int)max + "升";
            }


        }
        RxMsg msg = new RxMsg();
        RxBus.get().post("search", msg);
    }

    /**
     * 商品列表
     */
    private void search(){
        HashMap<String, String> params = new HashMap<>();
        params.put("keyword", "");
        params.put("cat", "");
        if(carAdapter.getSelection() == -1){
            params.put("type_id", "");
        } else {
            params.put("type_id", carLists.get(carAdapter.getSelection()).getType_id()+"");
        }
        params.put("price", "");
        params.put("sort", "");
        params.put("mileage", km);
        params.put("car_age", age);
        params.put("pageNo", pageNo+"");
        params.put("pageSize", pageSize+"");
        params.put("displacement", dis);
        if(shiftAdapter.getSelection() == -1){
            params.put("transmission", "");
        } else {
            if(shiftLists.get(shiftAdapter.getSelection()).getOp_name().equals("自动")){
                params.put("transmission", 1+"");
            }else {
                params.put("transmission", 0+"");
            }
        }
        params.put("city_id", SharedPreferencesUtil.getInstance(this).getInt("cityId")+"");
        if(colorAdapter.getSelection() == -1){
            params.put("color", "");
        } else {
            if(colorList.get(colorAdapter.getSelection()).getColor().equals("黑色")){
                params.put("color", 1+"");
            } else if(colorList.get(colorAdapter.getSelection()).getColor().equals("白色")){
                params.put("color", 2+"");
            } else if(colorList.get(colorAdapter.getSelection()).getColor().equals("银灰色")){
                params.put("color", 3+"");
            } else if(colorList.get(colorAdapter.getSelection()).getColor().equals("红色")){
                params.put("color", 5+"");
            } else if(colorList.get(colorAdapter.getSelection()).getColor().equals("蓝色")){
                params.put("color", 8+"");
            } else if(colorList.get(colorAdapter.getSelection()).getColor().equals("咖啡色")){
                params.put("color", 9+"");
            } else if(colorList.get(colorAdapter.getSelection()).getColor().equals("香槟色")){
                params.put("color", 11+"");
            } else if(colorList.get(colorAdapter.getSelection()).getColor().equals("橙色")){
                params.put("color", 6+"");
            } else if(colorList.get(colorAdapter.getSelection()).getColor().equals("黄色")){
                params.put("color", 13+"");
            } else if(colorList.get(colorAdapter.getSelection()).getColor().equals("紫色")){
                params.put("color", 10+"");
            } else if(colorList.get(colorAdapter.getSelection()).getColor().equals("绿色")){
                params.put("color", 7+"");
            }
        }
        if(countryAdapter.getSelection() == -1){
            params.put("country", "");
        } else {
            if(countriesList.get(countryAdapter.getSelection()).equals("德系")){
                params.put("country", 1+"");
            } else if(countriesList.get(countryAdapter.getSelection()).equals("日系")){
                params.put("country", 2+"");
            } else if(countriesList.get(countryAdapter.getSelection()).equals("韩系")){
                params.put("country", 5+"");
            } else if(countriesList.get(countryAdapter.getSelection()).equals("美系")){
                params.put("country", 3+"");
            } else if(countriesList.get(countryAdapter.getSelection()).equals("法系")){
                params.put("country", 4+"");
            } else if(countriesList.get(countryAdapter.getSelection()).equals("国产")){
                params.put("country", 6+"");
            } else if(countriesList.get(countryAdapter.getSelection()).equals("其他")){
                params.put("country", 7+"");
            }
        }
        if(driveAdapter.getSelection() == -1){
            params.put("drive", "");
        } else {
            if(driveList.get(driveAdapter.getSelection()).equals("两驱")){
                params.put("drive", 1+"");
            } else if(driveList.get(driveAdapter.getSelection()).equals("四驱")){
                params.put("drive", 2+"");
            }
        }
        if(disAdapter.getSelection() == -1){
            params.put("emissions", "");
        } else {
            if(disList.get(disAdapter.getSelection()).equals("国二")){
                params.put("emissions", 1+"");
            } else if(disList.get(disAdapter.getSelection()).equals("国三")){
                params.put("emissions", 2+"");
            } else if(disList.get(disAdapter.getSelection()).equals("国四")){
                params.put("emissions", 3+"");
            } else if(disList.get(disAdapter.getSelection()).equals("国五")){
                params.put("emissions", 4+"");
            }
        }
        if(fuelAdapter.getSelection() == -1){
            params.put("fuel", "");
        } else {
            if(fuelList.get(fuelAdapter.getSelection()).equals("汽油")){
                params.put("fuel", 1+"");
            } else if(fuelList.get(fuelAdapter.getSelection()).equals("柴油")){
                params.put("fuel", 2+"");
            } else if(fuelList.get(fuelAdapter.getSelection()).equals("电动")){
                params.put("fuel", 3+"");
            } else if(fuelList.get(fuelAdapter.getSelection()).equals("油电混合")){
                params.put("fuel", 4+"");
            } else if(fuelList.get(fuelAdapter.getSelection()).equals("其他")){
                params.put("fuel", 5+"");
            }
        }
        presenter.carResult(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.text_commit: //提交
                commit();
                break;
        }
    }

    private void commit(){
        RxMsg msg = new RxMsg();
        keyWordTypes.clear();
        keyWordTypes = SharedPreferencesUtil.getInstance(this).getDataList("keyWord", new TypeToken<List<WordType>>(){}.getType());
        if(!TextStringUtils.isEmpty(k)){
            for(WordType wordType: keyWordTypes){
                if(wordType.getType().equals("公里数")){
                    keyWordTypes.remove(wordType);
                    break;
                }
            }
            keyWordTypes.add(new WordType("公里数", k));
        }
        if(!TextStringUtils.isEmpty(a)){
            for(WordType wordType: keyWordTypes){
                if(wordType.getType().equals("车龄")){
                    keyWordTypes.remove(wordType);
                    break;
                }
            }
            keyWordTypes.add(new WordType("车龄", a));
        }
        if(!TextStringUtils.isEmpty(d)){
            for(WordType wordType: keyWordTypes){
                if(wordType.getType().equals("排量")){
                    keyWordTypes.remove(wordType);
                    break;
                }
            }
            keyWordTypes.add(new WordType("排量", d));
        }
        if(carAdapter.getSelection() != -1){
            for(WordType wordType: keyWordTypes){
                if(wordType.getType().equals("车辆类型")){
                    keyWordTypes.remove(wordType);
                    break;
                }
            }
            msg.setType(carLists.get(carAdapter.getSelection()).getType_id());
            msg.setValue(carLists.get(carAdapter.getSelection()).getType_name());
            WordType wordType = new WordType("车辆类型", carLists.get(carAdapter.getSelection()).getType_name());
            keyWordTypes.add(wordType);
        }
        if(shiftAdapter.getSelection() != -1){
            for(WordType wordType: keyWordTypes){
                if(wordType.getType().equals("变速")){
                    keyWordTypes.remove(wordType);
                    break;
                }
            }
            keyWordTypes.add(new WordType("变速", shiftLists.get(shiftAdapter.getSelection()).getOp_name()));
        }
        if(disAdapter.getSelection() != -1){
            for(WordType wordType: keyWordTypes){
                if(wordType.getType().equals("排放标准")){
                    keyWordTypes.remove(wordType);
                    break;
                }
            }
            keyWordTypes.add(new WordType("排放标准", disList.get(disAdapter.getSelection())));
        }
        if(countryAdapter.getSelection() != -1){
            for(WordType wordType: keyWordTypes){
                if(wordType.getType().equals("国别")){
                    keyWordTypes.remove(wordType);
                    break;
                }
            }
            keyWordTypes.add(new WordType("国别", countriesList.get(countryAdapter.getSelection())));
        }
        if(fuelAdapter.getSelection() != -1){
            for(WordType wordType: keyWordTypes){
                if(wordType.getType().equals("燃料类型")){
                    keyWordTypes.remove(wordType);
                    break;
                }
            }
            keyWordTypes.add(new WordType("燃料类型", fuelList.get(fuelAdapter.getSelection())));
        }
        if(driveAdapter.getSelection() != -1){
            for(WordType wordType: keyWordTypes){
                if(wordType.getType().equals("驱动")){
                    keyWordTypes.remove(wordType);
                    break;
                }
            }
            keyWordTypes.add(new WordType("驱动", driveList.get(driveAdapter.getSelection())));
        }
        if(colorAdapter.getSelection() != -1){
            for(WordType wordType: keyWordTypes){
                if(wordType.getType().equals("颜色")){
                    keyWordTypes.remove(wordType);
                    break;
                }
            }
            keyWordTypes.add(new WordType("颜色", colorList.get(colorAdapter.getSelection()).getColor()));
        }
        SharedPreferencesUtil.getInstance(this).setDataList("keyWord", keyWordTypes);
        RxBus.get().post("typeId", msg);
        ColpencilFrame.getInstance().finishActivity(this);
    }

    @Override
    public void carType(Result<CarType> resultInfo) {
        carLists.clear();
        carLists.addAll(resultInfo.getData());
        carAdapter.notifyDataSetChanged();
    }

    @Override
    public void typeError(String message) {

    }

    @Override
    public void addSunscribe(MineSub mineSub) {

    }

    @Override
    public void addError(String message) {

    }

    @Override
    public void searchResult(ResultInfo<CarResultRes> result) {
        carNumber = result.getData().getGoodsCount();
        tv_carNumber.setText(getResources().getString(R.string.choose_carNumber, carNumber));
    }

    @Override
    public void resultError(String message) {
        carNumber = 0;
        tv_carNumber.setText(getResources().getString(R.string.choose_carNumber, carNumber));
    }
}
