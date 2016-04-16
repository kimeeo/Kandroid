package com.kimeeo.kandroid.volleydatamanger;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.cookie.Cookie;

import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 2/29/16.
 */
abstract public class StringVolleyDataManager extends VolleyDataManager
{
    public StringVolleyDataManager(Context context,IVolleyRequestProvider volleyRequestController)
    {
        super(context,volleyRequestController);
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
                List<Cookie> cookies = getVolleyRequestController().getCookies();
                if(cookies!=null && cookies.size()!=0) {
                    for (Cookie cookie : cookies) {
                        headers.put(cookie.getName(), cookie.getValue());
                    }
                }
                return headers;
            }
        };
        return request;
    }
}
