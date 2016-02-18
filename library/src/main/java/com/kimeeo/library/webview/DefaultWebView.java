package com.kimeeo.library.webview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kimeeo.library.R;
import com.kimeeo.library.fragments.BaseFragment;

/**
 * Created by bhavinpadhiyar on 11/4/15.
 */
public class DefaultWebView extends BaseFragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout._default_webview_fragment_layout,container,false);
        WebView webView = (WebView)view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl((String)getFragmentData().getActionValue());
        webView.setWebViewClient(new WebViewClient());
        webView.setBackgroundColor(0x00000000);
        return view;
    }
    protected void garbageCollectorCall()
    {

    }
}