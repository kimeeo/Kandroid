package com.kimeeo.kandroid.sample.model;


import com.google.gson.internal.LinkedTreeMap;
import com.kimeeo.kandroid.sample.lists.EasyVerticalListView;
import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;
import com.kimeeo.library.listDataView.dataManagers.IJSONParseableObject;
import com.kimeeo.library.listDataView.dataManagers.IParseableObject;
import com.kimeeo.library.model.IFragmentData;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 12/26/15.
 */
public class SampleModelFrgament extends Object implements IJSONParseableObject,IParseableObject,IFragmentData
{
    public  boolean menuBreak=false;
    public  boolean selected=false;
    public  Object param;
    public  Class view;
    public  String id;
    public  String name;
    public  Object actionValue;
    public  String actionType;

    public  String icon;

    public Class getView()
    {
        if(view==null)
            view =EasyVerticalListView.class;
        return view;
    };

    public String getID()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public String getTitle()
    {
        return getName();
    }
    public boolean getBreak()
    {
        return menuBreak;
    }
    public void setBreak(boolean value)
    {
        menuBreak=value;
    }

    public Object getParam()
    {
        return param;
    }

    public void setParam(Object value)
    {
        param=value;
    }
    public String getIcon()
    {
        return icon;
    }
    public Object getActionValue()
    {
        return actionValue;
    }
    public String getActionType()
    {
        return actionType;
    }




    public String title;
    public String subTitle;

    public String image;
    public String details;
    public List<SampleModelFrgament> children;

    public void dataLoaded(LinkedTreeMap<String,Object> data,BaseDataParser FullData)
    {
        this.id = "ABC";
        view =EasyVerticalListView.class;
    }
    public void dataLoaded(BaseDataParser FullData)
    {
        this.id = "ABC";
        view =EasyVerticalListView.class;
    }
}
