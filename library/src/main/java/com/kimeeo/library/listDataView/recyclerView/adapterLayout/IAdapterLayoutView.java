package com.kimeeo.library.listDataView.recyclerView.adapterLayout;

import android.support.v7.widget.RecyclerView;

/**
 * Created by bhavinpadhiyar on 2/16/16.
 */
public interface IAdapterLayoutView {
    void setAdapter(RecyclerView.Adapter adapter);
    RecyclerView.Adapter getAdapter();
    RecyclerView.ViewHolder getViewHolderAt(int index);
}
