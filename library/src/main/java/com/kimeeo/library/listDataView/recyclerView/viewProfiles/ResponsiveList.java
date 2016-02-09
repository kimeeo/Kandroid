package com.kimeeo.library.listDataView.recyclerView.viewProfiles;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.kimeeo.library.listDataView.recyclerView.BaseProfileRecyclerView;

/**
 * Created by bhavinpadhiyar on 1/12/16.
 */
abstract public class ResponsiveList extends VerticalGrid {

    protected boolean getVariableHeight()
    {
        return true;
    }

    public ResponsiveList(String name, BaseProfileRecyclerView host) {
        super(name, host);

    }

    public RecyclerView.LayoutManager createLayoutManager() {
        RecyclerView.LayoutManager layoutManager;
        int columns = getColumnsCount();
        if(columns==1)
        {
            layoutManager = new LinearLayoutManager(getHost().getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        else
        {
            if(getVariableHeight())
                layoutManager = new StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL);
            else
                layoutManager = new GridLayoutManager(getHost().getActivity(), columns, GridLayoutManager.VERTICAL, false);
        }
        return layoutManager;
    }
}
