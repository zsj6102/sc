package com.colpencil.secondhandcar.Receiver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Province;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/16.
 */
public class LocationServer extends Service {

    private AMapLocationClient locationClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 初始化定位
     */
    private void initLocation(){
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
                        locationClient.stopLocation();
                        final String city = aMapLocation.getCity();
//                        String cityName = city.substring(0, city.length()-1);
                        SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("cityName", city);
                        RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_)
                                .createApi(CarApi.class)
                                .getCityId(city)
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
                                            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("provinceId", resultInfo.getData().getRegion_id());
                                            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("cityId", resultInfo.getData().getCity_id());
                                        } else {
                                            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("cityName", "莆田市");
                                            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("cityId", 203);
                                            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("provinceId", 14);
                                        }
                                    }
                                });
//                        RxCityMsg cityMsg = new RxCityMsg();
//                        cityMsg.setCity(city);
//                        RxBus.get().post("city", cityMsg);
                    } else {
                        //定位失败
                        locationClient.stopLocation();
                        Toast.makeText(CarApplication.getInstance(), "当前定位失败，已为您自动选择莆田市", Toast.LENGTH_SHORT).show();
                        SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("cityName", "莆田市");
                        SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("cityId", 203);
                        SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("provinceId", 14);
                    }
                }
            }
        });
        locationClient.startLocation(); //开始定位
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationClient != null) {
            locationClient.stopLocation();
        }
    }
}
