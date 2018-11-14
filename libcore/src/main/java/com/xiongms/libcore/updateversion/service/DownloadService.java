package com.xiongms.libcore.updateversion.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.xiongms.libcore.updateversion.CheckUpdateOption;
import com.xiongms.libcore.updateversion.Q;
import com.xiongms.libcore.updateversion.callback.DownloadCallback;
import com.xiongms.libcore.utils.AppUtil;
import com.xiongms.libcore.utils.NotificationUtil;

import java.io.File;

public class DownloadService extends Service implements DownloadCallback {
    private CheckUpdateOption mOption;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return START_NOT_STICKY;
        mOption = intent.getParcelableExtra("CheckUpdateOption");
        Q.download(mOption.getNewAppUrl(), mOption.getFilePath(), mOption.getFileName())
                .callback(this).execute();
        return START_STICKY;
    }

    @Override
    public void checkUpdateStart() {

    }

    @Override
    public void checkUpdateFailure(Throwable t) {
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra("CheckUpdateOption", mOption);
        startService(intent);
        NotificationUtil.showDownloadFailureNotification(this, intent, null, mOption.getNotificationIconResId(),
                mOption.getNotificationTitle(), mOption.getNotificationFailureContent(), true);
    }

    @Override
    public void checkUpdateFinish() {

    }

    @Override
    public void downloadProgress(long currentLength, long totalLength) {
        NotificationUtil.showDownloadingNotification(this, (int) currentLength, (int) totalLength, null,
                mOption.getNotificationIconResId(), mOption.getNotificationTitle(), false);
    }

    @Override
    public void downloadSuccess(File apk) {
        AppUtil.installApk(this, apk);
        NotificationUtil.showDownloadSuccessNotification(this, apk, null, mOption.getNotificationIconResId(),
                mOption.getNotificationTitle(), mOption.getNotificationSuccessContent(), false);
    }
}