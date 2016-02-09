package com.kimeeo.library.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by bhavinpadhiyar on 9/4/15.
 */
public interface IFragmentWatcher {
    void onDestroy(Fragment baseFragment);
    void onCreate(Fragment baseFragment);
}
