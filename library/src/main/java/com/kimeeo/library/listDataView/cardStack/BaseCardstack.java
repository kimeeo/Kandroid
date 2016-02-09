package com.kimeeo.library.listDataView.cardStack;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.library.R;
import com.kimeeo.library.fragments.BaseFragment;
import com.mutualmobile.cardstack.CardStackLayout;

/**
 * Created by bhavinpadhiyar on 2/4/16.
 */
abstract public class BaseCardstack extends BaseFragment {
    abstract protected BaseCardStackAdapter createListViewAdapter();
    protected CardStackLayout mList;
    public View getRootView() {
        return mRootView;
    }
    protected View mRootView;
    protected BaseCardStackAdapter mAdapter;


    protected void garbageCollectorCall()
    {
        mList = null;
        if(mAdapter!=null)
            mAdapter.garbageCollectorCall();
        mAdapter =null;
    }
    protected CardStackLayout getListView()
    {
        return mList;
    }
    public BaseCardStackAdapter getAdapter()
    {
        return mAdapter;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mRootView = createRootView(inflater, container, savedInstanceState);
        mList = createListView(mRootView);
        onViewCreated(mRootView);
        final Handler handler = new Handler();
        final Runnable runnablelocal = new Runnable() {
            @Override
            public void run() {
                mAdapter = createListViewAdapter();
                mList.setAdapter(mAdapter);
                configListView(mList, mAdapter);
            }
        };
        handler.postDelayed(runnablelocal,200);
        return mRootView;
    }

    public void onViewCreated(View view) {

    }
    protected void configListView(CardStackLayout mList,BaseCardStackAdapter mAdapter)
    {

    }
    protected View createRootView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout._fragment_stack_layout, container, false);
        return rootView;
    }
    protected CardStackLayout createListView(View rootView)
    {
        CardStackLayout recyclerView = (CardStackLayout) rootView.findViewById(R.id.listView);
        return recyclerView;
    }
}
