package com.kimeeo.kAndroid.aQueryDataManager;

import android.content.Context;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.kimeeo.library.listDataView.dataManagers.DataManager;

import org.apache.http.cookie.Cookie;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/8/16.
 */
abstract  public class AQueryDataManager extends DataManager {

    //private long cachingTime=1 * 60 * 1000;
    private long cachingTime=-1;

    protected void setCachingTime(long value)
    {
        cachingTime = value;
    }

    protected AQuery androidQuery;
    protected long getCachingTime()
    {
        return cachingTime;
    }

    public AQueryDataManager(Context context)
    {
        super(context);
        androidQuery= new AQuery(context);
    }

    public AQuery getaQuery() {
        return androidQuery;
    }
    public void garbageCollectorCall()
    {
        super.garbageCollectorCall();

        if(androidQuery!=null)
            ajaxCancel();
        androidQuery=null;


        cookies = null;
    }
    public void ajaxCancel()
    {
        androidQuery.ajaxCancel();
    }


    protected ExtendedAjaxCallback getAjaxCallback() {
        ExtendedAjaxCallback<Object> ajaxCallback = new ExtendedAjaxCallback<Object>() {
            public void callback(String url, Object json, AjaxStatus status) {
                dataHandler(url, json, status);
            }
        };
        List<Cookie> cookies = getCookies();
        if(cookies!=null && cookies.size()!=0) {
            for (Cookie cookie : cookies) {
                ajaxCallback.cookie(cookie.getName(), cookie.getValue());
            }
        }
        return ajaxCallback;
    }

    private  List<Cookie> cookies;
    public List<Cookie> getCookies()
    {
        return cookies;
    }
    public void setCookies(List<Cookie> cookies)
    {
        this.cookies=cookies;
    }

}
