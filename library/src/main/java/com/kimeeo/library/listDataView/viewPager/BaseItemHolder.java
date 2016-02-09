package com.kimeeo.library.listDataView.viewPager;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
abstract public class BaseItemHolder {
    private View itemView;
    protected int position;

    public View getItemView()
    {
        return itemView;
    }
    public void updateItemView(Object item, int position)
    {
        this.position = position;
        updateItemView(item,itemView,position);
    }

    abstract public void cleanView(View itemView,int position);
    abstract public void updateItemView(Object item,View itemView, int position);

    public BaseItemHolder(View itemView)
    {
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }
}
