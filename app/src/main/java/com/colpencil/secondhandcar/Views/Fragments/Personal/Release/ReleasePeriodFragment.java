package com.colpencil.secondhandcar.Views.Fragments.Personal.Release;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.GoodsInfo;
import com.colpencil.secondhandcar.Bean.Response.Installment;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.Present.Sell.AddGoodsPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Activities.Sell.SellCarRecordActivity;
import com.colpencil.secondhandcar.Views.Adapter.Sell.SellPeriodNumAdapter;
import com.colpencil.secondhandcar.Views.Imples.Sell.AddGoodsView;
import com.google.gson.reflect.TypeToken;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

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
 * Created by Administrator on 2017/4/18.
 * 车辆是否可分期
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_release_period)
public class ReleasePeriodFragment extends ColpencilFragment implements View.OnClickListener, AddGoodsView{

    @Bind(R.id.cb_period)
    CheckBox cb_period;

    @Bind(R.id.list_period)
    ListView lv_period;

    @Bind(R.id.text_release)
    TextView tv_release;

    private AddGoodsPresenter presenter;
    private SellPeriodNumAdapter adapter;
    private List<Installment> installmentList = new ArrayList<>();
    private StringBuffer period = new StringBuffer();
    private Observable<RxMsg> observable;
    private GoodsInfo goodsInfo;

    @Override
    protected void initViews(View view) {
        //选中可分期显示分期期数
       cb_period.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(buttonView.isChecked()){
                   lv_period.setVisibility(View.VISIBLE);
                   SharedPreferencesUtil.getInstance(getActivity()).setInt("isPeriod", 1);
               } else {
                   lv_period.setVisibility(View.GONE);
                   SharedPreferencesUtil.getInstance(getActivity()).setInt("isPeriod", 0);
               }
           }
       });
        adapter = new SellPeriodNumAdapter(getActivity(), installmentList, R.layout.item_sell_period_num);
        lv_period.setAdapter(adapter);
        adapter.setCheckBoxListener(new SellPeriodNumAdapter.CheckBoxListener() {
            @Override
            public void checked(int position) {
                if(position == 0){
                    period.append(installmentList.get(position).getInstallment());
                } else {
                    period.append(","+installmentList.get(position).getInstallment());
                }
                SharedPreferencesUtil.getInstance(getActivity()).setString("periodNum", period.toString());
            }
        });
        presenter.getPeriodNum();

        tv_release.setOnClickListener(this);
        initBus();
    }

    private void initBus(){
        observable = RxBus.get().register("release", RxMsg.class);
        Subscriber<RxMsg> subscriber = new Subscriber<RxMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxMsg rxMsg) {
                for(int i = 0; i < installmentList.size(); i++){
                    installmentList.get(i).setIxChecked(false);
                }
                adapter.notifyDataSetChanged();
            }
        };
        observable.subscribe(subscriber);
    }

    /**
     * 发布提交
     */
    private void releaseCar(){
        HashMap<String, RequestBody> params = new HashMap<>();
        String color = SharedPreferencesUtil.getInstance(getActivity()).getString("color");
        String country = SharedPreferencesUtil.getInstance(getActivity()).getString("nation");
        String drive = SharedPreferencesUtil.getInstance(getActivity()).getString("drive");
        String emissions = SharedPreferencesUtil.getInstance(getActivity()).getString("let");
        String fuel = SharedPreferencesUtil.getInstance(getActivity()).getString("fuel");
        String pic1 = SharedPreferencesUtil.getInstance(getActivity()).getString("zCPic");
        String pic2 = SharedPreferencesUtil.getInstance(getActivity()).getString("zQPic");
        String pic3 = SharedPreferencesUtil.getInstance(getActivity()).getString("cMPic");
        String pic4 = SharedPreferencesUtil.getInstance(getActivity()).getString("qPPic");
        String pic5 = SharedPreferencesUtil.getInstance(getActivity()).getString("hPPic");
        String pic6 = SharedPreferencesUtil.getInstance(getActivity()).getString("zKPic");
        String pic7 = SharedPreferencesUtil.getInstance(getActivity()).getString("fDPic");
        List<String> picList = new ArrayList<>();
        picList.addAll(SharedPreferencesUtil.getInstance(getActivity()).getDataList("more",new TypeToken<List<String>>() {
        }.getType()));
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("name") == null){
            Toast.makeText(getActivity(), "请输入车主", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("goodKm") == null){
            Toast.makeText(getActivity(), "请输入里程数", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("startTime") == null){
            Toast.makeText(getActivity(), "请选择上牌时间", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("surveyTime") == null){
            Toast.makeText(getActivity(), "请选择年检到期时间", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("safeTime") == null){
            Toast.makeText(getActivity(), "请选择交强险到期时间", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("commercialTime") == null){
            Toast.makeText(getActivity(), "请选择商业险到期时间", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("ghNumber") == null){
            Toast.makeText(getActivity(), "请选择过户次数", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("bill") == null){
            Toast.makeText(getActivity(), "请选择有无购车发票", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("care") == null){
            Toast.makeText(getActivity(), "请选择是否4S店保养", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("urgent") == null){
            Toast.makeText(getActivity(), "请选择是否急售", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("local") == null){
            Toast.makeText(getActivity(), "请选择是否是当地车牌", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("modify") == null){
            Toast.makeText(getActivity(), "请选择有无改装", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("attest") == null){
            Toast.makeText(getActivity(), "请选择有无车辆购置税证明", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("carInfo") == null){
            Toast.makeText(getActivity(), "请输入卖车简介", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("goodPrice") == null){
            Toast.makeText(getActivity(), "请输入车辆售价", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("goodRevenue") == null){
            Toast.makeText(getActivity(), "请输入车辆含税价", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pic1 == null){
            Toast.makeText(getActivity(), "请上传车辆正侧图", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pic2 == null){
            Toast.makeText(getActivity(), "请上传车辆正前图", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pic3 == null){
            Toast.makeText(getActivity(), "请上传车辆车门图", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pic4 == null){
            Toast.makeText(getActivity(), "请上传车辆前排图", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pic5 == null){
            Toast.makeText(getActivity(), "请上传车辆后排图", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pic6 == null){
            Toast.makeText(getActivity(), "请上传车辆中控图", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pic7 == null){
            Toast.makeText(getActivity(), "请上传发动机舱图", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("output") == null){
            Toast.makeText(getActivity(), "请输入车辆排量", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("box") == null){
            Toast.makeText(getActivity(), "请选择车辆变速箱类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if(color == null){
            Toast.makeText(getActivity(), "请选择车身颜色", Toast.LENGTH_SHORT).show();
            return;
        }
        if(country == null){
           Toast.makeText(getActivity(), "请选择国别", Toast.LENGTH_SHORT).show();
            return;
        }
        if(drive == null){
            Toast.makeText(getActivity(), "请选择车辆驱动类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if(emissions == null){
            Toast.makeText(getActivity(), "请选择车辆排放标准", Toast.LENGTH_SHORT).show();
            return;
        }
        if(fuel == null){
            Toast.makeText(getActivity(), "请选择燃料类型", Toast.LENGTH_SHORT).show();
            return;
        }
        params.put("cat_id", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getInt("sellCarId")+""));
        params.put("owner", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("name")));
        params.put("province_id", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getInt("clProvince")+""));
        params.put("city_id", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getInt("clCity")+""));
        params.put("region_id", OkhttpUtils.toRequestBody(""));
        params.put("mileage", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("goodKm")));
        params.put("listed_time", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("startTime")));
        params.put("annual_inspection_time", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("surveyTime")));
        params.put("strong_risk_time", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("safeTime")));
        params.put("business_risk_time", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("commercialTime")));
        params.put("change_num", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("ghNumber")));
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("bill").equals("有")){
            params.put("is_invoice", OkhttpUtils.toRequestBody(1+""));
        } else if(SharedPreferencesUtil.getInstance(getActivity()).getString("bill").equals("无")){
            params.put("is_invoice", OkhttpUtils.toRequestBody(0+""));
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("care").equals("是")){
            params.put("is_four_s", OkhttpUtils.toRequestBody(1+""));
        } else if(SharedPreferencesUtil.getInstance(getActivity()).getString("care").equals("否")){
            params.put("is_four_s", OkhttpUtils.toRequestBody(0+""));
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("urgent").equals("是")){
            params.put("is_urgent", OkhttpUtils.toRequestBody(1+""));
        } else if(SharedPreferencesUtil.getInstance(getActivity()).getString("urgent").equals("否")){
            params.put("is_urgent", OkhttpUtils.toRequestBody(0+""));
        }
        params.put("card_province_id",OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getInt("cpProvince")+""));
        params.put("card_city_id", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getInt("cpCity")+""));
        params.put("card_region_id", OkhttpUtils.toRequestBody(""));
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("local").equals("是")){
            params.put("is_local_license_plate", OkhttpUtils.toRequestBody(1+""));
        } else if(SharedPreferencesUtil.getInstance(getActivity()).getString("local").equals("否")){
            params.put("is_local_license_plate", OkhttpUtils.toRequestBody(0+""));
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("modify").equals("有")){
            params.put("is_modified", OkhttpUtils.toRequestBody(1+""));
        } else if(SharedPreferencesUtil.getInstance(getActivity()).getString("modify").equals("无")){
            params.put("is_modified", OkhttpUtils.toRequestBody(0+""));
        }
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("attest").equals("有")){
            params.put("is_tax", OkhttpUtils.toRequestBody(1+""));
        } else if(SharedPreferencesUtil.getInstance(getActivity()).getString("attest").equals("无")){
            params.put("is_tax", OkhttpUtils.toRequestBody(0+""));
        }
        params.put("installment", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getInt("isPeriod")+""));
        params.put("str_installment_type", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("periodNum")));
        params.put("intro", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("carInfo")));
        params.put("price", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("goodPrice")));
        params.put("cost", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("goodRevenue")));
        params.put("type_id", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getInt("carType")+""));
        params.put("remark0", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("zC")));
        params.put("remark1", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("cM")));
        params.put("remark2", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("hP")));
        params.put("remark3", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("fD")));
        params.put("remark4", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("zQ")));
        params.put("remark5", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("qP")));
        params.put("remark6", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("zK")));
        params.put("member_id", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id")+""));
        params.put("token", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("token")));
        params.put("store_id", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getInt("store_id")+""));
        params.put("displacement", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(getActivity()).getString("output")));
        if(SharedPreferencesUtil.getInstance(getActivity()).getString("box").equals("自动")){
            params.put("transmission", OkhttpUtils.toRequestBody(1+""));
        } else {
            params.put("transmission", OkhttpUtils.toRequestBody(0+""));
        }
        if(color.equals("黑色")){
            params.put("color", OkhttpUtils.toRequestBody(1+""));
        } else if(color.equals("白色")){
            params.put("color", OkhttpUtils.toRequestBody(2+""));
        } else if(color.equals("银灰色")){
            params.put("color", OkhttpUtils.toRequestBody(3+""));
        } else if(color.equals("深灰色")){
            params.put("color", OkhttpUtils.toRequestBody(4+""));
        } else if(color.equals("红色")){
            params.put("color", OkhttpUtils.toRequestBody(5+""));
        }else if(color.equals("橙色")){
            params.put("color", OkhttpUtils.toRequestBody(6+""));
        } else if(color.equals("绿色")){
            params.put("color", OkhttpUtils.toRequestBody(7+""));
        } else if(color.equals("蓝色")){
            params.put("color", OkhttpUtils.toRequestBody(8+""));
        }else if(color.equals("咖啡色")){
            params.put("color", OkhttpUtils.toRequestBody(9+""));
        } else if(color.equals("紫色")){
            params.put("color", OkhttpUtils.toRequestBody(10+""));
        } else if(color.equals("香槟色")){
            params.put("color", OkhttpUtils.toRequestBody(11+""));
        }else if(color.equals("多彩色")){
            params.put("color", OkhttpUtils.toRequestBody(12+""));
        } else if(color.equals("黄色")){
            params.put("color", OkhttpUtils.toRequestBody(13+""));
        } else if(color.equals("其他")){
            params.put("color", OkhttpUtils.toRequestBody(14+""));
        }
        if(country.equals("德系")){
            params.put("country", OkhttpUtils.toRequestBody(1+""));
        } else if(country.equals("日系")){
            params.put("country", OkhttpUtils.toRequestBody(2+""));
        }else if(country.equals("美系")){
            params.put("country", OkhttpUtils.toRequestBody(3+""));
        }else if(country.equals("法系")){
            params.put("country", OkhttpUtils.toRequestBody(4+""));
        }else if(country.equals("韩系")){
            params.put("country", OkhttpUtils.toRequestBody(5+""));
        }else if(country.equals("国产")){
            params.put("country", OkhttpUtils.toRequestBody(6+""));
        }else if(country.equals("其他")){
            params.put("country", OkhttpUtils.toRequestBody(7+""));
        }
        if(drive.equals("两驱")){
            params.put("drive", OkhttpUtils.toRequestBody(1+""));
        } else if(drive.equals("四驱")){
            params.put("drive", OkhttpUtils.toRequestBody(2+""));
        }
        if(emissions.equals("国二")){
            params.put("emissions", OkhttpUtils.toRequestBody(1+""));
        } else if(emissions.equals("国三")){
            params.put("emissions", OkhttpUtils.toRequestBody(2+""));
        } else if(emissions.equals("国三")){
            params.put("emissions", OkhttpUtils.toRequestBody(3+""));
        } else if(emissions.equals("国五")){
            params.put("emissions", OkhttpUtils.toRequestBody(4+""));
        }
        if(fuel.equals("汽油")){
            params.put("fuel", OkhttpUtils.toRequestBody(1+""));
        } else if(fuel.equals("柴油")){
            params.put("fuel", OkhttpUtils.toRequestBody(2+""));
        } else if(fuel.equals("电动")){
            params.put("fuel", OkhttpUtils.toRequestBody(3+""));
        } else if(fuel.equals("油电混合")){
            params.put("fuel", OkhttpUtils.toRequestBody(4+""));
        } else if(fuel.equals("其他")){
            params.put("fuel", OkhttpUtils.toRequestBody(5+""));
        }
        params.put("pic0\";filename=\"0.png", RequestBody.create(MediaType.parse("image/jpg"), new File(pic1)));
        params.put("pic1\";filename=\"1.png", RequestBody.create(MediaType.parse("image/jpg"), new File(pic3)));
        params.put("pic2\";filename=\"2.png", RequestBody.create(MediaType.parse("image/jpg"), new File(pic5)));
        params.put("pic3\";filename=\"3.png", RequestBody.create(MediaType.parse("image/jpg"), new File(pic7)));
        params.put("pic4\";filename=\"4.png", RequestBody.create(MediaType.parse("image/jpg"), new File(pic2)));
        params.put("pic5\";filename=\"5.png", RequestBody.create(MediaType.parse("image/jpg"), new File(pic4)));
        params.put("pic6\";filename=\"6.png", RequestBody.create(MediaType.parse("image/jpg"), new File(pic6)));
        if(picList != null){
            for(int i = 0; i < picList.size(); i++){
                params.put("pic7\";filename=\"" + i + ".png", RequestBody.create(MediaType.parse("image/jpg"), new File(picList.get(i))));
            }
        }
        presenter.addGoods(params);
    }

    public void setPeriodInfo(GoodsInfo goodsInfo){
        this.goodsInfo = goodsInfo;
        if(goodsInfo != null){
            if(goodsInfo.getInstallment() == 1){
                cb_period.setChecked(true);
            } else {
                cb_period.setChecked(false);
            }
        }
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new AddGoodsPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void addSuccess(Result_comment result) {
        RxMsg msg = new RxMsg();
        RxBus.get().post("release", msg);
        Intent intent = new Intent(getActivity(), SellCarRecordActivity.class);
        startActivity(intent);
    }

    @Override
    public void addFaile(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getPeriodNum(Result<Installment> result) {
        installmentList.clear();
        installmentList.addAll(result.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getFaile(String message) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_release:
                releaseCar();
                break;
        }
    }
}
