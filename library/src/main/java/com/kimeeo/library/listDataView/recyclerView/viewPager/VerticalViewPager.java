package com.kimeeo.library.listDataView.recyclerView.viewPager;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by bhavinpadhiyar on 1/21/16.
 */
abstract public class VerticalViewPager extends BaseViewPager
{
    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        return linearLayoutManager;
    }
}