package com.hitherejoe.tabby;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.kimeeo.library.R;

public class WebViewActivity extends ActionBarActivity {

    WebView mWebView;

    public static final String EXTRA_URL ="WebViewActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._tabby_activity_webview);
        mWebView = (WebView)findViewById(R.id.web_view);
        String url = getIntent().getStringExtra(EXTRA_URL);
        mWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl(url);
        setupActionBar(url);
    }

    private void setupActionBar(String url) {
        setTitle(url);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }
}