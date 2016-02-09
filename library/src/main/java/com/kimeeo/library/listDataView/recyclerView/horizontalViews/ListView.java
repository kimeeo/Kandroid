package com.kimeeo.library.listDataView.recyclerView.horizontalViews;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kimeeo.library.listDataView.recyclerView.DefaultRecyclerView;

/**
 * Created by bhavinpadhiyar on 7/17/15.
 */
abstract public class ListView extends DefaultRecyclerView
{
    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        return linearLayoutManager;
    }
}
