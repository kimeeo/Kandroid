package com.kimeeo.library.listDataView.viewHelper;

import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.OnCallService;
import com.kimeeo.library.listDataView.listViews.BaseListViewAdapter;
import com.kimeeo.library.listDataView.listViews.EndlessListScrollListener;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
public class ListViewHelper implements AdapterView.OnItemClickListener,OnCallService
{

    public ListViewHelper()
    {

    }

    protected ListView mList;
    public ListViewHelper with(ListView list)
    {
        this.mList =list;
        return this;
    }


    private DataManager dataManager;
    public ListViewHelper dataManager(DataManager dataManager)
    {
        this.dataManager =dataManager;
        return this;
    }

    public ListViewHelper loadNext(){
        dataManager.loadNext();
        return this;
    }
    public ListViewHelper loadRefreshData()
    {
        dataManager.loadRefreshData();
        return this;
    }
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public ListViewHelper swipeRefreshLayout(SwipeRefreshLayout view)
    {
        mSwipeRefreshLayout =view;
        return this;
    }


    protected View mEmptyView;
    public ListViewHelper emptyView(View view)
    {
        mEmptyView=view;
        if(mEmptyView!=null)
            mEmptyView.setVisibility(View.GONE);

        return this;
    }





    protected ImageView mEmptyViewImage;

    public ListViewHelper emptyImageView(ImageView view)
    {
        mEmptyViewImage=view;

        if(mEmptyViewImage!=null && emptyViewDrawable!=null)
            mEmptyViewImage.setImageDrawable(emptyViewDrawable);


        return this;
    }

    protected TextView mEmptyViewMessage;
    public ListViewHelper emptyMessageView(TextView view)
    {
        mEmptyViewMessage=view;
        if(mEmptyViewMessage!=null && emptyViewMessage!=null)
            mEmptyViewMessage.setText(emptyViewMessage);
        return this;
    }
    Drawable emptyViewDrawable;
    protected ListViewHelper emptyViewDrawable(Drawable drawable)
    {
        emptyViewDrawable=drawable;
        if(mEmptyViewImage!=null && emptyViewDrawable!=null)
            mEmptyViewImage.setImageDrawable(emptyViewDrawable);
        return this;
    }
    String emptyViewMessage;
    protected ListViewHelper emptyViewMessage(String emptyViewMessage)
    {
        this.emptyViewMessage = emptyViewMessage;
        if(mEmptyViewMessage!=null && emptyViewMessage!=null)
            mEmptyViewMessage.setText(emptyViewMessage);
        return this;
    }




    protected BaseListViewAdapter mAdapter;
    public ListViewHelper adapter(BaseListViewAdapter adapter)
    {
        this.mAdapter = adapter;
        this.mAdapter.setOnCallService(this);
        return this;
    }



    private OnItemClick onItemClick;
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
        mEmptyView =null;
        if(mEmptyViewImage!=null)
            mEmptyViewImage.setImageBitmap(null);
        mEmptyViewImage=null;
        mEmptyViewMessage=null;
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
        if(mEmptyView!=null)
        {
            if(dataManager.size()==0)
                mEmptyView.setVisibility(View.VISIBLE);
            else
                mEmptyView.setVisibility(View.GONE);
        }
        updateSwipeRefreshLayout(false);
    }
    public void onDataReceived(String url, Object value,Object status)
    {

    }
    public void onCallEnd(List<?> dataList,final boolean isRefreshData)
    {
        if (isRefreshData && mList!=null)
            mList.smoothScrollToPosition(0);


        if(mEmptyView!=null)
        {
            if(dataManager.size()==0)
                mEmptyView.setVisibility(View.VISIBLE);
            else
                mEmptyView.setVisibility(View.GONE);
        }

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
        if(mEmptyView!=null)
            mEmptyView.setVisibility(View.GONE);
    }

    public void onFirstCallEnd()
    {

    }
    public void onLastCallEnd()
    {

    }
    public static interface OnItemClick
    {
        void onItemClick(Object baseObject);
    }
}
