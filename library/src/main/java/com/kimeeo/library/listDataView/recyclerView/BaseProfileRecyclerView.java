/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kimeeo.library.listDataView.recyclerView;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.kimeeo.library.listDataView.recyclerView.viewProfiles.BaseViewProfile;


abstract public class BaseProfileRecyclerView extends BaseRecyclerView implements AdapterView.OnItemClickListener
{
    final protected RecyclerView.LayoutManager createLayoutManager()
    {
        return null;
    }
    final protected BaseRecyclerViewAdapter createListViewAdapter()
    {
        return null;
    }
    protected RecyclerView.ItemDecoration curruntItemDecoration;
    protected void garbageCollectorCall() {
        super.garbageCollectorCall();
        curruntItemDecoration=null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = createRootView(inflater, container, savedInstanceState);
        if(getDataManager().getRefreshEnabled())
            configSwipeRefreshLayout(createSwipeRefreshLayout(rootView));
        recyclerView = createRecyclerView(rootView);
        mEmptyView= createEmptyView(rootView);
        onViewCreated(rootView);
        garbageCollectorCall();
        return rootView;
    }

    public void applyProfile(BaseViewProfile profile) {
        RecyclerView.LayoutManager layoutManager= profile.createLayoutManager();
        profile.configLayoutManager(layoutManager);
        recyclerView.setLayoutManager(layoutManager);
        if(curruntItemDecoration!=null)
            recyclerView.removeItemDecoration(curruntItemDecoration);
        curruntItemDecoration=profile.createItemDecoration(getActivity());
        recyclerView.addItemDecoration(curruntItemDecoration);

        mAdapter = profile.createListViewAdapter();
        profile.configViewAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
        setOnScrollListener(recyclerView);


        RecyclerView.ItemAnimator itemAnimator = profile.createItemAnimator();
        int itemAnimatorDuration = profile.getItemAnimatorDuration();
        if(itemAnimator==null) {
            itemAnimator = createItemAnimator();
            itemAnimatorDuration=getItemAnimatorDuration();
        }

        if(itemAnimator!=null) {
            itemAnimator.setAddDuration(itemAnimatorDuration);
            itemAnimator.setChangeDuration(itemAnimatorDuration);
            itemAnimator.setMoveDuration(itemAnimatorDuration);
            itemAnimator.setRemoveDuration(itemAnimatorDuration);
            recyclerView.setItemAnimator(itemAnimator);
        }
        else
            recyclerView.setItemAnimator(null);

        profile.configRecyclerView(recyclerView, mAdapter);
    }
}
