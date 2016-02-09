package com.kimeeo.kandroid.sample.model;


import com.google.gson.internal.LinkedTreeMap;
import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;
import com.kimeeo.library.listDataView.dataManagers.IJSONParseableObject;
import com.kimeeo.library.listDataView.dataManagers.IParseableObject;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 12/26/15.
 */
public class SampleModel extends Object implements IJSONParseableObject,IParseableObject
{
    public String name;
    public String title;
    public String subTitle;
    public String icon;
    public String id;
    public String image;
    public String details;
    public List<SampleModel> children;

    public void dataLoaded(LinkedTreeMap<String,Object> data,BaseDataParser FullData)
    {

    }
    public void dataLoaded(BaseDataParser FullData)
    {

    }
}
