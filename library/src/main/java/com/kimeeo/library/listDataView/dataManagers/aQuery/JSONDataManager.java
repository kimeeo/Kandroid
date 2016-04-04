package com.kimeeo.library.listDataView.dataManagers.aQuery;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.kimeeo.library.ajax.ExtendedAjaxCallback;
import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;
import com.kimeeo.library.listDataView.dataManagers.IParseableObject;
import com.kimeeo.library.listDataView.dataManagers.PageData;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 12/23/15.
 */


abstract public class JSONDataManager extends AQueryDataManager {

    private static final String LOG_TAG= "JSONDataManager";
    protected BaseDataParser loadedDataVO;
    public JSONDataManager(Context context)
    {
        super(context);
    }

    protected void callService(String url)
    {
        ExtendedAjaxCallback ajaxCallback =getAjaxCallback();
        Map<String, Object> params=null;
        if(isLoadingRefreshData)
            params = getRefreshDataServerCallParams(pageData);
        else
            params = getNextDataServerCallParams(pageData);


        if(params==null) {
            ajaxCallback.setClazz(String.class);
            getaQuery().ajax(url, String.class,getCachingTime(), ajaxCallback);
        }
        else {
            ajaxCallback.setParams(params);
            ajaxCallback.setClazz(String.class);
            ajaxCallback.expire(getCachingTime());
            getaQuery().ajax(url, params, String.class, ajaxCallback);
        }
    }

    protected void dataIn(BaseDataParser value)
    {

    }

    public void garbageCollectorCall() {
        super.garbageCollectorCall();
        loadedDataVO=null;
    }

    protected void parseData(String url, Object value, Object status)
    {
        //BaseDataParser oldLoadedDataVO = loadedDataVO;
        boolean isRefreshPage = isRefreshPage(pageData,url);
        try
        {
            String data=(String)value;
            Class<BaseDataParser> clazz = getLoadedDataParsingAwareClass();

            Type type  =clazz;

            Gson gson= new Gson();
            loadedDataVO = gson.fromJson(data, type);

            loadedDataVO.typeCastData();
            dataIn(loadedDataVO);



            if(isRefreshPage==false)
                updatePagingData(loadedDataVO);


            List<?> list=null;
            if(loadedDataVO.parseRequired())
            {
                Class<Object> iBaseObjectClass = loadedDataVO.getManagedObjectClass();
                list = getResultList(loadedDataVO, iBaseObjectClass);
            }
            else {
                list = loadedDataVO.getList();
                if(list!=null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) instanceof IParseableObject)
                            ((IParseableObject) list.get(i)).dataLoaded(loadedDataVO);
                    }
                }
            }
            dataLoadingDone(list,isRefreshPage);
        }
        catch (Throwable e)
        {
            Log.e(LOG_TAG, "JSON PARSING FAIL " + url);
            dataLoadingDone(null,isRefreshPage);
            System.out.println(e);
        }
    }
    protected List<?> getResultList(BaseDataParser loadedDataVO,Class<Object> iBaseObjectClass)
    {
        List<?> list = loadedDataVO.getList();
        if(list!=null && list.size()!=0)
            return loadedDataVO.typeCastList(list, iBaseObjectClass);
        return null;
    }

    protected void updatePagingData(BaseDataParser loadedDataVO) {
        try
        {
            pageData.curruntPage = loadedDataVO.getActivePageIndex();
            pageData.totalPage = loadedDataVO.getTotalPageCount();
        }catch (Exception e)
        {
            pageData.curruntPage=pageData.totalPage=1;
        }
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
        try
        {
            return getRefreshDataURL(pageData).equals(url);
        }catch(Exception e)
        {
            return false;
        }
    }

}
