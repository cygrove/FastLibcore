package com.cygrove.libcore.download;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cygrove.libcore.R;
import com.cygrove.libcore.utils.NotificationUtil;

import java.io.File;

/**
 * Created by qiang_xi on 2016/10/7 13:56.
 * 后台下载服务
 */

public class DownloadService extends BaseService {
    private int iconResId;
    private String appName;
    private Intent mIntent;
    private Bitmap largeIcon;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null == intent) {
            return START_NOT_STICKY;
        }
        mIntent = intent;
        appName = intent.getStringExtra("appName");
        iconResId = intent.getIntExtra("iconResId", -1);
        largeIcon = intent.getParcelableExtra("largeIcon");
        if (iconResId == -1) {
            iconResId = R.mipmap.lib_ic_launcher;
        }
        download(intent.getStringExtra("downloadUrl"), intent.getStringExtra("filePath"), intent.getStringExtra("fileName"), true);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void downloadFailure(String failureMessage) {
        NotificationUtil.showDownloadFailureNotification(this, mIntent, largeIcon, iconResId, appName, "下载失败", true);
    }

    @Override
    public void downloadSuccess(File file) {
        NotificationUtil.showDownloadSuccessNotification(this, file, largeIcon, iconResId, appName, "下载完成,点击打开", false);
    }

    @Override
    public void downloading(int currentProgress, int totalProgress) {
        NotificationUtil.showDownloadingNotification(this, currentProgress, totalProgress, largeIcon, iconResId, "正在下载" + appName, false);
    }
}
