package com.cygrove.libcore.updateversion.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cygrove.libcore.R;


@SuppressLint("AppCompatCustomView")
public class CompatTextView extends TextView {

    private Drawable mContentDrawable;//必填，TextView的背景,可见
    private Drawable mMaskDrawable;//按需，RippleColor扩散的区域,不可见
    private Drawable mSelectorDrawable;//必填，兼容5.0以下版本的TextView背景
    private ColorStateList mRippleColor;//必填，水波纹扩散的颜色

    public CompatTextView(Context context) {
        this(context, null);
    }

    public CompatTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompatTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CompatTextView);
        int rippleColor = a.getColor(R.styleable.CompatTextView_rippleColor, Color.BLUE);
        mRippleColor = ColorStateList.valueOf(rippleColor);
        mContentDrawable = a.getDrawable(R.styleable.CompatTextView_contentDrawable);
        mMaskDrawable = a.getDrawable(R.styleable.CompatTextView_maskDrawable);
        mSelectorDrawable = a.getDrawable(R.styleable.CompatTextView_selectorDrawable);
        a.recycle();
        init();
    }

    private void init() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            RippleDrawable drawable = new RippleDrawable(mRippleColor, mContentDrawable, mMaskDrawable);
            setBackground(drawable);
        } else {
            setBackground(mSelectorDrawable);
        }
    }
}