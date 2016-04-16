package com.kimeeo.kandroid.volleydatamanger;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.kimeeo.library.actions.BaseAction;

import org.apache.http.cookie.Cookie;

import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class LoadDataVolley extends BaseAction {

    private IVolleyRequestProvider volleyRequestController;
    Gson gson = new Gson();
    List<Cookie> cookies;
    public LoadDataVolley(Activity activity) {
        super(activity);
        volleyRequestController = SampleVolleyRequestController.getInstance(activity);
    }
    public LoadDataVolley(Activity activity,IVolleyRequestProvider volleyRequestController,List<Cookie> cookies) {
        super(activity);
        this.volleyRequestController =volleyRequestController;
        this.cookies=cookies;
    }
    public LoadDataVolley(Activity activity,IVolleyRequestProvider volleyRequestController) {
        super(activity);
        this.volleyRequestController =volleyRequestController;
    }


    public void clear()
    {
        super.clear();
        gson=null;
        volleyRequestController=null;
        cookies=null;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }



    public void perform(final String url,final Result callResult,Map<String, String> params) {
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

        int method=Request.Method.GET;
        if(params!=null && params.entrySet().size()!=0)
            method=Request.Method.POST;

        Request request = getRequest(url, params,method,done,error);
        volleyRequestController.addToRequestQueue(request, tag);
    }
   
    public void perform(String url,final Result callResult,final Class typeCast,Map<String, String> params) {
        Result callResultLocal=new Result()
        {
            public void done(String url, Object data)
            {
                if(callResult!=null)
                {
                    if(data!=null && data instanceof String)  {
                        try {
                            Object result =gson.fromJson(data.toString(), typeCast);
                            callResult.done(url, result);
                        }
                        catch (Exception e)
                        {
                            callResult.done(url,e);
                        }
                    }
                    else
                        callResult.done(url, data);
                }
            }
        };
        perform(url,callResultLocal,params);
    }
    public void perform(String url, Result callResult, Class typeCast) {
        perform(url,callResult,typeCast,null);
    }

    public static interface Result
    {
        void done(String url, Object data);
    }

    protected Request getRequest(final String url,final Map<String, String> params,int method,Response.Listener done,Response.ErrorListener error)
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

                List<Cookie> cookiesLocal =cookies;
                if(cookiesLocal==null || cookiesLocal.size()==0)
                    cookiesLocal=volleyRequestController.getCookies();

                if(cookiesLocal!=null && cookiesLocal.size()!=0) {
                    for (Cookie cookie : cookiesLocal) {
                        headers.put(cookie.getName(), cookie.getValue());
                    }
                }
                return headers;
            }
        };
        return request;
    }
}
