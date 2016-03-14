package com.kimeeo.library.listDataView.viewHelper;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.kimeeo.library.listDataView.DefaultErrorHandler;
import com.kimeeo.library.listDataView.EmptyViewHelper;

/**
 * Created by bpa001 on 3/14/16.
 */
abstract public class BaseHelper implements EmptyViewHelper.IEmptyViewHelper {
    DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandlerLocal(this);

    abstract public Resources getResources();

    abstract public void retry();

    public Drawable getEmptyViewDrawable() {
        return defaultErrorHandler.getEmptyViewDrawable();
    }

    public String getEmptyViewMessage() {
        return defaultErrorHandler.getEmptyViewMessage();
    }

    public Drawable getInternetViewDrawable() {
        return defaultErrorHandler.getInternetViewDrawable();
    }

    public String getInternetViewMessage() {
        return defaultErrorHandler.getInternetViewMessage();
    }

    public class DefaultErrorHandlerLocal extends DefaultErrorHandler {
        BaseHelper baseHelper;

        public DefaultErrorHandlerLocal(BaseHelper baseHelper) {
            this.baseHelper = baseHelper;
        }

        public Resources getResources() {
            return baseHelper.getResources();
        }

        public void retry() {
            baseHelper.retry();
        }
    }
}
