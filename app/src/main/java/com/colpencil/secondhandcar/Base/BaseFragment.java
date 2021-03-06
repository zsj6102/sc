package com.colpencil.secondhandcar.Base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;

import com.colpencil.secondhandcar.R;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;

import java.io.File;

/**
 * Created by Administrator on 2017/4/1.
 * 选择照片的fragment
 */
public abstract class BaseFragment extends ColpencilFragment implements TakePhoto.TakeResultListener, InvokeListener{

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private DialogPlus dialogPlus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(getActivity(),type,invokeParam,this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public TakePhoto getTakePhoto(){
        if(takePhoto == null){
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }

    @Override
    public void takeSuccess(TResult result) {

    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    /**
     * 相册选择相关
     * @param isCrop
     * @param limit
     */
    public void openSelect(final boolean isCrop, final int limit){
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        final Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_image_picker, null);
        dialogPlus = DialogPlus.newDialog(getActivity())
                .setContentHolder(new ViewHolder(view))
                .setCancelable(true)
                .setExpanded(false)
                .create();
        view.findViewById(R.id.take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCrop) {
                    takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                } else {
                    takePhoto.onPickFromCapture(imageUri);
                }
                dialogPlus.dismiss();
            }
        });
        view.findViewById(R.id.select_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (limit == 1) {
                    if (isCrop) {
                        takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                    } else {
                        takePhoto.onPickFromGallery();
                    }

                } else {
                    takePhoto.onPickMultiple(limit);
                }
                dialogPlus.dismiss();
            }
        });
        view.findViewById(R.id.dialog_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPlus.dismiss();
            }
        });
        dialogPlus.show();
    }

    @Override
    public void takeCancel() {

    }

    private void configCompress(TakePhoto takePhoto) {
        CompressConfig config;
        config = new CompressConfig.Builder()
                .setMaxSize(102400*2) // 压缩不超过
                .setMaxPixel(800) // 压缩的尺寸大小
                .enableReserveRaw(true)  // 拍照后是否保存压缩后的图片
                .create();
        takePhoto.onEnableCompress(config, true); // 开启
    }

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true); // 使用TakePhoto自带相册
        builder.setCorrectImage(true);   // 把图片正过来
        takePhoto.setTakePhotoOptions(builder.create());
    }

    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(800).setAspectY(800);
        builder.setWithOwnCrop(true);  // 使用第三方裁剪
        return builder.create();
    }
}
