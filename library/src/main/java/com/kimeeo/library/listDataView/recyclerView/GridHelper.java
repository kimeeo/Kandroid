package com.kimeeo.library.listDataView.recyclerView;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.kimeeo.library.model.BaseApplication;

/**
 * Created by bhavinpadhiyar on 2/1/16.
 */
public class GridHelper {

    private IColoumProvider coloumProvider;

    public void garbageCollectorCall() {
        coloumProvider=null;
    }

    public int getColumnsCount() {
        return columnCount;
    }

    private int columnCount;

    public GridHelper(IColoumProvider coloumProvider,BaseApplication application)
    {
        this.coloumProvider =coloumProvider;
        int count = coloumProvider.getColumnsPhone();
        if(application!=null)
        {
            if(application.isTablet() && application.isIs7inchTablet())
                count = coloumProvider.getColumnsTablet7();
            else if(application.isTablet())
                count = coloumProvider.getColumnsTablet10();
            else
                count = coloumProvider.getColumnsPhone();
        }
        columnCount =count;

    }


    public void configLayoutManager(RecyclerView.LayoutManager layoutManager)
    {
        if(layoutManager instanceof GridLayoutManager)
        {
            GridLayoutManager gridLayoutManager = (GridLayoutManager)layoutManager;
            gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
        }
        else if(layoutManager instanceof StaggeredGridLayoutManager)
        {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager)layoutManager;
            staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        }
    }

    GridLayoutManager.SpanSizeLookup spanSizeLookup= new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            int viewType = coloumProvider.getAdapter().getItemViewType(position);
            switch(viewType){
                case BaseRecyclerViewAdapter.ViewTypes.VIEW_PROGRESS:
                    return getColumnsCount();
                case BaseRecyclerViewAdapter.ViewTypes.VIEW_HEADER:
                    return getColumnsCount();
                default:
                    return getSpanSizeForItem(position, viewType,coloumProvider.getAdapter().getDataManager().get(position));
            }
        }
    };
    protected int getSpanSizeForItem(int position,int viewType,Object baseObject)
    {
        if(baseObject instanceof ISpanSizeAware)
        {
            ISpanSizeAware spanSizeAware = (ISpanSizeAware)baseObject;
            int size = spanSizeAware.getSpanSize();
            if(size == ISpanSizeAware.FULL)
                return  getColumnsCount();
            else if(size == ISpanSizeAware.HALF)
                return getColumnsCount()/2;
            else if(size == ISpanSizeAware.QUARTER)
                return getColumnsCount()/4;
            else
                return coloumProvider.getSpanSizeForItem(position,viewType,baseObject);
        }
        return coloumProvider.getSpanSizeForItem(position,viewType,baseObject);
    }


    public  static interface IColoumProvider
    {
        int getColumnsCount();
        int getColumnsPhone();
        int getColumnsTablet10();
        int getColumnsTablet7();
        int getSpanSizeForItem(int position,int viewType,Object baseObject);
        BaseRecyclerViewAdapter getAdapter();
    }
}
