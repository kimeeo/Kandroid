package com.kimeeo.library.listDataView.recyclerView.stickyRecyclerHeaders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.IViewProvider;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
abstract public class DefaultStickyHeaderView extends BaseStickyHeaderView implements IViewProvider,IStickyViewProvider
{
    protected void configRecyclerView(RecyclerView recyclerView,BaseRecyclerViewAdapter mAdapter )
    {
        super.configRecyclerView(recyclerView, mAdapter);
        if(mAdapter instanceof StickyRecyclerHeadersAdapter)
        {
            StickyRecyclerHeadersAdapter adapter = (StickyRecyclerHeadersAdapter) mAdapter;
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

    abstract public View getStickyItemView(ViewGroup container);
    abstract public BaseItemHolder getStickyItemHolder(View view);
    abstract public long getHeaderId(int position);

    protected BaseRecyclerViewAdapter createListViewAdapter()
    {
        return new DefaultStickyRecyclerViewAdapter(getDataManager(),this,this,this);
    }
}
