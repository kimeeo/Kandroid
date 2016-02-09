package com.kimeeo.library.listDataView.viewPager;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.viewPager.viewPager.BaseViewPagerAdapter;
import com.nshmura.recyclertablayout.RecyclerTabLayout;

import butterknife.ButterKnife;

/**
 * Created by bhavinpadhiyar on 1/21/16.
 */
abstract public class TabIndicatorRecyclerViewAdapter extends RecyclerTabLayout.Adapter<TabIndicatorRecyclerViewAdapter.ViewHolder> {

    private final DataManager dataManager;

    public DataManager getDataManager()
    {
        return dataManager;
    }
    public TabIndicatorRecyclerViewAdapter(ViewPager viewPager, DataManager dataManager) {
        super(viewPager);
        this.dataManager=dataManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getView(parent);
        return getViewHolder(view);
    }

    protected abstract ViewHolder getViewHolder(View view);

    protected abstract View getView(ViewGroup parent);


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Object item =dataManager.get(position);


        if(item instanceof BaseViewPagerAdapter.ProgressItem) {
            holder.hide();
            holder.updateProgressView(item);
        }
        else
        {
            holder.show();
            holder.updatedItem(item);
            if (position == getCurrentIndicatorPosition())
                holder.updatedSelectedItem(item);
            else
                holder.updatedNormalItem(item);
        }

        if(dataManager.size()!=0)
        {
            if(dataManager.size()==1)
                holder.updateMiddel(item);
            else {
                if (position == 0)
                    holder.updateFirst(item);
                else if (position == dataManager.size() - 1)
                    holder.updateLast(item);
                else
                    holder.updateMiddel(item);
            }
        }

    }

    public int getItemCount() {
        return dataManager.size();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void updatedItem(Object o) {

        }
        public void updatedSelectedItem(Object o) {

        }
        public void updatedNormalItem(Object o) {
        }

        public void hide() {
            itemView.setVisibility(View.GONE);
        }

        public void show() {
            itemView.setVisibility(View.VISIBLE);
        }

        public void updateProgressView(Object item) {

        }

        public void updateFirst(Object item) {

        }

        public void updateLast(Object item) {

        }

        public void updateMiddel(Object item) {

        }
    }
}
