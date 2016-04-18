package com.kimeeo.library.listDataView.dataManagers.volleyDataManger;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kimeeo.library.listDataView.dataManagers.DataManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 1/8/16.
 */
abstract  public class VolleyDataManager extends DataManager {

    public void garbageCollectorCall() {
        super.garbageCollectorCall();
    }

    abstract protected Request getRequest(final String url,final Map<String, String> params,int method,Response.Listener done,Response.ErrorListener error);
    private long cachingTime=-1;

    /*
    public VolleyRequestController getVolleyRequestController() {
        return volleyRequestController;
    }
   */

    public IVolleyRequestProvider getVolleyRequestController() {
        return volleyRequestController;
    }

    private IVolleyRequestProvider volleyRequestController;
    protected void setCachingTime(long value)
    {
        cachingTime = value;
    }
    protected long getCachingTime()
    {
        return cachingTime;
    }

    public VolleyDataManager(Context context,IVolleyRequestProvider volleyRequestController)
    {
        super(context);
        this.volleyRequestController=volleyRequestController;
    }
    public void ajaxCancel(String tag)
    {
        volleyRequestController.cancelPendingRequests(tag);
    }



    protected void callService(String url)
    {
        //url ="https://google.com";
        Map<String, Object> params=null;
        if(isLoadingRefreshData)
            params = getRefreshDataServerCallParams(pageData);
        else
            params = getNextDataServerCallParams(pageData);

        Map<String, String> paramsFinal=null;
        if(params!=null)
        {
            paramsFinal = new HashMap<>();
            for (Map.Entry<String, Object> stringObjectEntry : params.entrySet()) {
                paramsFinal.put(stringObjectEntry.getKey(),stringObjectEntry.getValue().toString());
            }
        }

        if(paramsFinal!=null)
            makeACall(url, paramsFinal, Request.Method.POST);
        else
            makeACall(url, paramsFinal,Request.Method.GET);
    }

    protected void makeACall(final String url,final Map<String, String> params,int method)
    {
        String tag=url;
        Response.Listener done=getDone(url);
        Response.ErrorListener error=getError(url);
        Request request= getRequest(url, params, method,done,error);
        volleyRequestController.addToRequestQueue(request,tag);
    }
    protected Response.ErrorListener getError(final String url) {
        Response.ErrorListener error=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                dataHandler(url,null,error);
            }
        };
        return error;
    }
    protected Response.Listener getDone(final String url) {
        Response.Listener listener=new Response.Listener<Object>() {

            @Override
            public void onResponse(Object response) {
                dataHandler(url,response,"DONE");
            }
        };
        return listener;
    }
}
