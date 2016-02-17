package com.kimeeo.library.listDataView.dataManagers;

import android.content.Context;
import android.util.Log;

//import com.androidquery.callback.AjaxStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 12/23/15.
 */
abstract public class DataManager extends ArrayList<Object>{

    private int refreshItemPos=0;

    abstract protected void callService(String url) throws Exception;
    abstract protected String getNextDataURL(PageData data);
    abstract protected void parseData(String url, Object value, Object status);
    abstract public Class<BaseDataParser> getLoadedDataParsingAwareClass();
    protected String getRefreshDataURL(PageData data){return null;}

    public Map<String, Object> getNextDataServerCallParams(PageData data){return null;}
    public Map<String, Object> getRefreshDataServerCallParams(PageData data)
    {
        return null;
    }


    private static final String TAG = "DataManager";


    public Object remove(int position) {
        Object value=super.remove(position);
        notifyRemove(position,new Object[]{value});
        return value;
    }
    public boolean add(Object value)
    {
        notifyAdd(size(), new Object[]{value});
        return super.add(value);
    }
    public void  add(int position,Object value) {
        notifyAdd(position,new Object[]{value});
        super.add(position, value);
    }
    public boolean addAll(Collection value) {
        notifyAdd(size(), value.toArray());
        return super.addAll(value);
    }
    public boolean addAll(int index, Collection collection) {
        notifyAdd(index, collection.toArray());
        return super.addAll(index,collection);
    }
    public void addAll(int index,Object[] values) {
        notifyAdd(index,values);
        super.addAll(index, Arrays.asList(values));
    }

    public boolean removeAll(Collection value) {
        notifyRemove(0, value.toArray());
        return super.removeAll(value);
    }

    public void clear() {
        super.clear();
    }

    public boolean addAllSilent(int index, Collection collection) {
        return super.addAll(index,collection);
    }
    public boolean addAllSilent(Collection value) {
        return super.addAll(value);
    }

    protected void notifyAdd(int position,Object[] objects) {
        if(objects!=null && objects.length!=0)
        {
            ArrayList list= new ArrayList<>();
            for (Object item:objects) {
                list.add(item);
            }
            if(dataChangeWatcher!=null)
                dataChangeWatcher.itemsAdded(position,list);
        }

    }

    protected void notifyRemove(int position,Object[] objects) {
        if(objects!=null && objects.length!=0)
        {
            ArrayList list= new ArrayList<>();
            for (Object item:objects) {
                list.add(item);
            }
            if(dataChangeWatcher!=null)
                dataChangeWatcher.itemsRemoved(position,list);
        }

    }

    public void reset()
    {
        removeAll(this);
        loadNext();
    }

    public void setPagingSupport(boolean value){
        isPagingSupport =value;
    }
    public boolean getPagingSupport(){
        return isPagingSupport;
    }
    public boolean getRefreshEnabled()
    {
        return isRefreshEnabled;
    }
    public void setRefreshEnabled(boolean value){
        isRefreshEnabled =value;
    }

    private OnCallService onCallService;

    public DataChangeWatcher getDataChangeWatcher() {
        return dataChangeWatcher;
    }

    public void setDataChangeWatcher(DataChangeWatcher dataChangeWatcher) {
        this.dataChangeWatcher = dataChangeWatcher;
    }

    private DataChangeWatcher dataChangeWatcher;

    public PageData getPageData() {
        return pageData;
    }

    protected PageData pageData;

    public boolean isLoading() {
        return isLoading;
    }

    private boolean isLoading=false;
    private boolean isFirstCall=true;
    private boolean isAllPageLoaded=false;
    protected boolean isLoadingRefreshData = false;
    private boolean isPagingSupport=true;
    private boolean isRefreshEnabled=false;



    public DataManager(Context context)
    {
        pageData = new PageData();

    }
    public void garbageCollectorCall()
    {
        onCallService=null;
        pageData =null;
    }

    public void loadNext()
    {

        if(isLoading==false)
        {
            if(canLoadNext())
            {
                try {
                    isLoadingRefreshData = false;

                    invokeService(getNextDataURL(pageData));
                }
                catch (Exception e)
                {
                    Log.e(TAG, "Loading Next data fail: "+getNextDataURL(pageData));
                    noMoreData(e);
                }
            }
            else
            {
                noMoreData(null);
            }
        }
    }
    public void forceLoadNext()
    {
        try {
            isLoadingRefreshData = false;
            invokeService(getNextDataURL(pageData));
        }
        catch (Exception e)
        {
            Log.e(TAG, "Service call fail "+getNextDataURL(pageData));
            noMoreData(e);
        }
    }
    public void loadRefreshData()
    {
        if(isLoading==false)
        {
            if(canLoadRefresh())
            {
                try {
                    isLoadingRefreshData = true;
                    invokeService(getRefreshDataURL(pageData));
                }
                catch (Exception e)
                {
                    Log.e(TAG, "Loading refresh data failed :" + getNextDataURL(pageData));
                }
            }
            else
            {

            }
        }
    }

    private void invokeService(String url) throws Exception
    {

        if(url==null || url.equals(""))
            noMoreData(null);
        else {
            isLoading=true;
            if(onCallService!=null)
                onCallService.onCallStart();
            callService(url);
        }
    }


    public boolean canLoadRefresh()
    {
        if(!getRefreshEnabled())
            return false;
        else if(isLoading)
            return false;
        else if(getPagingSupport()==false)
            return false;
        else if(getRefreshDataURL(pageData)==null)
            return false;
        return true;
    }

    public boolean hasScopeOfRefresh()
    {
        if(!getRefreshEnabled())
            return false;
        else if(getPagingSupport()==false)
            return false;
        else if(getRefreshDataURL(pageData)==null)
            return false;
        return true;
    }

    public boolean canLoadNext()
    {
        if(isLoading)
            return false;
        else if(getNextDataURL(pageData)!=null)
        {
            if(pageData.curruntPage==1)
                return true;
            else if(getPagingSupport())
            {
                if(pageData.curruntPage==pageData.totalPage)
                    return false;
                else if(isAllPageLoaded)
                    return false;
                else
                    return true;
            }
            return true;
        }
        else
            return false;
    }




    public void setOnCallService(OnCallService onCallService)
    {
        this.onCallService = onCallService;
    }
    public OnCallService getOnCallService()
    {
        return onCallService;
    }
    public void noMoreData(Exception e)
    {
        if(onCallService!=null) {
            onCallService.onCallEnd(null,isLoadingRefreshData);
            onCallService.onLastCallEnd();

            if(isFirstCall)
            {
                onCallService.onFirstCallEnd();
                isFirstCall=false;
            }


            if(e!=null)
                onCallService.onDataLoadError(getNextDataURL(pageData),e);
        }
    }
    public void setRefreshItemPos(int value)
    {
        refreshItemPos = value;
    }
    public int getRefreshItemPos()
    {
        return refreshItemPos;
    }
    protected void dataLoadingDone(List<?> list,boolean isRefreshPage)
    {

        if(list!=null) {
            if (isRefreshPage)
                addAll(getRefreshItemPos(), list);
            else
                addAll(list);
        }
        if(onCallService!=null)
            onCallService.onCallEnd(list, isRefreshPage);

        if(pageData.curruntPage == pageData.totalPage)
        {
            isAllPageLoaded = true;
            if(onCallService!=null)
                onCallService.onLastCallEnd();
        }
        else if(isRefreshPage==false && (list==null || list.size()==0))
        {
            isAllPageLoaded = true;
            if(onCallService!=null)
                onCallService.onLastCallEnd();
        }

        if(pageData.curruntPage == 1)
        {
            if(onCallService!=null)
                onCallService.onFirstCallEnd();
        }


    }

    public void dataHandler(String url, Object value, Object status)
    {
        isLoadingRefreshData = false;
        if(onCallService!=null)
            onCallService.onDataReceived(url, value, status);
        isLoading = false;

        if(value != null) {
            try {
                parseData(url, value, status);
            } catch (Exception e) {
                Log.e(TAG, "Data Parshing fialed : "+url);
                dataLoadError(url, e);
            }
        }
        else
            dataLoadError(url, status);
    }


    protected void dataLoadError(String url, Object status)
    {
        if(onCallService!=null)
            onCallService.onDataLoadError(url, status);
        noMoreData(null);
    }

}
