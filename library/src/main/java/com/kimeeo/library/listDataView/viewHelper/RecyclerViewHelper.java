package com.kimeeo.library.listDataView.viewHelper;

import android.content.res.Resources;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.EmptyViewHelper;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.OnCallService;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.EndlessRecyclerOnScrollListener;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
public class RecyclerViewHelper extends BaseHelper implements AdapterView.OnItemClickListener, OnCallService
{
    protected EmptyViewHelper mEmptyViewHelper;
    protected BaseRecyclerViewAdapter mAdapter;
    boolean showInternetError = true;
    boolean showInternetRetry = true;
    private RecyclerView.ItemAnimator itemAnimator;
    private int animatorDuration = 200;
    private RecyclerView recyclerView;
    private DataManager dataManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemDecoration itemDecoration;
    private OnItemClick onItemClick;

    public RecyclerViewHelper()
    {

    }

    public Resources getResources() {
        return recyclerView.getResources();
    }

    public void retry() {
        loadNext();
    }

    public RecyclerViewHelper emptyView(View view) {
        mEmptyViewHelper = new EmptyViewHelper(view.getContext(), view, this, showInternetError, showInternetRetry);
        return this;
    }

    public RecyclerViewHelper showInternetError(boolean showInternetError) {
        this.showInternetError = showInternetError;
        return this;
    }

    public RecyclerViewHelper showInternetRetry(boolean showInternetRetry) {
        this.showInternetRetry = showInternetRetry;
        return this;
    }

    public RecyclerViewHelper emptyView(EmptyViewHelper emptyViewHelper) {
        mEmptyViewHelper = emptyViewHelper;
        return this;
    }

    public RecyclerViewHelper animator(RecyclerView.ItemAnimator itemAnimator)
    {
        this.itemAnimator=itemAnimator;
        return this;
    }

    public RecyclerViewHelper animatorDuration(int value)
    {
        animatorDuration=value;
        return this;
    }

    public RecyclerViewHelper with(RecyclerView recyclerView)
    {
        this.recyclerView =recyclerView;
        return this;
    }

    public RecyclerViewHelper dataManager(DataManager dataManager)
    {
        this.dataManager =dataManager;
        return this;
    }

    public RecyclerViewHelper loadNext(){
        dataManager.loadNext();
        return this;
    }

    public RecyclerViewHelper loadRefreshData()
    {
        dataManager.loadRefreshData();
        return this;
    }

    public RecyclerViewHelper swipeRefreshLayout(SwipeRefreshLayout view)
    {
        mSwipeRefreshLayout =view;
        return this;
    }

    public RecyclerViewHelper layoutManager(RecyclerView.LayoutManager layoutManager)
    {
        this.layoutManager = layoutManager;
        return this;
    }

    public RecyclerViewHelper adapter(BaseRecyclerViewAdapter adapter)
    {
        this.mAdapter = adapter;
        this.mAdapter.setOnCallService(this);
        return this;
    }

    public RecyclerViewHelper decoration(RecyclerView.ItemDecoration item) {
        this.itemDecoration = item;
        return this;
    }

    public RecyclerViewHelper setOnItemClick(OnItemClick item) {
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
        recyclerView = null;

        if (mEmptyViewHelper != null)
            mEmptyViewHelper.clean();
        mEmptyViewHelper = null;
        mSwipeRefreshLayout=null;
    }



    public RecyclerViewHelper create() throws Exception{


        if(layoutManager==null)
            throw new Exception("Must have layoutManager");
        else if(mAdapter==null)
            throw new Exception("Must have mAdapter");
        else if(recyclerView==null)
            throw new Exception("Must have recyclerView");
        else if(dataManager==null)
            throw new Exception("Must have dataManager");

        recyclerView.setLayoutManager(layoutManager);
        if(itemDecoration!=null)
            recyclerView.addItemDecoration(itemDecoration);

        if(dataManager.getRefreshEnabled())
            configSwipeRefreshLayout(mSwipeRefreshLayout);

        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
        setOnScrollListener(recyclerView);
        setItemAnimator(recyclerView);
        configRecyclerView(recyclerView,mAdapter,dataManager);
        loadNext();
        return this;
    }
    protected void configRecyclerView(RecyclerView recyclerView,BaseRecyclerViewAdapter mAdapter,DataManager dataManager)
    {

    }


    protected void setItemAnimator(RecyclerView mList)
    {
        if(itemAnimator!=null) {
            itemAnimator.setAddDuration(animatorDuration);
            itemAnimator.setChangeDuration(animatorDuration);
            itemAnimator.setMoveDuration(animatorDuration);
            itemAnimator.setRemoveDuration(animatorDuration);
            mList.setItemAnimator(itemAnimator);
        }
    }






    protected void setOnScrollListener(RecyclerView mList)
    {
        mList.setOnScrollListener(new EndlessRecyclerOnScrollListener(mList.getLayoutManager()) {
            @Override
            public void onLoadMore() {
                if(dataManager.canLoadNext())
                    loadNext();
            }
            public void onScroll(RecyclerView recyclerView, int dx, int dy)
            {
                onDataScroll(recyclerView, dx, dy);
            }
        });
    }








    protected void onDataScroll(RecyclerView recyclerView, int dx, int dy)
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
        if (isRefreshData)
            recyclerView.scrollToPosition(0);


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
