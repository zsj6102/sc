package com.colpencil.secondhandcar.Overall;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.GlideImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/3/22.
 */

public class CarApplication extends MultiDexApplication {

    static CarApplication instance;

    private ExecutorService es;

    private ImagePicker mImagePicker;

    private AMapLocationClient mLocationClient;

    private AMapLocationClientOption mLocationClientOption;

    public AMapLocationClient getLocationClient(){
        return mLocationClient;
    }

    public AMapLocationClientOption getLocationClientOption(){
        return mLocationClientOption;
    }

    public ImagePicker getImagePicker(){
        return mImagePicker;
    }

    public static CarApplication getInstance(){
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        if("UzSimpleStorage".equals(name)){
            name = SharedPreferencesUtil.SHARED_NAME;
        }
        return super.getSharedPreferences(name, mode);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

//        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);     		// 初始化 JPush

        //初始化操作框架
        ColpencilFrame.init(this);
        //初始化日志工具
        ColpencilLogger.init();

        es = Executors.newFixedThreadPool(17);

        mImagePicker = ImagePicker.getInstance();
        mImagePicker.setImageLoader(new GlideImageLoader()); //设置图片加载器
        mImagePicker.setShowCamera(true); //设置拍照按钮
        mImagePicker.setCrop(false); //允许裁剪（单选才有效）
        mImagePicker.setSaveRectangle(true); //是否按矩形框保存
        mImagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        mImagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        mImagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        mImagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        mImagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素

        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationClientOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationClientOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationClientOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationClientOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationClientOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationClientOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationClientOption);

        initImageLoader();
    }


    private void initImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false) // default
                .cacheOnDisk(true) // default
                .showImageForEmptyUri(R.mipmap.holder) // resource or
                .showImageOnFail(R.mipmap.holder).build();

        File imageCacheDir = StorageUtils.getCacheDirectory(this, true);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCache(new UnlimitedDiskCache(imageCacheDir))
                // 自定义缓存路径
                .defaultDisplayImageOptions(options)
                .imageDownloader(
                        new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // 超时时间
                .build();// 开始构建
        // 全局初始化此配置
        ImageLoader.getInstance().init(config);
    }
}
