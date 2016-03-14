package com.kimeeo.library.listDataView;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import com.kimeeo.library.R;

/**
 * Created by bpa001 on 3/14/16.
 */
abstract public class DefaultErrorHandler implements EmptyViewHelper.IEmptyViewHelper {
    abstract public Resources getResources();

    abstract public void retry();

    public Drawable getEmptyViewDrawable() {
        Drawable drawable = getResources().getDrawable(R.drawable._vector_icon_empty_box);
        drawable.setColorFilter(getResources().getColor(R.color._emptyViewMessageColor), PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }

    public String getEmptyViewMessage() {
        return getResources().getString(R.string._emptyViewMessage);
    }

    public Drawable getInternetViewDrawable() {
        Drawable drawable = getResources().getDrawable(R.drawable._vector_icon_internet_connection);
        drawable.setColorFilter(getResources().getColor(R.color._emptyViewMessageColor), PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }

    public String getInternetViewMessage() {
        return getResources().getString(R.string._emptyViewInterntMessage);
    }
}
