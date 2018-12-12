package com.cygrove.libcore.updateversion;

import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.cygrove.libcore.updateversion.dialog.CheckUpdateDialog;
import com.cygrove.libcore.updateversion.task.DownloadTask;
import com.cygrove.libcore.utils.AppUtil;
import com.cygrove.libcore.utils.ToastUtil;

public class Q {

    /**
     * 下载任务
     *
     * @param url      下载地址
     * @param filePath apk存储路径
     * @param fileName apk存储名称
     * @return 下载任务包装类
     */
    public static DownloadTask download(String url, String filePath, String fileName) {
        if (TextUtils.isEmpty(filePath)) {
            filePath = Environment.getExternalStorageDirectory().getPath();
        }
        if (TextUtils.isEmpty(fileName)) {
            filePath = "Update.apk";
        }
        return new DownloadTask(url, filePath, fileName);
    }

    /**
     * 在使用lib自带的CheckUpdateDialog时，可使用该方法展示一个dialogFragment
     *
     * @param context must be FragmentActivity
     * @param option  the CheckUpdateOption,用于设置lib自带的CheckUpdateDialog的一些属性
     * @return CheckUpdateDialog
     */
    public static CheckUpdateDialog show(FragmentActivity context, CheckUpdateOption option, boolean shouldToast) {
        if (option != null) {
            try {
                int currentVersion = Integer.parseInt(AppUtil.getAppVersionName(context).replace(".", ""));
                int version = Integer.parseInt(option.getNewAppVersionName().replace(".", ""));
                if (currentVersion < version) {
                    FragmentManager manager = context.getSupportFragmentManager();
                    CheckUpdateDialog dialog = new CheckUpdateDialog();
                    dialog.applyOption(option);
                    dialog.show(manager, "CheckUpdateDialog");
                    return dialog;
                } else if (currentVersion == version) {
                    if (shouldToast) {
                        ToastUtil.show("当前已是最新版本");
                    }
                }
            } catch (Exception e) {
                Logger.e(e.getMessage());
            }
        }
        return null;
    }
}