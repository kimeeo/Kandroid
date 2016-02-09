package com.kimeeo.library.listDataView.viewHelper;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.OnCallService;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.EndlessRecyclerOnScrollListener;
import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
public class RecyclerViewHelper implements AdapterView.OnItemClickListener,OnCallService
{

    public RecyclerViewHelper()
    {

    }

    private RecyclerView.ItemAnimator itemAnimator;
    public RecyclerViewHelper animator(RecyclerView.ItemAnimator itemAnimator)
    {
        this.itemAnimator=itemAnimator;
        return this;
    }

    private int animatorDuration=200;
    public RecyclerViewHelper animatorDuration(int value)
    {
        animatorDuration=value;
        return this;
    }



    private RecyclerView recyclerView;
    public RecyclerViewHelper with(RecyclerView recyclerView)
    {
        this.recyclerView =recyclerView;
        return this;
    }


    private DataManager dataManager;
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
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public RecyclerViewHelper swipeRefreshLayout(SwipeRefreshLayout view)
    {
        mSwipeRefreshLayout =view;
        return this;
    }
    private RecyclerView.LayoutManager layoutManager;
    public RecyclerViewHelper layoutManager(RecyclerView.LayoutManager layoutManager)
    {
        this.layoutManager = layoutManager;
        return this;
    }


    protected View mEmptyView;
    public RecyclerViewHelper emptyView(View view)
    {
        mEmptyView=view;
        if(mEmptyView!=null)
            mEmptyView.setVisibility(View.GONE);

        return this;
    }





    protected ImageView mEmptyViewImage;

    public RecyclerViewHelper emptyImageView(ImageView view)
    {
        mEmptyViewImage=view;

        if(mEmptyViewImage!=null && emptyViewDrawable!=null)
            mEmptyViewImage.setImageDrawable(emptyViewDrawable);


        return this;
    }

    protected TextView mEmptyViewMessage;
    public RecyclerViewHelper emptyMessageView(TextView view)
    {
        mEmptyViewMessage=view;
        if(mEmptyViewMessage!=null && emptyViewMessage!=null)
            mEmptyViewMessage.setText(emptyViewMessage);
        return this;
    }
    Drawable emptyViewDrawable;
    protected RecyclerViewHelper emptyViewDrawable(Drawable drawable)
    {
        emptyViewDrawable=drawable;
        if(mEmptyViewImage!=null && emptyViewDrawable!=null)
            mEmptyViewImage.setImageDrawable(emptyViewDrawable);
        return this;
    }
    String emptyViewMessage;
    protected RecyclerViewHelper emptyViewMessage(String emptyViewMessage)
    {
        this.emptyViewMessage = emptyViewMessage;
        if(mEmptyViewMessage!=null && emptyViewMessage!=null)
            mEmptyViewMessage.setText(emptyViewMessage);
        return this;
    }




    protected BaseRecyclerViewAdapter mAdapter;
    public RecyclerViewHelper adapter(BaseRecyclerViewAdapter adapter)
    {
        this.mAdapter = adapter;
        this.mAdapter.setOnCallService(this);
        return this;
    }


    private RecyclerView.ItemDecoration itemDecoration;
    public RecyclerViewHelper decoration(RecyclerView.ItemDecoration item) {
        this.itemDecoration = item;
        return this;
    }

    private OnItemClick onItemClick;
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
        mEmptyView =null;
        if(mEmptyViewImage!=null)
            mEmptyViewImage.setImageBitmap(null);
        mEmptyViewImage=null;
        mEmptyViewMessage=null;
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
        if (isRefreshData)
            recyclerView.scrollToPosition(0);


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
