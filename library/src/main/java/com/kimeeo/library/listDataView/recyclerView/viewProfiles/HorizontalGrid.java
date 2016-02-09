package com.kimeeo.library.listDataView.recyclerView.viewProfiles;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kimeeo.library.listDataView.recyclerView.BaseProfileRecyclerView;

/**
 * Created by bhavinpadhiyar on 1/12/16.
 */
abstract public class HorizontalGrid extends VerticalGrid
{
    public HorizontalGrid(String name, BaseProfileRecyclerView host)
    {
        super(name,host);
    }
    public RecyclerView.LayoutManager createLayoutManager()
    {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getHost().getActivity(), getColumnsCount(), GridLayoutManager.HORIZONTAL, false);
        return gridLayoutManager;
    }
}