package com.kimeeo.library.listDataView.recyclerView.viewProfiles;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kimeeo.library.listDataView.recyclerView.BaseProfileRecyclerView;

/**
 * Created by bhavinpadhiyar on 1/12/16.
 */
abstract public class HorizontalList extends VerticalList {
    public HorizontalList(String name, BaseProfileRecyclerView host)
    {
        super(name,host);
    }

    public RecyclerView.LayoutManager createLayoutManager()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getHost().getActivity(), LinearLayoutManager.HORIZONTAL, false);
        return linearLayoutManager;
    }
}
