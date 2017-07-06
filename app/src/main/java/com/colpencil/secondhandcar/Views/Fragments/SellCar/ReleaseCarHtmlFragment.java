package com.colpencil.secondhandcar.Views.Fragments.SellCar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Base.BaseFragment;
import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxBrandMsg;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Bean.RxCityMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Sell.HtmlUrlPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Activities.Buy.BrandClassifyActivity;
import com.colpencil.secondhandcar.Views.Activities.Home.CityChooiceActivity;
import com.colpencil.secondhandcar.Views.Activities.Sell.SellCarRecordActivity;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Imples.Sell.HtmlUrlView;
import com.jph.takephoto.model.TResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/27.
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_release_html)
public class ReleaseCarHtmlFragment extends BaseFragment implements HtmlUrlView, SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.web_view)
    WebView webView;

    @Bind(R.id.swipe)
    SwipeRefreshLayout sp;

    @Bind(R.id.empty)
    LinearLayout empty;

    private Observable<RxBrandMsg> observable;
    private Observable<RxCityMsg> cityMsgObservable;
    private Observable<RxCityMsg> msgObservable;
    private HtmlUrlPresenter presenter;
    private Intent intent;

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> uploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 100;

    @Override
    protected void initViews(View view) {
        tv_title.setText("发布卖车信息");
        iv_back.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        sp.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        sp.setEnabled(false);
//        sp.setOnRefreshListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }
        getUrl();
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
        initBus();
    }

    private void getUrl(){
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(getActivity()).getString("token"));
        presenter.htmlUrl(params);
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
            Toast.makeText(getActivity(), "Failed to Upload Image", Toast.LENGTH_LONG).show();
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
        intent = new Intent(getActivity(), BrandClassifyActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    @android.webkit.JavascriptInterface
    public String cityName(){
        JSONObject object = new JSONObject();
        try {
            object.put("cityId", SharedPreferencesUtil.getInstance(getActivity()).getInt("cityId"));
            object.put("cityName", SharedPreferencesUtil.getInstance(getActivity()).getString("cityName"));
            object.put("provinceId", SharedPreferencesUtil.getInstance(getActivity()).getInt("provinceId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    /**
     * 选择城市
     * @param type
     */
    @android.webkit.JavascriptInterface
    public void cityActionFromJsWithParams(int type){
        intent = new Intent(getActivity(), CityChooiceActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    /**
     * 发布成功
     */
    @android.webkit.JavascriptInterface
    public void releaseSuccess(){
        intent = new Intent(getActivity(), SellCarRecordActivity.class);
        intent.putExtra("position", 0);
        startActivity(intent);
    }

    @android.webkit.JavascriptInterface
    public void openSelect(){
        webView.post(new Runnable() {
            @Override
            public void run() {
                openSelect(false, 1);
            }
        });

    }

    @android.webkit.JavascriptInterface
    public String personInfo(){
        JSONObject object = new JSONObject();
        try {
            object.put("member_id", SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id"));
            object.put("store_id", SharedPreferencesUtil.getInstance(getActivity()).getInt("store_id"));
            object.put("token", SharedPreferencesUtil.getInstance(getActivity()).getString("token"));
//            webView.loadUrl("javascript:getPersonFromNative("+ object.toString() +")");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new HtmlUrlPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void htmlUrl(ResultInfo<Url> resultInfo) {
        empty.setVisibility(View.GONE);
        webView.loadUrl(resultInfo.getData().getUrl());
    }

    @Override
    public void loadError(ResultInfo<Url> resultInfo) {
        if(resultInfo.getCode() == -1){
            Toast.makeText(getActivity(), "身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setBoolean("isLogin", false);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("member_id", 0);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("token", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("is_store", 0);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("isCommit", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("mobile", " ");
            RxBusMsg msg = new RxBusMsg();
            msg.setCarType(1);
            msg.setLogin(false);
            RxBus.get().post("rxIsLogin", msg);
            startActivity(new Intent(CarApplication.getInstance(), LoginActivity.class));
        } else {
            empty.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void takeSuccess(final TResult result) {
        super.takeSuccess(result);
        webView.post(new Runnable() {
            @Override
            public void run() {
                File file = new File(result.getImages().get(0).getCompressPath());
                webView.loadUrl("javascript:getPhotoFromNative("+ file +")");
            }
        });
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void onRefresh() {
        getUrl();
        sp.setRefreshing(false);
    }
}
