package com.kimeeo.kandroid.sample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.kimeeo.library.actions.SelectImage;

/**
 * Created by bhavinpadhiyar on 7/17/15.
 */
public class BaseActivity extends ActionBarActivity implements SelectImage.RegisterImageUploadCallBack {

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

    SelectImage imageSelector;
    public void registerImageUploadCallBack(SelectImage imageSelector)
    {
        this.imageSelector=imageSelector;
    }

    public void unRegisterImageUploadCallBack(SelectImage imageSelector)
    {
        this.imageSelector=null;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(imageSelector !=null)
            imageSelector.onActivityResult(requestCode,resultCode,data);
    }
}
