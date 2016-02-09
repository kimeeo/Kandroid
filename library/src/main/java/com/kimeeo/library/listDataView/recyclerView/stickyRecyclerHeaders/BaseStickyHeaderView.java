package com.kimeeo.library.listDataView.recyclerView.stickyRecyclerHeaders;

import android.support.v7.widget.RecyclerView;

import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerView;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
abstract public class BaseStickyHeaderView extends BaseRecyclerView
{
    protected void configRecyclerView(RecyclerView recyclerView,BaseRecyclerViewAdapter mAdapter )
    {
        super.configRecyclerView(recyclerView, mAdapter);
        if(mAdapter instanceof StickyRecyclerHeadersAdapter)
        {
            StickyRecyclerHeadersAdapter adapter = (StickyRecyclerHeadersAdapter) mAdapter;
            mAdapter.supportLoader = false;
            final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
            recyclerView.addItemDecoration(headersDecor);
            mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    headersDecor.invalidateHeaders();
                }
            });
        }
    }

}
