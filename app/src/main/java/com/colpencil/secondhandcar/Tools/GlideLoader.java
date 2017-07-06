package com.colpencil.secondhandcar.Tools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.youth.banner.loader.ImageLoader;

public class GlideLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
    }
}
