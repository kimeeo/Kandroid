package com.kimeeo.library.listDataView.recyclerView.viewProfiles;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kimeeo.library.listDataView.recyclerView.BaseProfileRecyclerView;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.DefaultRecyclerViewAdapter;

/**
 * Created by bhavinpadhiyar on 1/12/16.
 */
abstract public class VerticalViewPager extends BaseViewProfile {
    public VerticalViewPager(String name, BaseProfileRecyclerView host)
    {
        super(name,host);
    }

    public RecyclerView.LayoutManager createLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getHost().getActivity(), LinearLayoutManager.VERTICAL, false);
        return linearLayoutManager;
    }

    public BaseRecyclerViewAdapter createListViewAdapter()
    {
        return new DefaultRecyclerViewAdapter(getHost().getDataManager(),getHost(),this);
    }
}
