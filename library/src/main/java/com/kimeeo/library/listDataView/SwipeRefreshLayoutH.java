package com.kimeeo.library.listDataView;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by bhavinpadhiyar on 1/28/16.
 */
public class SwipeRefreshLayoutH extends SwipeRefreshLayout {
    public SwipeRefreshLayoutH(Context context) {
        this(context, null);
    }
    public SwipeRefreshLayoutH(Context context, AttributeSet attrs) {
        super(context,attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private int mTouchSlop;
    private float mPrevX;





    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(event).getX();
                break;

            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);

                if (xDiff > mTouchSlop) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(event);
    }
}
