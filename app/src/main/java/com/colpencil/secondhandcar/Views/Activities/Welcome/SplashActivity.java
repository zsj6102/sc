package com.colpencil.secondhandcar.Views.Activities.Welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.colpencil.secondhandcar.Receiver.LocationServer;
import com.colpencil.secondhandcar.Views.Activities.MainActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

/**
 * Created by Administrator on 2017/4/20.
 * 启动页
 */
public class SplashActivity extends Activity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, LocationServer.class));
        new Thread() {
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(1500);
                boolean guide = SharedPreferencesUtil.getInstance(SplashActivity.this).getBoolean("guide", false);
                if (guide == false) {
                    intent = new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
