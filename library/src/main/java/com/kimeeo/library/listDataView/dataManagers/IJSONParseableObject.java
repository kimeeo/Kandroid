package com.kimeeo.library.listDataView.dataManagers;

import com.google.gson.internal.LinkedTreeMap;

/**
 * Created by bhavinpadhiyar on 12/25/15.
 */
public interface IJSONParseableObject {
    void dataLoaded(LinkedTreeMap<String,Object> data,BaseDataParser entireData);
}
