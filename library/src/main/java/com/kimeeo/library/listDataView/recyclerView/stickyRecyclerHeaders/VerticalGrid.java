package com.kimeeo.library.listDataView.recyclerView.stickyRecyclerHeaders;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.kimeeo.library.listDataView.recyclerView.GridHelper;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
abstract public class VerticalGrid extends DefaultStickyHeaderView implements GridHelper.IColoumProvider {
    protected RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), getColumnsCount(), GridLayoutManager.VERTICAL, false);
        return gridLayoutManager;
    }


    public int getColumnsCount()
    {
        if(gridHelper!=null)
            return gridHelper.getColumnsCount();
        return getColumnsPhone();
    }

    public int getColumnsPhone() {
        return 2;
    }
    public int getColumnsTablet10() {
        return 5;
    }
    public int getColumnsTablet7() {return 4;}

    public int getSpanSizeForItem(int position,int viewType,Object baseObject)
    {
        return 1;
    }
    protected void garbageCollectorCall() {
        super.garbageCollectorCall();
        if(gridHelper!=null)
            gridHelper.garbageCollectorCall();
        gridHelper=null;
    }
    private GridHelper gridHelper;

    @Override
    protected void configViewParam()
    {
        super.configViewParam();
        gridHelper = new GridHelper(this,getApplication());
    }
    protected void configLayoutManager(RecyclerView.LayoutManager layoutManager)
    {
        gridHelper.configLayoutManager(layoutManager);
    }
}
