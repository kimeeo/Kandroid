/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kimeeo.library.listDataView.listViews;

import android.widget.AbsListView;

/**
 * Created by bhavinpadhiyar on 7/28/15.
 */
abstract public class EndlessListScrollListener implements AbsListView.OnScrollListener {

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;

    public EndlessListScrollListener() {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
        onScrolled(view,firstVisibleItem,visibleItemCount,totalItemCount);


        if (loading)
        {
            if (totalItemCount > previousTotal)
            {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold))
        {
            onLoadMore();
            loading = true;
        }
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    public abstract void onLoadMore();
    public abstract void onScrolled(AbsListView view, int firstVisibleItem, int visibleItemCount,int totalItemCount);

}
