package com.kimeeo.library.listDataView.recyclerView.verticalHeaderViews;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by bhavinpadhiyar on 7/17/15.
 */
abstract public class ListView extends DefaultHeaderRecyclerView
{
    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        return linearLayoutManager;
    }
}
