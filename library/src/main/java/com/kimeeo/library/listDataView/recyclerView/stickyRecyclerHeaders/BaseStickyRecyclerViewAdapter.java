package com.kimeeo.library.listDataView.recyclerView.stickyRecyclerHeaders;

import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.OnCallService;
import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
abstract public class BaseStickyRecyclerViewAdapter extends BaseRecyclerViewAdapter implements StickyRecyclerHeadersAdapter<BaseItemHolder> {
    public BaseStickyRecyclerViewAdapter(DataManager dataManager,OnCallService onCallService)
    {
        super(dataManager,onCallService);
        supportLoader = false;
    }


    abstract protected View getStickyItemView(ViewGroup container);
    abstract protected BaseItemHolder getStickyItemHolder(View view);


    @Override
    public BaseItemHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = getStickyItemView(parent);
        return getStickyItemHolder(view);
    }


    public void onBindHeaderViewHolder(BaseItemHolder holder, int position)
    {
        Object item = getDataManager().get(position);
        holder.updateItemView(item, position);
    }
}
