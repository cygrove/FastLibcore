package com.cygrove.libcore.qrcode.mvp;

import android.Manifest;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bumptech.glide.request.FutureTarget;
import com.cygrove.libcore.R;
import com.cygrove.libcore.glide.GlideApp;
import com.cygrove.libcore.mvp.BaseMVPActivity;
import com.cygrove.libcore.utils.ToastUtil;
import com.cygrove.widget.qr_scan.WeakLightZXingView;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ScanActivity extends BaseMVPActivity<ScanPersenter> implements Contract.View, QRCodeView.Delegate {
    @BindView(R.id.zxingview)
    WeakLightZXingView zxingview;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_scan;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        zxingview.setDelegate(this);
        zxingview.setWeakLightListener(isWeakLight -> zxingview.getScanBoxView().setWeakLight(isWeakLight));
        zxingview.getScanBoxView().setOnFlashLightStateChangeListener(isOpenFlashLight -> {
            if (isOpenFlashLight) {
                zxingview.openFlashlight();
            } else {
                zxingview.closeFlashlight();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            startScann();
        } else {
            requestCodeQRCodePermissions();
        }
    }

    @AfterPermissionGranted(110)
    private void startScann() {
        zxingview.startCamera();
        zxingview.startSpot();
    }

    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "识别二维码/条码需要摄像头权限", 110, perms);
        }
    }

    @Override
    protected void onResume() {
        zxingview.showScanRect();
        super.onResume();
    }

    @Override
    protected void onStop() {
        zxingview.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        zxingview.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        ToastUtil.show(result);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        showToast("打开相机失败");
    }

    /**
     * 子线程执行识别操作
     *
     * @param selectedImageUri
     */
    private void scanImgTask(final Uri selectedImageUri) {
        new Thread(() -> {
            try {
                FutureTarget<Bitmap> future = GlideApp.with(getContext())
                        .asBitmap()
                        .load(selectedImageUri)
                        .submit(800, 800);
                Bitmap bitmap = future.get();

                String result = QRCodeDecoder.syncDecodeQRCode(bitmap);
                if (result != null && result.length() > 0) {
                    onScanQRCodeSuccess(result);
                    return;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            ToastUtil.show("识别失败");
        }).start();
    }
}