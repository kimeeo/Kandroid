package com.kimeeo.library.actions;

import android.app.Activity;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class BaseAction {
    public Activity getActivity() {
        return activity;
    }

    protected Activity activity;

    public BaseAction(Activity activity) {
        this.activity = activity;
    }

    public void clear()
    {
        activity=null;
    }
}
