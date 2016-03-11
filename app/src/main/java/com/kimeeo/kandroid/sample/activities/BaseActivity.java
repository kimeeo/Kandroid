package com.kimeeo.kandroid.sample.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by bhavinpadhiyar on 7/17/15.
 */
public class BaseActivity extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
    }
    @Override
    public void onStop() {
        //EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
