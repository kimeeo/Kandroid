package com.kimeeo.library.listDataView.viewPager;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.viewPager.viewPager.BaseViewPagerAdapter;
import com.nshmura.recyclertablayout.RecyclerTabLayout;

/**
 * Created by bhavinpadhiyar on 1/21/16.
 */
abstract public class TabIndicatorRecyclerViewAdapter extends RecyclerTabLayout.Adapter<TabIndicatorRecyclerViewAdapter.ViewHolder> {

    private final DataManager dataManager;
    private OnItemClick onItemClick;
    public TabIndicatorRecyclerViewAdapter(ViewPager viewPager, DataManager dataManager) {
        super(viewPager);
        this.dataManager = dataManager;
    }

    protected abstract ViewHolder getViewHolder(View view, int viewType);

    protected abstract View getView(ViewGroup parent, int viewType);

    public DataManager getDataManager()
    {
        return dataManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getView(parent, viewType);
        return getViewHolder(view, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object item =dataManager.get(position);
        if(item instanceof BaseViewPagerAdapter.ProgressItem) {
            holder.configProgressItem();
            holder.updateProgressView(item);
        }
        else
        {
            holder.configNormalItem();

            holder.udateItem(position, item);
            holder.configItem(position, item);

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

    protected void gotoItem(Object item, int position, boolean isSmooth) {
        if (getViewPager() != null)
            getViewPager().setCurrentItem(position, isSmooth);

        if (onItemClick != null)
            onItemClick.onItemClick(item, position, isSmooth);
    }

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public int getItemCount() {
        return dataManager.size();
    }


    protected boolean isClickSupport() {
        return true;
    }


    public interface OnItemClick {
        void onItemClick(Object item, int position, boolean isSmooth);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected Object item;
        protected int position;
        private View.OnClickListener onItemClick;

        public ViewHolder(View itemView) {
            super(itemView);
            if (isClickSupport()) {
                onItemClick = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoItem(item, position, false);
                    }
                };
                itemView.setOnClickListener(onItemClick);
            }
        }

        public void configItem(int position, Object item) {
            this.item = item;
            this.position = position;
        }

        public void udateItem(int position, Object item) {

        }

        public void updatedSelectedItem(Object o) {

        }
        public void updatedNormalItem(Object o) {
        }

        public void configProgressItem() {
            itemView.setVisibility(View.GONE);
        }

        public void configNormalItem() {
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
