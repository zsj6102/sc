package com.colpencil.secondhandcar.Views.Activities.Welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Activities.MainActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.Arrays;

import cn.bingoogolapple.bgabanner.BGABanner;


/**
 * Created by Administrator on 2017/4/20.
 * 引导页
 */
public class GuideActivity extends AppCompatActivity {

    private BGABanner mBackgroundBanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        processLogic();
    }

    private void initView(){
        setContentView(R.layout.activity_guide);
        mBackgroundBanner = (BGABanner) findViewById(R.id.banner_guide_background);
    }

    private void setListener() {
        mBackgroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                SharedPreferencesUtil.getInstance(GuideActivity.this).setBoolean("guide", true);
                finish();
            }
        });
    }

    private void processLogic() {
        mBackgroundBanner.setOverScrollMode(View.OVER_SCROLL_NEVER);

        // 初始化方式1：通过传入数据模型并结合Adapter的方式初始化
        mBackgroundBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                ((ImageView) view).setImageResource((int) model);
            }
        });
        mBackgroundBanner.setData(Arrays.asList(R.mipmap.guide_one, R.mipmap.guide_two, R.mipmap.guide_three), null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在界面可见时给背景Banner设置一个白色背景，避免滑动过程中两个Banner都设置透明度后能看到Launcher
        mBackgroundBanner.setBackgroundResource(android.R.color.white);
    }
}
