package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.kimeeo.library.ajax.ExtendedAjaxCallback;
import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;

import org.apache.http.cookie.Cookie;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class LoadDataAQuery extends BaseAction{

    public LoadDataAQuery(Activity activity) {
        super(activity);
    }

    public LoadDataAQuery(Activity activity,AQuery androidQuery,List<Cookie> cookies) {
        super(activity);
        this.cookies=cookies;
        this.androidQuery=androidQuery;
    }

    public LoadDataAQuery(Activity activity,List<Cookie> cookies) {
        super(activity);
        this.cookies=cookies;
    }
    protected AQuery androidQuery;
    Gson gson = new Gson();
    //private long cachingTime=1 * 60 * 1000;
    private long cachingTime=-1;
    protected void setCachingTime(long value)
    {
        cachingTime = value;
    }
    protected long getCachingTime()
    {
        return cachingTime;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    List<Cookie> cookies;
    public void clear()
    {
        androidQuery=null;
        gson=null;
        cookies=null;
        super.clear();
    }

    public void perform(String url,final Result callResult,Map<String, Object> params)
    {
        if(androidQuery==null)
            androidQuery = new AQuery(activity);

        ExtendedAjaxCallback ajaxCallback = new ExtendedAjaxCallback<Object>() {
            public void callback(String url, Object json, AjaxStatus status)
            {
                callResult.done(url, json, status);
            }
        };
        if(cookies!=null && cookies.size()!=0) {
            for (Cookie cookie : cookies) {
                ajaxCallback.cookie(cookie.getName(), cookie.getValue());
            }
        }

        if(params!=null && params.entrySet().size()!=0)
        {
            ajaxCallback.setParams(params);
            ajaxCallback.setClazz(String.class);
            ajaxCallback.expire(getCachingTime());
            androidQuery.ajax(url, params, String.class, ajaxCallback);
        }
        else {
            ajaxCallback.setClazz(String.class);
            androidQuery.ajax(url, String.class, getCachingTime(), ajaxCallback);
        }
    }

    public void perform(String url,final Result callResult,final Class typeCast,Map<String, Object> params) {

        Result resultLocal =new Result()
        {
            public void done(String url, Object json, Object status)
            {
                if(json!=null && json instanceof String)  {
                    try {

                        Object data =gson.fromJson(json.toString(), typeCast);
                        callResult.done(url, data, status);
                    }catch (Exception e)
                    {
                        callResult.done(url, null, e);
                    }
                }
                else
                    callResult.done(url, null, status);
            }
        };
        perform(url,resultLocal,params);
    }

    public void perform(String url, Result callResult, Class typeCast) {
        perform(url,callResult,typeCast,null);
    }
    public static interface Result
    {
        void done(String url, Object json, Object status);
    }
}
