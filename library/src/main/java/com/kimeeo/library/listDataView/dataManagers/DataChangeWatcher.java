package com.kimeeo.library.listDataView.dataManagers;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 12/25/15.
 */
public interface DataChangeWatcher
{
    void itemsAdded(List items);
    void itemsRemoved(List items);
}