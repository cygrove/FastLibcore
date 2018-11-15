package com.xiongms.libcore.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.orhanobut.logger.Logger;
import com.xiongms.libcore.BuildConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件工具类
 *
 * @author cygrove
 * @time 2018-11-15 17:49
 */
public class FileUtil implements FileConstant {
    public static final String SD_CARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     * 描述：获取Uri
     * Android N与之前的方式有区别
     */
    public static Uri getUri(Context context, File file) {
        if (file == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N || !isCanUseSD(context)) {
            Uri uri = FileProvider.getUriForFile(context, BuildConfig.FILE_PROVIDER, file);
            return uri;
        } else {
            Uri uri = Uri.fromFile(file);
            return uri;
        }
    }


    /**
     * 描述：SD卡是否能用.
     *
     * @return true 可用,false不可用
     */
    public static boolean isCanUseSD(Context context) {
        return hasSDWritePermission(context);
    }

    private static boolean hasSDWritePermission(Context context) {
        boolean canUseSD = false;
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (checkCallPhonePermission == PackageManager.PERMISSION_GRANTED) {
                        canUseSD = true;
                    }
                } else {
                    canUseSD = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return canUseSD;
    }

    /**
     * 获取扩展名
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 初始化缓存文件路径
     */
    public static void createDefaultDir() {
        mkdirs(getAppPath());
        mkdirs(getSplashImageDir());
        mkdirs(getFileCache());
        mkdirs(getLogDir());
        mkdirs(getUserDir());
        mkdirs(getShareDir());
        mkdirs(getAppCacheDir());
        mkdirs(getVideoCacheDir());
        mkdirs(getWebCacheDir());
        mkdirs(getImageCacheDir());
        mkdirs(getUploadCacheDir());
        mkdirs(getUpdateCacheDir());
    }

    public static String getAppPath() {
        return SD_CARD_PATH + File.separator + APP_DIR + File.separator;
    }

    public static String getSplashImageDir() {
        return getAppPath() + SPLASH_IMAGE_DIR + File.separator;
    }

    public static String getFileCache() {
        return getAppPath() + FILE_CACHE + File.separator;
    }

    public static String getLogDir() {
        return getAppPath() + LOG_DIR + File.separator;
    }

    public static String getUserDir() {
        String user = "当前用户";
        return getAppPath() + user + File.separator;
    }

    public static String getShareDir() {
        return getUserDir() + SHARE_DIR + File.separator;
    }


    public static String getAppCacheDir() {
        return getUserDir() + APP_CACHE_DIR + File.separator;
    }

    public static String getVideoCacheDir() {
        return getAppCacheDir() + VIDEO_CACHE_DIR + File.separator;
    }

    public static String getWebCacheDir() {
        return getAppCacheDir() + WEB_CACHE_DIR + File.separator;
    }

    public static String getImageCacheDir() {
        return getAppCacheDir() + IMAGE_CACHE_DIR + File.separator;
    }

    public static String getUploadCacheDir() {
        return getAppCacheDir() + UPLOAD_CACHE_DIR + File.separator;
    }

    public static String getUpdateCacheDir() {
        return getAppCacheDir() + UPDATE_CACHE_DIR + File.separator;
    }

    /**
     * 判断sd卡是否存在
     *
     * @return
     */
    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 创建文件夹
     *
     * @param dirsPath
     * @return 文件夹所在的绝对路径
     */
    public static boolean mkdirs(String dirsPath) {
        File dirsFile = new File(dirsPath);
        return dirsFile.exists() || dirsFile.mkdirs();
    }

    public static long getFileSize(String path) {
        File file = new File(path);
        long s = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                s = fis.available();
            } catch (IOException e) {
                Logger.e(e.getMessage());
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        Logger.e(e.getMessage());
                    }
                }
            }
        }
        return s;

    }

    public static long getCacheSize(String path) {
        long total = 0;
        File file = new File(path);
        if (!file.exists()) {
            return total;
        }
        if (file.isFile()) {
            total = file.length();
        } else if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            if (fileList != null && fileList.length > 0) {
                for (File tmpFile : fileList) {
                    total += getCacheSize(tmpFile.getAbsolutePath());
                }
            }
        }
        return total;
    }

    public static String calculateSizeToString(Context ctx, long size) {
        if (size <= 0) {
            return "0.0MB";
        } else {
            return Formatter.formatFileSize(ctx, size);
        }
    }

    public static boolean deleteFiles(String path) {
        File file = new File(path);
        boolean flag = false;
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            flag = file.delete();
        } else if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            if (fileList != null && fileList.length > 0) {
                for (File tmpFile : fileList) {
                    flag = deleteFiles(tmpFile.getAbsolutePath());
                }
            }
        }
        return flag;
    }

    /**
     * 从一个输入流里写文件
     *
     * @param destFilePath 要创建的文件的路径
     * @param in           要读取的输入流
     * @return 写入成功返回true, 写入失败返回false
     */
    public static boolean writeFile(String destFilePath, InputStream in) {
        FileOutputStream fos = null;
        if (createFile(destFilePath)) {
            try {
                fos = new FileOutputStream(destFilePath);
                int readCount = 0;
                int len = 1024;
                byte[] buffer = new byte[len];
                while ((readCount = in.read(buffer)) != -1) {
                    fos.write(buffer, 0, readCount);
                }
                fos.flush();

                return true;
            } catch (IOException e) {
                Logger.e(e.getMessage());
            } finally {
                if (null != fos) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        Logger.e(e.getMessage());
                    }
                }
                if (null != in) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        Logger.e(e.getMessage());
                    }
                }
            }
        }
        return false;
    }

    /**
     * 将一个文件拷贝到另外一个地方
     *
     * @param sourceFile    源文件地址
     * @param destFile      目的地址
     * @param shouldOverlay 是否覆盖
     * @return
     */
    public static boolean copyFiles(String sourceFile, String destFile, boolean shouldOverlay) {
        try {
            if (shouldOverlay) {
                deleteFile(destFile);
            }
            FileInputStream fi = new FileInputStream(sourceFile);
            writeFile(destFile, fi);
            return true;
        } catch (FileNotFoundException e) {
            Logger.e(e.getMessage());
        }
        return false;
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 路径名
     * @return
     */
    public static boolean isFileExist(String filePath) {
        try {
            File f = new File(filePath);
            return f.exists();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 创建一个文件，创建成功返回true
     *
     * @param filePath
     * @return
     */
    public static boolean createFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    if (!file.getParentFile().mkdirs()) {
                        return false;
                    }
                }
                return file.createNewFile();
            }
        } catch (IOException e) {
            Logger.e(e.getMessage());
        }
        return true;
    }

    /**
     * 删除一个文件
     *
     * @param filePath 要删除的文件路径名
     * @return true if this file was deleted, false otherwise
     */
    public static boolean deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                return file.delete();
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return false;
    }

    /**
     * 删除 directoryPath目录下的所有文件，包括删除删除文件夹
     *
     * @param dir 需要删除的文件夹
     */
    public static void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] listFiles = dir.listFiles();
            if (listFiles != null) {
                for (File listFile : listFiles) {
                    deleteDirectory(listFile);
                }
            }
        }
        boolean result = dir.delete();
    }

    public static void cleanDirectory(File directory) throws IOException, IllegalArgumentException {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        File[] files = directory.listFiles();
        if (files == null) { // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }

        IOException exception = null;
        for (File file : files) {
            try {
                forceDelete(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                String message = "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }

    public static FileOutputStream openOutputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                if (!parent.mkdirs()) {
                    throw new IOException("File '" + file + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file);
    }

    /**
     * 保存文件
     *
     * @param file
     * @param data
     * @return
     */
    public static boolean save(File file, byte[] data) {
        OutputStream os = null;
        try {
            os = openOutputStream(file);
            os.write(data, 0, data.length);

            return true;
        } catch (Exception e) {

            Logger.d("FileUtil", "save " + file + "error! " + e.getMessage());

            return false;
        } finally {
            try {
                if (os != null)
                    os.close();
            } catch (IOException e) {
                Logger.e(e.getMessage());
            }
        }
    }

    public static boolean deleteQuietly(File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                cleanDirectory(file);
            }
        } catch (Exception ignored) {
        }

        try {
            return file.delete();
        } catch (Exception ignored) {
            return false;
        }
    }

    public static void deleteShareSdkData(Context ctx) {
        StorageManager sm = (StorageManager) ctx.getSystemService(Context.STORAGE_SERVICE);
        try {
            // 获取sdcard的路径：外置和内置
            String[] paths = (String[]) sm.getClass().getMethod("getVolumePaths", new Class[]{}).invoke(sm);
            if (paths != null) {
                if (paths.length != 0) {
                    for (String path : paths) {
                        if (!TextUtils.isEmpty(path)) {
                            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED)) {
                                path = path.trim();
                                String sdkDataPath = path + "/" + "ShareSdk";
                                File dataFile = new File(sdkDataPath);
                                if (dataFile.exists()) {
                                    deleteQuietly(dataFile);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            Logger.e(e.getMessage());
        }
    }


    public static boolean makeDir(String dirPath) {
        File file = new File(dirPath);
        return file.exists() || file.mkdirs();
    }

    public static void copyFromAssets(AssetManager assets, String source, String dest, boolean isCover) throws IOException {
        FileUtil.createFile(dest);
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = assets.open(source);
            fos = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int size = 0;
            while ((size = is.read(buffer, 0, 1024)) >= 0) {
                fos.write(buffer, 0, size);
            }
        } catch (Exception e) {
            Logger.e("InputStreamException:" + e.toString());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } finally {
                    if (is != null) {
                        is.close();
                    }
                }
            }
        }
    }
}
