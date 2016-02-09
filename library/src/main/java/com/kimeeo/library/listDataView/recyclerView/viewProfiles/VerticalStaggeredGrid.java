package com.kimeeo.library.listDataView.recyclerView.viewProfiles;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.kimeeo.library.listDataView.recyclerView.BaseProfileRecyclerView;

/**
 * Created by bhavinpadhiyar on 1/12/16.
 */
abstract public class VerticalStaggeredGrid extends VerticalGrid {

    public VerticalStaggeredGrid(String name, BaseProfileRecyclerView host)
    {
        super(name,host);

    }
    public RecyclerView.LayoutManager createLayoutManager()
    {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(getColumnsCount(), StaggeredGridLayoutManager.VERTICAL);
        return layoutManager;
    }
}
