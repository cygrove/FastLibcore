package com.xiongms.libcore.updateversion.dispatcher;

import android.os.Handler;
import android.os.Looper;

import com.xiongms.libcore.updateversion.callback.BaseCallback;
import com.xiongms.libcore.updateversion.callback.DownloadCallback;
import com.xiongms.libcore.updateversion.callback.StringCallback;
import com.xiongms.libcore.updateversion.exception.RequestException;

import java.io.File;
import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class CallbackDispatcher {
    private static Handler mDispatcher = new Handler(Looper.getMainLooper());

    /**
     * 把checkUpdateStart回调分发到主线程中<br/>
     * 一般情况下，请求都发生在主线程，但有时也会发生在子线程中，故这里做同样的处理
     *
     * @param callback the callback
     */
    public static void dispatchRequestStart(final BaseCallback callback) {
        if (callback == null) return;
        mDispatcher.post(new Runnable() {
            @Override
            public void run() {
                callback.checkUpdateStart();
            }
        });
    }

    /**
     * 把checkUpdateSuccess回调分发到主线程中
     *
     * @param callback the callback
     * @param response body.string()方法是一个耗时的操作，需要执行在子线程中，但返回的结果需要回调到主线程
     * @throws IOException 读取流时可能抛出异常
     */
    public static void dispatchRequestSuccess(final StringCallback callback, Response response) throws IOException {
        if (callback == null) return;
        final ResponseBody body = response.body();
        if (body != null) {
            final String result = body.string();
            mDispatcher.post(new Runnable() {
                @Override
                public void run() {
                    callback.checkUpdateSuccess(result);
                }
            });
        } else {
            mDispatcher.post(new Runnable() {
                @Override
                public void run() {
                    callback.checkUpdateFailure(new RequestException("body==null"));
                }
            });
        }
    }

    /**
     * 把checkUpdateFailure回调分发到主线程中
     *
     * @param callback the callback
     * @param e        所有的失败情况都在这里，可通过这里了解为什么会失败
     */
    public static void dispatchRequestFailure(final BaseCallback callback, final Throwable e) {
        if (callback == null) return;
        mDispatcher.post(new Runnable() {
            @Override
            public void run() {
                callback.checkUpdateFailure(e);
            }
        });
    }

    /**
     * 把downloadProgress回调分发到主线程中
     *
     * @param callback      the callback
     * @param currentLength 当前下载的总字节数
     * @param totalLength   apk的总字节数
     */
    public static void dispatchDownloading(final DownloadCallback callback,
                                           final long currentLength, final long totalLength) {
        if (callback == null) return;
        mDispatcher.post(new Runnable() {
            @Override
            public void run() {
                callback.downloadProgress(currentLength, totalLength);
            }
        });
    }

    /**
     * 把downloadSuccess回调分发到主线程中
     *
     * @param callback the callback
     * @param apk      下载完成的apk文件
     */
    public static void dispatchDownloadSuccess(final DownloadCallback callback, final File apk) {
        if (callback == null) return;
        mDispatcher.post(new Runnable() {
            @Override
            public void run() {
                callback.downloadSuccess(apk);
            }
        });
    }

    /**
     * 把checkUpdateFinish回调分发到主线程中
     *
     * @param callback the callback
     */
    public static void dispatchRequestFinish(final BaseCallback callback) {
        if (callback == null) return;
        mDispatcher.post(new Runnable() {
            @Override
            public void run() {
                callback.checkUpdateFinish();
            }
        });
    }


}
