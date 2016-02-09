package com.kimeeo.library.listDataView.recyclerView.verticalViews;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.kimeeo.library.listDataView.recyclerView.BaseGridView;

/**
 * Created by bhavinpadhiyar on 7/17/15.
 */
abstract public class ResponsiveView extends BaseGridView
{
    protected boolean getVariableHeight()
    {
        return true;
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        RecyclerView.LayoutManager layoutManager;
        int columns = getColumnsCount();
        if(columns==1)
        {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        else
        {
            if(getVariableHeight())
                layoutManager = new StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL);
            else
                layoutManager = new GridLayoutManager(getActivity(), columns, GridLayoutManager.VERTICAL, false);
        }
        return layoutManager;
    }
}