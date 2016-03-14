package com.kimeeo.library.listDataView.viewHelper;

import android.content.res.Resources;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.EmptyViewHelper;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.OnCallService;
import com.kimeeo.library.listDataView.listViews.BaseListViewAdapter;
import com.kimeeo.library.listDataView.listViews.EndlessListScrollListener;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
public class ListViewHelper extends BaseHelper implements AdapterView.OnItemClickListener, OnCallService
{
    protected EmptyViewHelper mEmptyViewHelper;
    protected ListView mList;
    protected BaseListViewAdapter mAdapter;
    private DataManager dataManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private OnItemClick onItemClick;

    public ListViewHelper()
    {

    }

    public Resources getResources()
    {
        return mList.getResources();
    }

    public void retry()
    {
        loadNext();
    }

    public ListViewHelper emptyView(View view)
    {
        mEmptyViewHelper = new EmptyViewHelper(view.getContext(), view, this, true, true);
        return this;
    }

    public ListViewHelper emptyView(EmptyViewHelper emptyViewHelper)
    {
        mEmptyViewHelper = emptyViewHelper;
        return this;
    }

    public ListViewHelper with(ListView list)
    {
        this.mList = list;
        return this;
    }

    public ListViewHelper dataManager(DataManager dataManager)
    {
        this.dataManager = dataManager;
        return this;
    }

    public ListViewHelper loadNext() {
        dataManager.loadNext();
        return this;
    }

    public ListViewHelper loadRefreshData()
    {
        dataManager.loadRefreshData();
        return this;
    }

    public ListViewHelper swipeRefreshLayout(SwipeRefreshLayout view)
    {
        mSwipeRefreshLayout = view;
        return this;
    }

    public ListViewHelper adapter(BaseListViewAdapter adapter)
    {
        this.mAdapter = adapter;
        this.mAdapter.setOnCallService(this);

        return this;
    }

    public ListViewHelper setOnItemClick(OnItemClick item) {
        onItemClick=item;
        return this;
    }




    protected void clear()
    {
        if(dataManager!=null) {
            dataManager.garbageCollectorCall();
            dataManager = null;
        }
        if(mAdapter!=null)
            mAdapter.garbageCollectorCall();

        mAdapter =null;
        mList = null;
        if (mEmptyViewHelper != null)
            mEmptyViewHelper.clean();
        mEmptyViewHelper = null;
        mSwipeRefreshLayout=null;
    }



    public ListViewHelper create() throws Exception{

        if(mAdapter==null)
            throw new Exception("Must have mAdapter");
        else if(mList==null)
            throw new Exception("Must have recyclerView");
        else if(dataManager==null)
            throw new Exception("Must have dataManager");

        if(dataManager.getRefreshEnabled())
            configSwipeRefreshLayout(mSwipeRefreshLayout);

        mList.setOnItemClickListener(this);
        mList.setAdapter(mAdapter);
        setOnScrollListener(mList);
        configListView(mList,mAdapter,dataManager);
        loadNext();
        return this;
    }
    protected void configListView(ListView mList,BaseListViewAdapter mAdapter,DataManager dataManager)
    {

    }

    protected void setOnScrollListener(ListView mList)
    {
        mList.setOnScrollListener(new EndlessListScrollListener() {
            @Override
            public void onLoadMore() {
                if(dataManager.canLoadNext())
                    loadNext();
            }
            public void onScrolled(AbsListView view, int firstVisibleItem, int visibleItemCount,int totalItemCount)
            {
                //BUG
                onDataScroll((ListView)view, 0, 0);
            }
        });
    }








    protected void onDataScroll(ListView recyclerView, int dx, int dy)
    {

    }
    protected void configSwipeRefreshLayout(SwipeRefreshLayout view)
    {
        mSwipeRefreshLayout = view;
        if(mSwipeRefreshLayout!=null) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (dataManager.canLoadRefresh())
                        loadRefreshData();
                    else
                        mSwipeRefreshLayout.setRefreshing(false);

                    mSwipeRefreshLayout.setEnabled(dataManager.hasScopeOfRefresh());
                }
            });
            boolean refreshEnabled = dataManager.getRefreshEnabled();
            mSwipeRefreshLayout.setEnabled(refreshEnabled);
            mSwipeRefreshLayout.setColorSchemeColors(R.array.progressColors);
        }
    }

    public void updateSwipeRefreshLayout(boolean isRefreshData)
    {
        if(mSwipeRefreshLayout!=null) {
            mSwipeRefreshLayout.setRefreshing(false);

            if(isRefreshData)
                mSwipeRefreshLayout.setEnabled(dataManager.hasScopeOfRefresh());
        }
    }
    public void onDataLoadError(String url, Object status)
    {
        if (mEmptyViewHelper != null)
            mEmptyViewHelper.updateView(dataManager);
        updateSwipeRefreshLayout(false);
    }
    public void onDataReceived(String url, Object value,Object status)
    {

    }
    public void onCallEnd(List<?> dataList,final boolean isRefreshData)
    {
        if (isRefreshData && mList!=null)
            mList.smoothScrollToPosition(0);


        if (mEmptyViewHelper != null)
            mEmptyViewHelper.updateView(dataManager);
        updateSwipeRefreshLayout(isRefreshData);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Object baseObject = dataManager.get(position);
        if(onItemClick!=null)
            onItemClick.onItemClick(baseObject);
    }


    public void onCallStart()
    {
        if (mEmptyViewHelper != null)
            mEmptyViewHelper.updatesStart();
    }

    public void onFirstCallEnd()
    {

    }
    public void onLastCallEnd()
    {

    }

    public interface OnItemClick
    {
        void onItemClick(Object baseObject);
    }
}
