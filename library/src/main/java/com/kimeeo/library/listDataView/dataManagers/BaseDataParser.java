package com.kimeeo.library.listDataView.dataManagers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhavinpadhiyar on 12/25/15.
 */
abstract public class BaseDataParser {

    abstract public Class<Object> getManagedObjectClass();
    abstract public int getActivePageIndex();
    abstract public int getTotalPageCount();
    abstract public int getPageSize();
    abstract public String getNextPageURL();
    abstract public String getRefreshPagePageURL();
    abstract public List<?> getList();
    abstract public Object getData();
    abstract public boolean parseRequired();

    private static final String LOG_TAG= "BaseDataParser";

    public List<?> typeCastList(List<?> list,Class<Object> iBaseObjectClass)
    {
        Object iBaseObject;
        List<Object> dataList = new ArrayList<Object>();
        LinkedTreeMap<String, Object> listItem;
        for (int i = 0; i < list.size(); i++) {
            try {
                listItem = (LinkedTreeMap<String, Object>) list.get(i);
                iBaseObject = parse(listItem, iBaseObjectClass);

                if (iBaseObject instanceof IJSONParseableObject)
                    ((IJSONParseableObject) iBaseObject).dataLoaded(listItem, this);

                if (iBaseObject instanceof IParseableObject)
                    ((IParseableObject) iBaseObject).dataLoaded(this);

                dataList.add(iBaseObject);
            } catch (Exception e) {
                Log.e(LOG_TAG, "JSON PARSHING FAIL :index=" + i + "  : data" + list.get(i).toString());
            }
        }
        return dataList;
    }

    public void typeCastData()
    {

    }
    public Object parse(LinkedTreeMap<String,Object> data,Class<Object> clazz) throws Exception
    {
        Gson gson= new Gson();
        Type type = data.getClass();
        String json=  gson.toJson(data, type);
        Object object = gson.fromJson(json, clazz);
        return object;
    }
}
