package com.kimeeo.library.listDataView.recyclerView.horizontalViews;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kimeeo.library.listDataView.recyclerView.BaseGridView;

/**
 * Created by bhavinpadhiyar on 7/17/15.
 */
abstract public class GridView extends BaseGridView
{
    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), getColumnsCount(), GridLayoutManager.HORIZONTAL, false);
        return gridLayoutManager;
    }
}