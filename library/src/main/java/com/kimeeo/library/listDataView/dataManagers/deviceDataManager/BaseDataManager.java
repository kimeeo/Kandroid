package com.kimeeo.library.listDataView.dataManagers.deviceDataManager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.IParseableObject;
import com.kimeeo.library.listDataView.dataManagers.PageData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
abstract public class BaseDataManager extends DataManager {

    private static final String LOG_TAG= "BaseDataManager";
    protected Context context;

    public void garbageCollectorCall() {
        super.garbageCollectorCall();
        loadedDataVO=null;
        context=null;
    }

    abstract protected InputStream getInputStream(Context context,String url) throws Exception;
    public BaseDataManager(Context context)
    {
        super(context);
        this.context =context;
    }
    protected void callService(String url)
    {
        try {
            String data=readTxt(getInputStream(context,url));
            dataHandler(url, data, "DONE");
        }catch(Exception e)
        {
            dataLoadingDone(null, false);
        }
    }

    protected String readTxt(InputStream inputStream){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        try {
            i = inputStream.read();
            while (i != -1)
            {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toString();
    }

    protected void dataIn(BaseDataParser value)
    {

    }
    protected BaseDataParser loadedDataVO;

    protected void parseData(String url, Object value, Object status)
    {
        //BaseDataParser oldLoadedDataVO = loadedDataVO;
        boolean isRefreshPage = isRefreshPage(pageData, url);
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
