package com.kimeeo.library.listDataView.dataManagers.volley;

import android.content.Context;

import com.kimeeo.library.ajax.IVolleyRequestProvider;
import com.kimeeo.library.listDataView.dataManagers.IListProvider;
import com.kimeeo.library.utils.NetworkUtilities;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 12/23/15.
 */


abstract public class DefaultJSONFallbackOfflineDataManager extends JSONDataManager {
    private static final String LOG_TAG= "DefaultJSONDataManager";
    private IListProvider listProvider;

    public boolean isConnected() {
        return isConnected;
    }

    private boolean isConnected=false;

    public void garbageCollectorCall() {
        super.garbageCollectorCall();
        listProvider=null;
    }
    public DefaultJSONFallbackOfflineDataManager(Context context,IVolleyRequestProvider volleyRequestController,IListProvider listProvider)
    {
        super(context,volleyRequestController);
        this.listProvider=listProvider;
        this.isConnected =NetworkUtilities.isConnected(context);
    }

    protected void callService(String url)
    {
        if(isConnected())
            super.callService(url);
        else
        {
            boolean isRefreshPage = isRefreshPage(getPageData(), url);
            List<?> data;
            if(isRefreshPage)
                data=listProvider.getList(getPageData(),getRefreshDataServerCallParams(getPageData()));
            else
                data=listProvider.getList(getPageData(), getNextDataServerCallParams(getPageData()));
            dataHandler(url,data,"DONE");
        }
    }

    protected void parseData(String url, Object value, Object status)
    {
        if(isConnected())
            super.parseData(url,value,status);
        else
        {
            boolean isRefreshPage = isRefreshPage(getPageData(),url);
            if(isRefreshPage==false)
                updatePagingData();
            dataLoadingDone((List<?>)value,isRefreshPage);
        }
    }

}
