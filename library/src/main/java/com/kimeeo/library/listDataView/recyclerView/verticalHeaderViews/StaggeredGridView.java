package com.kimeeo.library.listDataView.recyclerView.verticalHeaderViews;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by bhavinpadhiyar on 7/20/15.
 */
abstract public class StaggeredGridView extends BaseHeaderGridView
{
    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(getColumnsCount(), StaggeredGridLayoutManager.VERTICAL);
        return layoutManager;
    }

}
