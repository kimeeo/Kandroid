package com.kimeeo.library.ajax;

import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

import org.apache.http.cookie.Cookie;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 2/29/16.
 */
public interface IVolleyRequestProvider {

    RequestQueue getRequestQueue() ;
    ImageLoader getImageLoader();

    <T> void addToRequestQueue(Request<T> req, String tag);
    <T> void addToRequestQueue(Request<T> req);
    void cancelPendingRequests(Object tag);

    List<Cookie> getCookies();
    void setCookies(List<Cookie> cookies);
}
