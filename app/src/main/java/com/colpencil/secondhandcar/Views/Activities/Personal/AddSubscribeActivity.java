package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.CarResult;
import com.colpencil.secondhandcar.Bean.Response.CarResultRes;
import com.colpencil.secondhandcar.Bean.Response.CarType;
import com.colpencil.secondhandcar.Bean.Response.MineSub;
import com.colpencil.secondhandcar.Bean.Response.Shift;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxBrandMsg;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.Present.Mine.AddsubscribePresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Ui.RangeSeekbar;
import com.colpencil.secondhandcar.Views.Activities.Buy.BrandClassifyActivity;
import com.colpencil.secondhandcar.Views.Adapter.ChooseCar.ChooseCarAdapter;
import com.colpencil.secondhandcar.Views.Adapter.ChooseCar.ChooseCarMulAdapter;
import com.colpencil.secondhandcar.Views.Imples.Mine.CarTypeView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/12.
 * 添加订阅
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_add_subscribe)
public class AddSubscribeActivity extends ColpencilActivity implements View.OnClickListener, RangeSeekbar.OnRangeChangedListener, CarTypeView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_rigth)
    TextView tv_right;

    @Bind(R.id.ll_rigth)
    RelativeLayout rl_right;

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

    @Bind(R.id.sb_price)
    RangeSeekbar sb_price;

    @Bind(R.id.text_price)
    TextView tv_price;

    @Bind(R.id.text_add)
    TextView tv_add;

    private Intent intent;

    private ChooseCarAdapter carAdapter; //车辆类型
    private ChooseCarMulAdapter shiftAdapter; //变速箱

    private List<CarType> carLists; //车辆类型
    private List<Shift> shiftLists; //变速箱

    private AddsubscribePresenter presenter;
    private Observable<RxBrandMsg> observable;
    private boolean isOk = false;

    private int catId;
    private int minAge;
    private int maxAge;
    private int minMileage;
    private int maxMileage;
    private int minPrice;
    private int maxPrice ;

    @Override
    protected void initViews(View view) {
        tv_title.setText("添加订阅");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("确定");

        carLists = new ArrayList<>();
        shiftLists = new ArrayList<>();
        shiftLists.add(new Shift("自动"));
        shiftLists.add(new Shift("手动"));

        carAdapter = new ChooseCarAdapter(this, carLists);
        shiftAdapter = new ChooseCarMulAdapter(this, shiftLists);

        gv_classify.setAdapter(carAdapter);
        gv_shift.setAdapter(shiftAdapter);

        presenter.carType();

        sb_age.setValue(0, 8);
        sb_km.setValue(0, 15);
        sb_price.setValue(0, 50);
        sb_age.setOnRangeChangedListener(this);
        sb_km.setOnRangeChangedListener(this);
        sb_price.setOnRangeChangedListener(this);

        ll_left.setOnClickListener(this);
        rl_right.setOnClickListener(this);
        tv_add.setOnClickListener(this);

        initBus();
    }

    /**
     * 初始化event
     */
    private void initBus(){
        observable = RxBus.get().register("addSub", RxBrandMsg.class);
        Subscriber<RxBrandMsg> subscriber = new Subscriber<RxBrandMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBrandMsg rxBrandMsg) {
                catId = rxBrandMsg.getCatId();
                tv_add.setText(rxBrandMsg.getCatName());
                isOk = true;
            }
        };
        observable.subscribe(subscriber);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_rigth: //确定
                if(carAdapter.getSelection() != -1 || shiftAdapter.getSelection() != -1){
                    isOk = true;
                }
                if(isOk){
                    HashMap<String, String> params = new HashMap<>();
                    params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
                    params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
                    if(carAdapter.getSelection() == -1){
                        params.put("type_ids", "");
                    } else {
                        params.put("type_ids", carLists.get(carAdapter.getSelection()).getType_id()+"");
                    }
                    if(catId == 0){
                        params.put("cat_ids", "");
                    } else {
                        params.put("cat_ids", catId+"");
                    }
                    if(shiftAdapter.getSelection() == -1){
                        params.put("transmission", "");
                    } else {
                        if(shiftLists.get(shiftAdapter.getSelection()).getOp_name().equals("手动")){
                            params.put("transmission", 0+"");
                        } else {
                            params.put("transmission", 1+"");
                        }
                    }
                    if(minAge == 0 && maxAge == 0){
                        params.put("car_age", "");     //未选择过传空
                    }else if(minAge == 0 && maxAge == 8){
                        params.put("car_age", "");     //选择过，最后变不限传空
                    }else if(minAge > 0 && maxAge == 8 ){
                        params.put("car_age",minAge+"_");   //几年以上以上按照最小值+"_"
                    } else {
                        params.put("car_age",minAge+"_"+maxAge);  //其他按最小值到最大值(含以下) 下面类似
                    }
                    if(minMileage == 0 && maxMileage == 0){
                        params.put("mileage", "");
                    }else if(minMileage == 0 && maxMileage == 15){
                        params.put("mileage", "");
                    } else if (minMileage > 0 && maxMileage == 15){
                        params.put("mileage", minMileage+"_");
                    }else {
                        params.put("mileage", minMileage+"_"+maxMileage);
                    }
                    if(minPrice == 0 && maxPrice == 0){
                        params.put("price", "");
                    } else if(minPrice == 0 && maxPrice == 50){
                        params.put("price", "");
                    } else if(minPrice > 0 && maxPrice == 50){
                        params.put("price",minPrice+"_");
                    }else {
                        params.put("price", minPrice+"_"+maxPrice);
                    }
                    presenter.addSubscribe(params);
                } else {
                    Toast.makeText(this, "至少选择一个选项", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.text_add: //添加品牌
                intent = new Intent(this, BrandClassifyActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRangeChanged(RangeSeekbar view, float min, float max, boolean isFromUser) {
        isOk = true;
        if(view == sb_age){
            if(min == 0 && max == 8){
                tv_age.setText("不限车龄");
            } else if(min ==0){
                tv_age.setText((int) max + "年以下");
            }else if(max == 8){
                tv_age.setText((int) min + "年以上");
            } else {
                tv_age.setText((int)min + "-" + (int)max + "年");
            }
            minAge = (int)min;
            maxAge = (int)max;
        } else if(view == sb_km){
            if(min == 0 && max == 15){
                tv_km.setText("不限公里");
            } else if(min == 0){
                tv_km.setText((int) max + "万公里以下");
            } else if(max == 15){
                tv_km.setText((int) min + "万公里以上");
            }else {
                tv_km.setText((int) min + "-" + (int) max + "万公里");
            }
            minMileage = (int)min;
            maxMileage = (int)max;
        } else if(view == sb_price){
            if(min == 0 && max == 50){
                tv_price.setText("不限价格");
            } else if(min == 0){
                tv_price.setText((int) max + "万元以下");
            } else if(max == 50){
                tv_price.setText((int)min + "万元以上");
            } else {
                tv_price.setText((int)min + "-" + (int)max + "万元");
            }
            minPrice = (int)min;
            maxPrice = (int)max;
        }
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
        Toast.makeText(this, "订阅成功", Toast.LENGTH_SHORT).show();
        RxMsg msg = new RxMsg();
        msg.setIsSuccess(1);
        RxBus.get().post("rxAdd", msg);
        finish();
    }

    @Override
    public void addError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void searchResult(ResultInfo<CarResultRes> result) {

    }

    @Override
    public void resultError(String message) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBrand", observable);
    }
}
