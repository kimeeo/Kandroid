package com.kimeeo.library.listDataView.dataManagers;

import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
public interface IListProvider {
    List<?> getList(PageData data,Map<String, Object> param);
}
