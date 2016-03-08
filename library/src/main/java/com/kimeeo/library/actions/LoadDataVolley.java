package com.kimeeo.library.actions;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.kimeeo.library.ajax.ExtendedAjaxCallback;
import com.kimeeo.library.listDataView.dataManagers.volley.IVolleyRequestProvider;

import org.apache.http.cookie.Cookie;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class LoadDataVolley extends BaseAction{

    private IVolleyRequestProvider volleyRequestController;

    public LoadDataVolley(Activity activity) {
        super(activity);
    }
    public LoadDataVolley(Activity activity,IVolleyRequestProvider volleyRequestController) {
        super(activity);
        this.volleyRequestController =volleyRequestController;
    }

    public void clear()
    {
        super.clear();
    }

    public void perform(final String url,final Result callResult,Map<String, String> params,List<Cookie> cookies) {
        String tag=url;
        Response.ErrorListener error=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                callResult.done(url, error);
            }
        };
        Response.Listener done=new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {
                callResult.done(url, response);
            }
        };

        Request request;
        if(params!=null)
            request = getRequest(url, params, Request.Method.POST,done,error,cookies);
        else
            request = getRequest(url, params, Request.Method.GET,done,error,cookies);
        volleyRequestController.addToRequestQueue(request,tag);
    }
   
    public void perform(String url,final Result callResult,final Class typeCast,Map<String, String> params,List<Cookie> cookies) {
        Result callResultLocal=new Result()
        {
            public void done(String url, Object data)
            {
                if(callResult!=null)
                {
                    if(data!=null && data instanceof String)  {
                        try {
                            Gson gson = new Gson();
                            Object result =gson.fromJson(data.toString(), typeCast);
                            callResult.done(url, result);
                        }catch (Exception e)
                        {
                            callResult.done(url,e);
                        }
                    }
                    else
                        callResult.done(url, data);
                }
            }
        };
        perform(url,callResultLocal,params,cookies);
    }
    public void perform(String url, Result callResult, Class typeCast,Map<String, String> params) {
        perform(url,callResult,typeCast,params,null);
    }
    public void perform(String url, Result callResult, Class typeCast) {
        perform(url,callResult,typeCast,null,null);
    }

    public static interface Result
    {
        void done(String url, Object data);
    }

    protected Request getRequest(final String url,final Map<String, String> params,int method,Response.Listener done,Response.ErrorListener error,final List<Cookie> cookies)
    {
        StringRequest request = new StringRequest(method,url,done ,error){
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> headers = super.getHeaders();
                if(cookies!=null && cookies.size()!=0) {
                    for (Cookie cookie : cookies) {
                        headers.put(cookie.getName(), cookie.getValue());
                    }
                }
                else if(volleyRequestController.getCookies()!=null && volleyRequestController.getCookies().size()!=0)
                {
                    for (Cookie cookie : volleyRequestController.getCookies()) {
                        headers.put(cookie.getName(), cookie.getValue());
                    }
                }
                return headers;
            }
        };
        return request;
    }
}
