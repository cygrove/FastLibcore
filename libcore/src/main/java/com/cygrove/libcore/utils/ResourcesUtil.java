package com.cygrove.libcore.utils;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.DisplayMetrics;

import com.orhanobut.logger.Logger;

import java.io.InputStream;

public class ResourcesUtil {
    public static final String TAG = "ResourcesUtil";
    // init on advert application
    private static Resources sResouces = null;
    private static float sDesity = 0;

    public static void init(Resources resources) {
        sResouces = resources;
        sDesity = sResouces.getDisplayMetrics().density;
    }

    public static float getDesity() {
        return sDesity;
    }

    public static Resources getResources() {
        return sResouces;
    }

    public static boolean getBoolean(int id) {
        return sResouces.getBoolean(id);
    }

    public static int getAlpha(int id) {
        final int color = getColor(id);
        return android.graphics.Color.alpha(color);
    }

    public static int getColor(int id) {
        return sResouces.getColor(id);
    }

    public static ColorStateList getColorStateList(int id) {
        return sResouces.getColorStateList(id);
    }

    public static float getDimension(int id) {
        return sResouces.getDimension(id);
    }

    public static int getDimensionPixelOffset(int id) {
        return sResouces.getDimensionPixelOffset(id);
    }

    public static int getDimensionPixelSize(int id) {
        return sResouces.getDimensionPixelSize(id);
    }

    public static DisplayMetrics getDisplayMetrics() {
        return sResouces.getDisplayMetrics();
    }

    public static Drawable getDrawable(int resId, int width, int height) {
        Bitmap b = getBitmap(resId, width, height);
        if (b != null) {
            return new BitmapDrawable(sResouces, b);
        }
        return null;
    }

    public static Drawable getDrawable(int resId, float scale) {
        Bitmap b = getBitmap(resId, scale);
        if (b != null) {
            return new BitmapDrawable(sResouces, b);
        }
        return null;
    }

    public static Bitmap getBitmap(int resId, float scale) {
        Bitmap b = getBitmap(resId);
        if (b == null) {
            return null;
        }
        if (scale == 1.0f) {
            return b;
        }
        int height = b.getHeight();
        int width = b.getWidth();
        height = (int) (height * scale);
        width = (int) (width * scale);

        try {
            Bitmap newBitmap = Bitmap.createScaledBitmap(b, width, height, true);
            return newBitmap;
        } catch (OutOfMemoryError err) {
            Logger.d(TAG, "Alert! OutOfMemoryError occured!");
            return null;
        }
    }

    public static Drawable getDrawable(int id) {
        return sResouces.getDrawable(id);
    }

    public static int[] getIntArray(int id) {
        return sResouces.getIntArray(id);
    }

    public static int getInteger(int id) {
        return sResouces.getInteger(id);
    }

    public static String getString(int id) {
        return sResouces.getString(id);
    }

    public static String getString(int id, Object... formatArgs) {
        return sResouces.getString(id, formatArgs);
    }

    public static String[] getStringArray(int id) {
        return sResouces.getStringArray(id);
    }

    public static CharSequence getText(int id, CharSequence def) {
        return sResouces.getText(id, def);
    }

    public static CharSequence getText(int id) {
        return sResouces.getText(id);
    }

    public static CharSequence[] getTextArray(int id) {
        return sResouces.getTextArray(id);
    }

    public static InputStream openRawResource(int id) {
        return sResouces.openRawResource(id);
    }

    public static AssetFileDescriptor openRawResourceFd(int id) {
        return sResouces.openRawResourceFd(id);
    }

    public static Bitmap getBitmap(int id) {
        return BitmapFactory.decodeResource(sResouces, id);
    }

    public static Bitmap getBitmap(int resId, int width, int height) {
        try {
            Bitmap srcBitmap = getBitmap(resId);
            if (srcBitmap != null) {
                if (srcBitmap.getWidth() == width && srcBitmap.getHeight() == height) {
                    return srcBitmap;
                }

                Bitmap newBitmap = Bitmap.createScaledBitmap(srcBitmap, width, height, true);
                return newBitmap;
            }

            return null;
        } catch (OutOfMemoryError err) {
            Logger.d(TAG, "Alert! OutOfMemoryError occured!");
            return null;
        }
    }

    /**
     * 根据给定图片，宽，高，颜色生成一个新的bitmap，空白区域使用color填充
     *
     * @param resId
     * @param width
     * @param height
     * @param color
     * @return
     */
    public static Bitmap getBitmap(int resId, int width, int height, int color) {
        Bitmap srcBitmap = BitmapFactory.decodeResource(sResouces, resId);
        Bitmap objBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(objBitmap);
        Paint p = new Paint();
        canvas.drawColor(color);
        canvas.drawBitmap(srcBitmap, null, new Rect(0, 0, srcBitmap.getWidth(), srcBitmap.getHeight()), p);
        return objBitmap;
    }

    /**
     * 对BitmapDrawable做伸缩
     *
     * @param drawable
     * @param scaleRate
     * @return
     */
    public static Drawable scaleDrawable(Drawable drawable, float scaleRate) {
        if (drawable == null || !(drawable instanceof BitmapDrawable)) {
            return null;
        }
        Bitmap oldBmp = ((BitmapDrawable) drawable).getBitmap();
        if (oldBmp == null || oldBmp.isRecycled()) {
            return drawable;
        }

        Bitmap newBmp = Bitmap.createScaledBitmap(oldBmp, (int) (drawable.getIntrinsicWidth() * scaleRate),
                (int) (drawable.getIntrinsicHeight() * scaleRate), true);

        return new BitmapDrawable(sResouces, newBmp);
    }

    public static StateListDrawable getBtnBgDrawable(int normalResId, int pressedResId) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        if (pressedResId > 0) {
            stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed},
                    getDrawable(pressedResId));
            stateListDrawable.addState(new int[]{android.R.attr.state_focused}, getDrawable(pressedResId));
        }

        if (normalResId > 0) {
            stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, getDrawable(normalResId));
        }
        return stateListDrawable;
    }

    public static StateListDrawable getCheckBoxBgDrawable(int offResId, int onResId) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_checked},
                getDrawable(onResId));
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, -android.R.attr.state_checked},
                getDrawable(offResId));

        stateListDrawable.addState(
                new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled, android.R.attr.state_checked},
                getDrawable(onResId));
        stateListDrawable.addState(
                new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled, -android.R.attr.state_checked},
                getDrawable(offResId));

        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_checked,
                -android.R.attr.state_window_focused}, getDrawable(onResId));
        stateListDrawable
                .addState(
                        new int[]{android.R.attr.state_enabled,
                                -android.R.attr.state_checked - android.R.attr.state_window_focused},
                        getDrawable(offResId));

        return stateListDrawable;
    }

    public static ColorStateList getStateListColor(int normalResId, int pressedResId) {
        final int[][] states = {new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed},
                new int[]{android.R.attr.state_focused}, new int[]{android.R.attr.state_enabled}};
        int[] colors = {getColor(pressedResId), getColor(pressedResId), getColor(normalResId)};
        ColorStateList list = new ColorStateList(states, colors);
        return list;
    }

    public static Bitmap convertDrawable2Bitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        return ((BitmapDrawable) drawable).getBitmap();
    }

    public static int dip2px(float dipValue) {
        final float scale = getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int dip2px(DisplayMetrics metrics, float dipValue) {
        final float scale = metrics.density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int sp2px(float sp) {
        final float scale = getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

    public static int sp2px(DisplayMetrics metrics, float sp) {
        final float scale = metrics.scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        final float scale = getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2dip(DisplayMetrics metrics, float pxValue) {
        final float scale = metrics.density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 判断是否横屏
     *
     * @return
     */
    public static boolean isOrientationLandscape() {
        return sResouces.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 返回屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        DisplayMetrics metrics = getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * 返回屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        DisplayMetrics metrics = getDisplayMetrics();
        return metrics.heightPixels;
    }

    /**
     * 应用区域高度
     *
     * @param activity
     * @return
     */
    public static int getVisibleScreenHeight(Activity activity) {
        //应用区域
        Rect outRect1 = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        return outRect1.height();
    }
}