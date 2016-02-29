package com.kimeeo.library.listDataView.dataManagers.volley;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

import org.apache.http.cookie.Cookie;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 2/29/16.
 */
public class SampleVolleyRequestController implements IVolleyRequestProvider {

    public static final String TAG = SampleVolleyRequestController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static SampleVolleyRequestController mInstance;

    public static synchronized SampleVolleyRequestController getInstance(Context app)
    {
        if (mInstance == null)
        {
            mInstance = new SampleVolleyRequestController();
            Network network = new BasicNetwork(new HurlStack());
            Cache cache = new DiskBasedCache(Environment.getDataDirectory(), 1024 * 1024);
            mInstance.mRequestQueue = new RequestQueue(cache, network);
            mInstance.mRequestQueue.start();
            //Volley.newRequestQueue(app.getApplicationContext(),network);
        }
        return mInstance;
    }
    private List<Cookie> cookies;
    public List<Cookie> getCookies()
    {
        return cookies;
    }
    public void setCookies(List<Cookie> cookies)
    {
        this.cookies=cookies;
    }


    public RequestQueue getRequestQueue() {

        return mRequestQueue;
    }
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
