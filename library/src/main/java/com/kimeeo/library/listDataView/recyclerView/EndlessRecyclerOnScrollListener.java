package com.kimeeo.library.listDataView.recyclerView;

/**
 * Created by bhavinpadhiyar on 7/22/15.
 */
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;


    private RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerOnScrollListener(RecyclerView.LayoutManager linearLayoutManager)
    {
        this.mLayoutManager = linearLayoutManager;
    }


        @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
            onScroll(recyclerView, dx, dy);
            visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLayoutManager.getItemCount();

        if(mLayoutManager instanceof LinearLayoutManager)
            firstVisibleItem = ((LinearLayoutManager)mLayoutManager).findFirstVisibleItemPosition();
        else if(mLayoutManager instanceof GridLayoutManager)
            firstVisibleItem = ((GridLayoutManager)mLayoutManager).findFirstVisibleItemPosition();
        else if(mLayoutManager instanceof StaggeredGridLayoutManager)
        {
            int[] firstVisibleItems = null;
            firstVisibleItems = ((StaggeredGridLayoutManager) mLayoutManager).findFirstVisibleItemPositions(firstVisibleItems);
            if(firstVisibleItems != null && firstVisibleItems.length > 0) {
                firstVisibleItem = firstVisibleItems[0];
            }
        }
        if (loading)
        {
            if (totalItemCount > previousTotal)
            {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {

            onLoadMore();

            loading = true;
        }
    }

    public abstract void onLoadMore();
    public abstract void onScroll(RecyclerView recyclerView, int dx, int dy);
}