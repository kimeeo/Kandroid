package com.kimeeo.kandroid.sample.model;

import com.kimeeo.library.model.IFragmentData;

/**
 * Created by bhavinpadhiyar on 1/11/16.
 */
public class FragmentData implements IFragmentData {
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

    public FragmentData(String id,String name,String actionType,Object actionValue,Class view,String icon)
    {
        this.id = id;
        this.name = name;
        this.actionType = actionType;
        this.actionValue = actionValue;
        this.icon = icon;
        this.view = view;
    }
    public FragmentData()
    {

    }
}