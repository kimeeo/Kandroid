package com.kimeeo.library.listDataView.recyclerView.verticalViews;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.kimeeo.library.listDataView.recyclerView.BaseGridView;

/**
 * Created by bhavinpadhiyar on 7/20/15.
 */
abstract public class StaggeredGridView extends BaseGridView
{
    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(getColumnsCount(), StaggeredGridLayoutManager.VERTICAL);
        return layoutManager;
    }

}
