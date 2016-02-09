package com.kimeeo.library.listDataView.dataManagers.simpleList;

import android.content.Context;

import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.IListProvider;
import com.kimeeo.library.listDataView.dataManagers.PageData;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
public class ListDataManager extends DataManager {

    private static final String LOG_TAG= "ListDataManager";

    private IListProvider listProvider;
    public void garbageCollectorCall()
    {
        super.garbageCollectorCall();
        listProvider = null;
    }

    public ListDataManager(Context context,IListProvider listProvider)
    {
        super(context);
        this.listProvider=listProvider;
    }
    protected void callService(String url)
    {
        boolean isRefreshPage = isRefreshPage(getPageData(), url);
        List<?> data;
        if(isRefreshPage)
            data=listProvider.getList(getPageData(),getRefreshDataServerCallParams(getPageData()));
        else
            data=listProvider.getList(getPageData(), getNextDataServerCallParams(getPageData()));
        dataHandler(url,data,"DONE");
    }
    protected String getNextDataURL(PageData data)
    {
        return data.curruntPage+"";
    }
    protected void parseData(String url, Object value, Object status)
    {
        boolean isRefreshPage = isRefreshPage(getPageData(),url);
        if(isRefreshPage==false)
            updatePagingData();
        dataLoadingDone((List<?>)value,isRefreshPage);
    }
    protected void updatePagingData() {
        try
        {
            getPageData().curruntPage +=1;
            getPageData().totalPage +=1;
        }catch (Exception e)
        {
            getPageData().curruntPage=getPageData().totalPage=1;
        }
    }

    protected boolean isRefreshPage(PageData pageData,String url)
    {
        if(getRefreshDataURL(pageData)!=null)
            return getRefreshDataURL(pageData).equals(url);
        return false;
    }


    final public Class<BaseDataParser> getLoadedDataParsingAwareClass()
    {
        return null;
    }
}
