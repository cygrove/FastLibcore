package com.xiongms.libcore.updateversion.task;

import com.xiongms.libcore.updateversion.request.DownloadRequest;
import com.xiongms.libcore.updateversion.request.IRequest;

import java.io.File;


public class DownloadTask extends BaseTask<DownloadTask> {
    private File mApk;//下载的apk文件

    public DownloadTask(String url, String filePath, String fileName) {
        super(url);
        mApk = createFile(filePath, fileName);
    }

    @Override
    public void execute() {
        IRequest request = new DownloadRequest(mUrl, mApk, mCallback);
        request.request();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private File createFile(String filePath, String fileName) {
        createDir(filePath);
        File file = new File(filePath, fileName);
        //如果存在该文件,则删除
        if (file.exists()) {
            file.delete();
        }
        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createDir(String filePath) {
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}