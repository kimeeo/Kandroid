package com.kimeeo.library.listDataView.dataManagers.sqlLite;

import android.content.Context;

import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.IListProvider;
import com.kimeeo.library.listDataView.dataManagers.PageData;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/29/16.
 */
public class SQLLiteDataManager extends DataManager {

    private static final String LOG_TAG= "SQLLiteDataManager";

    private IListProvider listProvider;

    public SQLiteDataHelper getSqlLiteOpenHelper() {
        return sqlLiteOpenHelper;
    }

    private SQLiteDataHelper sqlLiteOpenHelper;
    public void garbageCollectorCall()
    {
        super.garbageCollectorCall();
        listProvider = null;
        listProvider = null;
    }

    public SQLLiteDataManager(Context context,SQLiteDataHelper sqlLiteDataHelper,IListProvider listProvider)
    {
        super(context);
        this.listProvider=listProvider;
        this.sqlLiteOpenHelper =sqlLiteDataHelper;
    }
    public SQLLiteDataManager(Context context,IListProvider listProvider)
    {
        super(context);
        this.listProvider=listProvider;
        this.sqlLiteOpenHelper =null;
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