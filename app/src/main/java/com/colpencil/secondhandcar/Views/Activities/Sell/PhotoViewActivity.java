package com.colpencil.secondhandcar.Views.Activities.Sell;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.PhotoView.PhotoView;
import com.property.colpencil.colpencilandroidlibrary.Ui.PhotoView.PhotoViewAttacher;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/24.
 * 大图浏览
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_photo)
public class PhotoViewActivity extends ColpencilActivity {

    @Bind(R.id.photo)
    PhotoView pv;

    private String url;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(getIntent() != null){
            url = getIntent().getStringExtra("url");
        }

        Glide.with(this)
                .load(url)
                .into(pv);

        pv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                onBackPressed();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
}
