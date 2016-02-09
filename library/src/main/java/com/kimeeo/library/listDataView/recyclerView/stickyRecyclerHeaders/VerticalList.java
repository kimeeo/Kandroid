package com.kimeeo.library.listDataView.recyclerView.stickyRecyclerHeaders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
abstract public class VerticalList extends DefaultStickyHeaderView {
    protected RecyclerView.LayoutManager createLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        return linearLayoutManager;
    }
}
