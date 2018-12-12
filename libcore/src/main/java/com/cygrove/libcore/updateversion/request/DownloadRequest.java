package com.cygrove.libcore.updateversion.request;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.cygrove.libcore.updateversion.callback.BaseCallback;
import com.cygrove.libcore.updateversion.callback.DownloadCallback;
import com.cygrove.libcore.updateversion.dispatcher.CallbackDispatcher;
import com.cygrove.libcore.updateversion.exception.CallbackException;
import com.cygrove.libcore.updateversion.exception.DownloadException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DownloadRequest implements IRequest {
    private String mUrl;//下载地址
    private File mApk;//apk文件
    private DownloadCallback mCallback;//下载过程的回调
    private long mLastUpdateTime;

    public DownloadRequest(String url, File file, BaseCallback callback) {
        mUrl = url;
        mApk = file;
        //检查callback类型
        if (callback != null && !(callback instanceof DownloadCallback)) {
            throw new CallbackException("DownloadRequest must be use DownloadCallback");
        }
        mCallback = (DownloadCallback) callback;
    }

    @Override
    public void request() {
        CallbackDispatcher.dispatchRequestStart(mCallback);
        OkHttpClient client = new OkHttpClient();
        //构建Request
        Request request = new Request.Builder().url(mUrl).build();
        //发起请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Logger.e(e.getMessage());
                CallbackDispatcher.dispatchRequestFailure(mCallback, e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Logger.e(response.toString());
                final ResponseBody body = response.body();
                if (body == null) {
                    CallbackDispatcher.dispatchRequestFailure(mCallback, new DownloadException("body==null"));
                    return;
                }
                if (writeFileFromBody(mApk, body, mCallback)) {
                    CallbackDispatcher.dispatchDownloadSuccess(mCallback, mApk);
                    CallbackDispatcher.dispatchRequestFinish(mCallback);
                } else {
                    CallbackDispatcher.dispatchRequestFailure(mCallback, new DownloadException("writeFileFromBody has occur exception"));
                }
            }
        });
    }

    /**
     * 从响应体中获取InputStream并写入文件，在写入过程中获取下载进度并回调
     *
     * @param file apk 文件
     * @param body 响应体
     * @throws IOException io过程中的异常
     */
    private boolean writeFileFromBody(File file, ResponseBody body, DownloadCallback callback) throws IOException {
        final InputStream is = body.byteStream();
        final long totalLength = body.contentLength();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            final byte data[] = new byte[2048];
            int len;
            long current = 0;
            while ((len = is.read(data)) != -1) {
                fos.write(data, 0, len);
                current += len;
                //1s回调一次
                if (current < totalLength) {
                    if (System.currentTimeMillis() - mLastUpdateTime < 1000) {
                        continue;
                    }
                }
                mLastUpdateTime = System.currentTimeMillis();
                CallbackDispatcher.dispatchDownloading(callback, current, totalLength);
            }
            fos.flush();
            return current == totalLength;
        } catch (IOException e) {
            Logger.e(e.getMessage());
            return false;
        } finally {
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
}