package com.colpencil.secondhandcar.Views.Activities.Sell;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxBrandMsg;
import com.colpencil.secondhandcar.Bean.RxCityMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Sell.EditGoodPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Activities.Buy.BrandClassifyActivity;
import com.colpencil.secondhandcar.Views.Activities.Home.CityChooiceActivity;
import com.colpencil.secondhandcar.Views.Imples.Sell.EditGoodView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/6/1.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_change_good)
public class ChangeGoodActivity extends ColpencilActivity implements EditGoodView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.web_view)
    WebView webView;

    private Intent intent;
    private int goods_id;
    private Observable<RxBrandMsg> observable;
    private Observable<RxCityMsg> cityMsgObservable;
    private Observable<RxCityMsg> msgObservable;
    private EditGoodPresenter presenter;

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> uploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 100;

    @Override
    protected void initViews(View view) {
        goods_id = getIntent().getIntExtra("goods_id", 0);
        tv_title.setText("修改卖车信息");
        showLoading("加载中");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
        presenter.editGood(params);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){
            // For 3.0+ Devices (Start)
            protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                selectPhoto();
            }

            // For Lollipop 5.0+ Devices
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }
                uploadMessage = filePathCallback;
                selectPhoto();
                return true;
            }

            //For Android 4.1 only
            protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                selectPhoto();
            }

            protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                selectPhoto();
            }
        });
        webView.addJavascriptInterface(this, "releaseCar");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initBus();
    }

    private void selectPhoto(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == FILECHOOSER_RESULTCODE) {
                if (uploadMessage == null)
                    return;
                onActivityResultAboveL(requestCode, resultCode, data);
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
        else
            Toast.makeText(this, "Failed to Upload Image", Toast.LENGTH_LONG).show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILECHOOSER_RESULTCODE || uploadMessage == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessage.onReceiveValue(results);
        uploadMessage = null;
    }

    private void initBus(){
        observable = RxBus.get().register("brandName", RxBrandMsg.class);
        Subscriber<RxBrandMsg> subscriber = new Subscriber<RxBrandMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBrandMsg rxBrandMsg) {
                JSONObject object = new JSONObject();
                try {
                    object.put("catId", rxBrandMsg.getCatId());
                    object.put("typeId", rxBrandMsg.getTypeId());
                    object.put("typeName", rxBrandMsg.getTypeName());
                    object.put("catName", rxBrandMsg.getCatName());
                    webView.loadUrl("javascript:getDataFromNative("+ object.toString() +")");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        observable.subscribe(subscriber);
        cityMsgObservable = RxBus.get().register("carCity", RxCityMsg.class);
        Subscriber<RxCityMsg> subscriberCity = new Subscriber<RxCityMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxCityMsg rxCityMsg) {
                JSONObject object = new JSONObject();
                try {
                    object.put("cityId", rxCityMsg.getCityId());
                    object.put("cityName", rxCityMsg.getCity());
                    object.put("provinceId", rxCityMsg.getProvinceId());
                    webView.loadUrl("javascript:getVehicleAreaFromNative("+ object.toString() +")");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        cityMsgObservable.subscribe(subscriberCity);
        msgObservable = RxBus.get().register("carPaiCity", RxCityMsg.class);
        Subscriber<RxCityMsg> msgSubscriber = new Subscriber<RxCityMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxCityMsg rxCityMsg) {
                JSONObject object = new JSONObject();
                try {
                    object.put("cityId", rxCityMsg.getCityId());
                    object.put("cityName", rxCityMsg.getCity());
                    object.put("provinceId", rxCityMsg.getProvinceId());
                    webView.loadUrl("javascript:getPlateAreaFromNative("+ object.toString() +")");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        msgObservable.subscribe(msgSubscriber);
    }

    /**
     * 选择品牌
     * @param type
     */
    @android.webkit.JavascriptInterface
    public void actionFromJsWithParam(int type){
        intent = new Intent(this, BrandClassifyActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    /**
     * 选择城市
     * @param type
     */
    @android.webkit.JavascriptInterface
    public void cityActionFromJsWithParams(int type){
        intent = new Intent(this, CityChooiceActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    /**
     * 发布成功
     */
    @android.webkit.JavascriptInterface
    public void releaseSuccess(){
        ColpencilFrame.getInstance().finishActivity(SellCarRecordActivity.class);
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        intent = new Intent(this, SellCarRecordActivity.class);
        intent.putExtra("position", 1);
        startActivity(intent);
        ColpencilFrame.getInstance().finishActivity(this);
    }

    @android.webkit.JavascriptInterface
    public String personInfo(){
        JSONObject object = new JSONObject();
        try {
            object.put("member_id", SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getInt("member_id"));
            object.put("store_id", SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getInt("store_id"));
            object.put("token", SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getString("token"));
            object.put("goods_id", goods_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new EditGoodPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void editGood(ResultInfo<Url> resultInfo) {
        hideLoading();
        webView.loadUrl(resultInfo.getData().getUrl());
    }

    @Override
    public void loadError(String message) {
        hideLoading();
    }
}
