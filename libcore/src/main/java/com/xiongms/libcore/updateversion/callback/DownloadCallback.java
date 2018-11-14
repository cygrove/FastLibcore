package com.xiongms.libcore.updateversion.callback;

import java.io.File;

public interface DownloadCallback extends BaseCallback {
    /**
     * 下载进度的回调，每隔1s回调一次
     *
     * @param currentLength 当前下载的字节数
     * @param totalLength   apk的总字节数
     */
    void downloadProgress(long currentLength, long totalLength);

    /**
     * 下载成功的回调
     *
     * @param apk 下载的apk文件，可在此回调中调用安装apk方法进行安装
     */
    void downloadSuccess(File apk);

}
