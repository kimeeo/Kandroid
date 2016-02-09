package com.kimeeo.library.listDataView.listViews.swipeDeck;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daprlabs.cardstack.SwipeDeck;
import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.listViews.BaseListView;
import com.kimeeo.library.listDataView.listViews.BaseListViewAdapter;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhavinpadhiyar on 2/4/16.
 */
abstract public class BaseSwipeDeck extends BaseListView {


    private ProgressView mProgressBar;
    private SwipeDeck swipeDeck;
    private List<Object> dataSwiped;
    protected void garbageCollectorCall()
    {
        super.garbageCollectorCall();
        mProgressBar=null;
        swipeDeck=null;
        dataSwiped=null;
    }

    protected View createRootView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        if(getDataManager().getRefreshEnabled())
            return inflater.inflate(R.layout._fragment_swipe_deck_view_with_swipe_refresh_layout, container, false);
        else
            return inflater.inflate(R.layout._fragment_swipe_deck_view, container, false);
    }
    protected com.daprlabs.cardstack.SwipeDeck createSwipeDeckView(View rootView)
    {
        return (SwipeDeck) rootView.findViewById(R.id.listView);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        configViewParam();
        mRootView = createRootView(inflater, container, savedInstanceState);
        if(getDataManager().getRefreshEnabled())
            configSwipeRefreshLayout(createSwipeRefreshLayout(mRootView));

        swipeDeck = createSwipeDeckView(mRootView);
        mEmptyView= createEmptyView(mRootView);
        mAdapter = createListViewAdapter();
        mAdapter.supportLoader=false;

        dataSwiped = new ArrayList<>();

        swipeDeck.setAdapter(mAdapter);
        swipeDeck.setHardwareAccelerationEnabled(true);



        configSwipeDeck(swipeDeck, mAdapter);

        int rightHint=getRightHint();
        if(rightHint!=-1)
            swipeDeck.setRightImage(rightHint);

        int leftHint=getLeftHint();
        if(leftHint!=-1)
            swipeDeck.setLeftImage(leftHint);


        if(mRootView.findViewById(R.id.progressBar)!=null)
            mProgressBar= (ProgressView)mRootView.findViewById(R.id.progressBar);

        swipeDeck.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                dataSwiped.add(getDataManager().get(position));
                leftCardExit(getDataManager().get(position));
                if(getDataManager().size()==dataSwiped.size() && getDataManager().canLoadNext())
                    emptyAndLoadNext();

            }

            @Override
            public void cardSwipedRight(int position) {
                dataSwiped.add(getDataManager().get(position));
                rightCardExit(getDataManager().get(position));

                if(getDataManager().size()== dataSwiped.size() && getDataManager().canLoadNext())
                    emptyAndLoadNext();
            }

            @Override
            public void cardsDepleted() {

            }
        });




        loadNext();
        onViewCreated(mRootView);
        return mRootView;
    }

    private void emptyAndLoadNext() {
        getDataManager().removeAll(getDataManager());
        dataSwiped = new ArrayList<>();
        loadNext();
    }

    protected int getLeftHint()
    {
        return -1;
    }

    protected int getRightHint() {
        return -1;
    }

    public void leftCardExit(Object dataObject) {

    }


    public void rightCardExit(Object dataObject) {

    }
    public void selectRight() {
        swipeDeck.swipeTopCardRight(2000);
    }
    public void selectLeft() {
        swipeDeck.swipeTopCardLeft(2000);
    }
    protected void configSwipeDeck(SwipeDeck mList,BaseListViewAdapter mAdapter)
    {

    }

    public void onCallEnd(List<?> dataList,final boolean isRefreshData)
    {
        super.onCallEnd(dataList, isRefreshData);

        swipeDeck.setAdapter(mAdapter);

        if(mProgressBar!=null)
            mProgressBar.setVisibility(View.GONE);

    }

    public void onCallStart()
    {
        super.onCallStart();
        if(mProgressBar!=null)
            mProgressBar.setVisibility(View.VISIBLE);
    }
}
