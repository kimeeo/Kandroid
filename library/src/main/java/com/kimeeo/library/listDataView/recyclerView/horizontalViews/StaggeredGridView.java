package com.kimeeo.library.listDataView.recyclerView.horizontalViews;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.kimeeo.library.listDataView.recyclerView.BaseGridView;

/**
 * Created by bhavinpadhiyar on 7/20/15.
 */
abstract public class StaggeredGridView extends BaseGridView
{
    protected RecyclerView.LayoutManager createLayoutManager() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(getColumnsCount(), StaggeredGridLayoutManager.HORIZONTAL);
        return layoutManager;
    }
}
