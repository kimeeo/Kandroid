package com.kimeeo.library.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by BhavinPadhiyar on 07/04/16.
 */
public class ViewStackPager extends ViewPager {

    public ViewStackPager(Context context) {
        super(context);
    }

    public ViewStackPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
