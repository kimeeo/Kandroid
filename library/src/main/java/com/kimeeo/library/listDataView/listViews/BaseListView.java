package com.kimeeo.library.listDataView.listViews;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.BaseListDataView;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/20/16.
 */
abstract public class BaseListView extends BaseListDataView implements AdapterView.OnItemClickListener{
    abstract protected BaseListViewAdapter createListViewAdapter();

    protected ListView mList;

    public View getRootView() {
        return mRootView;
    }

    protected View mRootView;
    protected View mEmptyView;
    protected ImageView mEmptyViewImage;
    protected TextView mEmptyViewMessage;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    protected BaseListViewAdapter mAdapter;


    protected void garbageCollectorCall()
    {
        super.garbageCollectorCall();
        mList = null;
        if(mAdapter!=null)
            mAdapter.garbageCollectorCall();
        mAdapter =null;
        mEmptyView =null;
        if(mEmptyViewImage!=null)
            mEmptyViewImage.setImageBitmap(null);
        mEmptyViewImage=null;
        mEmptyViewMessage=null;
        mSwipeRefreshLayout=null;
    }
    protected ListView getListView()
    {
        return mList;
    }
    public BaseListViewAdapter getAdapter()
    {
        return mAdapter;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        configViewParam();
        mRootView = createRootView(inflater, container, savedInstanceState);
        if(getDataManager().getRefreshEnabled())
            configSwipeRefreshLayout(createSwipeRefreshLayout(mRootView));

        mList = createListView(mRootView);
        mEmptyView= createEmptyView(mRootView);

        mAdapter = createListViewAdapter();
        mList.setOnItemClickListener(this);
        mList.setAdapter(mAdapter);
        configListView(mList, mAdapter);
        setOnScrollListener(mList);
        loadNext();
        onViewCreated(mRootView);
        return mRootView;
    }

    public void onViewCreated(View view) {

    }
    //Confgi Your RecycleVIew Here
    protected void configListView(ListView mList,BaseListViewAdapter mAdapter)
    {

    }



    protected void setOnScrollListener(ListView mList)
    {
        mList.setOnScrollListener(new EndlessListScrollListener() {
            @Override
            public void onLoadMore() {
                if(getDataManager().canLoadNext())
                    loadNext();
            }
            public void onScrolled(AbsListView view, int firstVisibleItem, int visibleItemCount,int totalItemCount)
            {
                //BUG
                onDataScroll((ListView)view, 0, 0);
            }
        });
    }

    protected View createRootView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView;
        if(getDataManager().getRefreshEnabled())
            rootView = inflater.inflate(R.layout._fragment_list_view_with_swipe_refresh_layout, container, false);
        else
            rootView = inflater.inflate(R.layout._fragment_list_view, container, false);
        return rootView;
    }
    protected ListView createListView(View rootView)
    {
        ListView recyclerView = (ListView) rootView.findViewById(R.id.listView);
        return recyclerView;
    }

    protected Drawable getEmptyViewDrawable()
    {
        Drawable drawable =getResources().getDrawable(R.drawable._empty_box);
        drawable.setColorFilter(getResources().getColor(R.color._emptyViewMessageColor), PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }
    protected String getEmptyViewMessage()
    {
        return getResources().getString(R.string._emptyViewMessage);
    }
    public ImageView getEmptyImageView(View rootView)
    {
        return mEmptyViewImage;
    }
    public TextView getEmptyMessageView(View rootView)
    {
        return mEmptyViewMessage;
    }
    public View getEmptyView()
    {
        return mEmptyView;
    }



    protected View createEmptyView(View rootView)
    {
        View emptyView = rootView.findViewById(R.id.emptyView);
        if(rootView.findViewById(R.id.emptyViewImage)!=null && rootView.findViewById(R.id.emptyViewImage) instanceof ImageView) {
            mEmptyViewImage = (ImageView) rootView.findViewById(R.id.emptyViewImage);
            mEmptyViewImage.setImageDrawable(getEmptyViewDrawable());
        }

        if(rootView.findViewById(R.id.emptyViewMessage)!=null && rootView.findViewById(R.id.emptyViewMessage) instanceof TextView) {
            mEmptyViewMessage = (TextView) rootView.findViewById(R.id.emptyViewMessage);
            mEmptyViewMessage.setText(getEmptyViewMessage());
        }

        if(emptyView!=null)
            emptyView.setVisibility(View.GONE);
        return emptyView;
    }

    protected void onDataScroll(ListView listView, int dx, int dy)
    {

    }
    protected SwipeRefreshLayout getSwipeRefreshLayout()
    {
        return mSwipeRefreshLayout;
    }
    protected void configSwipeRefreshLayout(SwipeRefreshLayout view)
    {
        mSwipeRefreshLayout = view;
        if(mSwipeRefreshLayout!=null) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (getDataManager().canLoadRefresh())
                        loadRefreshData();
                    else
                        mSwipeRefreshLayout.setRefreshing(false);

                    mSwipeRefreshLayout.setEnabled(getDataManager().hasScopeOfRefresh());
                }
            });
            boolean refreshEnabled = getDataManager().getRefreshEnabled();
            mSwipeRefreshLayout.setEnabled(refreshEnabled);
            mSwipeRefreshLayout.setColorSchemeColors(R.array.progressColors);
        }
    }
    protected SwipeRefreshLayout createSwipeRefreshLayout(View rootView)
    {
        if(rootView.findViewById(R.id.swipeRefreshLayout)!=null) {
            SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            return swipeRefreshLayout;
        }
        return null;
    }
    public void updateSwipeRefreshLayout(boolean isRefreshData)
    {
        if(mSwipeRefreshLayout!=null) {
            mSwipeRefreshLayout.setRefreshing(false);

            if(isRefreshData)
                mSwipeRefreshLayout.setEnabled(getDataManager().hasScopeOfRefresh());
        }
    }
    public void onDataLoadError(String url, Object status)
    {
        if(mEmptyView!=null)
        {
            if(getDataManager().size()==0)
                mEmptyView.setVisibility(View.VISIBLE);
            else
                mEmptyView.setVisibility(View.GONE);
        }
        updateSwipeRefreshLayout(false);
    }
    public void onDataReceived(String url, Object value,Object status)
    {
        updateSwipeRefreshLayout(false);
    }
    public void onCallEnd(List<?> dataList,final boolean isRefreshData)
    {
        if (isRefreshData && mList!=null)
            mList.smoothScrollToPosition(0);


        if(mEmptyView!=null)
        {
            if(getDataManager().size()==0)
                mEmptyView.setVisibility(View.VISIBLE);
            else
                mEmptyView.setVisibility(View.GONE);
        }

        updateSwipeRefreshLayout(isRefreshData);

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Object baseObject = getDataManager().get(position);
        onItemClick(baseObject);
    }

    public void onItemClick(Object baseObject)
    {

    }


    public void onCallStart()
    {
        if(mEmptyView!=null)
            mEmptyView.setVisibility(View.GONE);
    }

    public void onFirstCallEnd()
    {

    }
    public void onLastCallEnd()
    {

    }
}
