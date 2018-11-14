package com.xiongms.libcore.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;

/**
 * 图片加载器
 * by cy
 */
public class GlideLoader {
    private volatile static Builder builder = new Builder();

    public static Builder getBuilder() {
        return builder;
    }

    /***
     * 全部配置类
     * ***/
    public static final class Builder {
        //全局配置默认加载图
        int Default;
        //全局配置默认头像图
        int DefaultHead;
        //全局配置默认圆图
        int DefaultCircle;
        //全局配置默认其他图
        int DefaultOther;

        private Builder() {
        }

        public Builder setDefault(int Default) {
            this.Default = Default;
            return this;
        }

        public Builder setDefaultHead(int DefaultHead) {
            this.DefaultHead = DefaultHead;
            return this;
        }

        public Builder setDefaultCircle(int DefaultCircle) {
            this.DefaultCircle = DefaultCircle;
            return this;
        }

        public Builder setDefaultOther(int DefaultOther) {
            this.DefaultOther = DefaultOther;
            return this;
        }
    }


    /**
     * 普通加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .dontAnimate()
                .placeholder(builder.Default)
                .into(imageView);
    }

    public static void loadHead(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .dontAnimate()
                .placeholder(builder.DefaultHead)
                .transform(new GlideCircleTransform())
                .into(imageView);
    }

    public static void loadAd(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .dontAnimate()
                .placeholder(builder.DefaultOther)
                .transform(new GlideCircleTransform())
                .into(imageView);
    }

    /**
     * 普通加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView, int placeHolder) {
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .dontAnimate()
                .placeholder(placeHolder)
                .into(imageView);
    }

    /**
     * 加载本地图片
     *
     * @param context
     * @param file
     * @param imageView
     */
    public static void loadImage(Context context, File file, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(file)
                .dontAnimate()
                .placeholder(builder.Default)
                .into(imageView);
    }

    /**
     * 加载本地图片
     *
     * @param context
     * @param uri
     * @param imageView
     */
    public static void loadImage(Context context, Uri uri, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(uri)
                .dontAnimate()
                .placeholder(builder.Default)
                .into(imageView);
    }

    /**
     * 加载资源图片
     *
     * @param context
     * @param res
     * @param imageView
     */
    public static void loadImage(Context context, int res, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(res)
                .dontAnimate()
                .placeholder(builder.Default)
                .into(imageView);
    }

    /**
     * 加载gif图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadGifImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .asGif()
                .load(url)
                .dontAnimate()
                .placeholder(builder.Default)
                .into(imageView);
    }

    /**
     * 加载gif图
     *
     * @param context
     * @param res
     * @param imageView
     */
    public static void loadGifImage(Context context, int res, ImageView imageView) {
        GlideApp.with(context)
                .asGif()
                .load(res)
                .dontAnimate()
                .placeholder(builder.Default)
                .into(imageView);
    }

    /**
     * 加载圆图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadcircleImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .dontAnimate()
                .placeholder(builder.Default)
                .transform(new GlideCircleTransform())
                .into(imageView);
    }

    /**
     * 加载圆图
     *
     * @param context
     * @param file
     * @param imageView
     */
    public static void loadcircleImage(Context context, File file, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(file)
                .dontAnimate()
                .placeholder(builder.Default)
                .transform(new GlideCircleTransform())
                .into(imageView);
    }

    /**
     * 加载圆角
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadRoundImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .dontAnimate()
                .placeholder(builder.Default)
                .transform(new GlideCircleTransform())
                .into(imageView);
    }

    /**
     * 加载图片带回调
     *
     * @param context
     * @param url
     * @param listener
     */
    public static void loadImageWithCallback(Context context, String url, SimpleTarget<Bitmap> listener) {
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .dontAnimate()
                .placeholder(builder.Default)
                .into(listener);
    }
}