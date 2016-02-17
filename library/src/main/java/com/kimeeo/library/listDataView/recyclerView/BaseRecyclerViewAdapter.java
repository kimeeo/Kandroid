package com.kimeeo.library.listDataView.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.dataManagers.DataChangeWatcher;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.OnCallService;
import com.lsjwzh.widget.recyclerviewpager.TabLayoutSupport;
import com.rey.material.widget.ProgressView;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by bhavinpadhiyar on 7/21/15.
 */
abstract public class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseItemHolder> implements OnCallService, TabLayoutSupport.ViewPagerTabLayoutAdapter,BaseItemHolder.OnItemHolderClick,DataChangeWatcher {

    public static class ViewTypes {
        public static final int VIEW_PROGRESS = 0;
        public static final int VIEW_ITEM = 1;
        public static final int VIEW_HEADER = -1;
    }
    private static final String TAG = "BaseRecyclerViewAdapter";
    private AdapterView.OnItemClickListener mOnItemClickListener;
    private DataManager dataManager;

    public void garbageCollectorCall()
    {
        dataManager=null;
        mOnItemClickListener=null;
        onCallService=null;
    }
    public void add(Object value) {

        add(getDataManager().size(), value);
    }
    public void add(int position,Object value) {
        getDataManager().add(position, value);
        if(position==0)
            notifyDataSetChanged();
        else
            notifyItemInserted(position);
    }
    public void remove(int position) {
        getDataManager().remove(position);
        if(position==0)
            notifyDataSetChanged();
        else
            notifyItemRemoved(position);

    }
    public boolean removeAll(Collection value) {
        boolean value1 =getDataManager().removeAll(value);
        if(value1)
            notifyItemRangeRemoved(0, value.size());
        return value1;
    }

    public void clear() {
        int size = getDataManager().size();
        getDataManager().clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(Object[] values) {
        int startIndex = getDataManager().size();
        getDataManager().addAll(startIndex, Arrays.asList(values));
        notifyItemRangeInserted(startIndex, values.length);
    }


    protected DataManager getDataManager()
    {
        return dataManager;
    }


    public String getPageTitle(int var1)
    {
        return var1+"";
    }



    abstract protected View getItemView(int viewType,LayoutInflater inflater,ViewGroup container);
    abstract protected BaseItemHolder getItemHolder(int viewType,View view);

    private OnCallService onCallService;



    public OnCallService getOnCallService()
    {
        return onCallService;
    }
    public void setOnCallService(OnCallService onCallService)
    {
        this.onCallService =onCallService;
    }
    public void removeOnCallService()
    {
        onCallService=null;
    }


    public BaseRecyclerViewAdapter(DataManager dataManager, OnCallService onCallService) {
        this.dataManager= dataManager;
        this.dataManager.setOnCallService(this);
        this.dataManager.setDataChangeWatcher(this);
        setOnCallService(onCallService);
    }

    public void itemsAdded(int position,List items)
    {
        if(items!=null && items.size()!=0) {
            if (position == 0)
                notifyDataSetChanged();
            else
                notifyItemRangeInserted(position, items.size());
        }
    }
    public void itemsRemoved(int position,List items)
    {
        if(items!=null && items.size()!=0) {
            if (position == 0)
                notifyDataSetChanged();
            else
                notifyItemRangeRemoved(position, items.size());
        }
    }


    public void onItemHolderClick(BaseItemHolder itemHolder,int position)
    {
        if (mOnItemClickListener != null)
            mOnItemClickListener.onItemClick(null, itemHolder.itemView,position, itemHolder.getItemId());
    }
    public boolean supportLoader = true;
    @Override
    public BaseItemHolder onCreateViewHolder(ViewGroup container, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        BaseItemHolder itemHolder;
        View root;
        if(viewType== ViewTypes.VIEW_PROGRESS && supportLoader)
        {
            root = getProgressItem(viewType,inflater, container);
            itemHolder = new ProgressViewHolder(root);
            itemHolder.setOnItemHolderClick(this);
        }
        else {
            root= getItemView(viewType,inflater,container);
            itemHolder= getItemHolder(viewType, root);
            itemHolder.setOnItemHolderClick(this);
        }
        return itemHolder;

    }
    protected View getProgressItem(int viewType,LayoutInflater inflater,ViewGroup container)
    {
        return inflater.inflate(R.layout._progress_item, container, false);
    }

    @Override
    public int getItemCount() {

        return getDataManager().size();

    }

    @Override
    public void onBindViewHolder(BaseItemHolder itemHolder, int position) {
        Object item = getDataManager().get(position);
        if(getItemViewType(position)== ViewTypes.VIEW_PROGRESS && itemHolder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams && supportLoader)
        {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) itemHolder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }
        else if(getItemViewType(position)== ViewTypes.VIEW_HEADER && itemHolder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams)
        {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) itemHolder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }
        else if (itemHolder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams)
        {
            boolean isSnap = getSpanForItem(itemHolder,position,getItemViewType(position));
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) itemHolder.itemView.getLayoutParams();
            layoutParams.setFullSpan(isSnap);
        }

        itemHolder.updateItemView(item, position);
    }

    protected boolean getSpanForItem(BaseItemHolder itemHolder, int position,int viewType)
    {
        return false;
    }



    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    @Override
    public int getItemViewType(int position)
    {
        return getDataManager().get(position) instanceof ProgressItem? ViewTypes.VIEW_PROGRESS:getListItemViewType(position,getDataManager().get(position));
    }

    protected int getListItemViewType(int position, Object item)
    {
        return ViewTypes.VIEW_ITEM;
    }
    public static class ProgressItem
    {

    }

    public void onCallStart()
    {
        if(supportLoader) {
            try
            {
                getDataManager().add(new ProgressItem());
                notifyItemInserted(getDataManager().size());
            }catch (Exception e){

            }

        }

        if(onCallService!=null)
            onCallService.onCallStart();
    }



    public void onDataReceived(String url,Object value,Object status)
    {
        List<Object> list = getDataManager();
        if(list.size()!=0 && list.get(list.size() - 1) instanceof ProgressItem && supportLoader)
        {
            getDataManager().remove(getDataManager().size() - 1);
            notifyItemRemoved(getDataManager().size());
        }
        if(onCallService!=null)
            onCallService.onDataReceived(url, value, status);
    }
    final public void onCallEnd(List<?> dataList,boolean isRefreshPage)
    {
        if(dataList!=null && dataList.size()!=0) {
            if(isRefreshPage) {
                notifyItemRangeInserted(getDataManager().getRefreshItemPos(), dataList.size());
            }
            else
                notifyItemRangeInserted(getDataManager().size() - dataList.size(), dataList.size());
        }
        onDataCallIn(dataList,isRefreshPage);

        if(onCallService!=null)
            onCallService.onCallEnd(dataList, isRefreshPage);
    }
    public void onDataCallIn(List<?> dataList,boolean isRefreshPage)
    {

    }
    public void onFirstCallEnd()
    {
        if(onCallService!=null)
            onCallService.onFirstCallEnd();
    }
    public void onLastCallEnd()
    {
        if(onCallService!=null)
            onCallService.onLastCallEnd();
    }
    public void onDataLoadError(String url, Object status)
    {
        if(onCallService!=null)
            onCallService.onDataLoadError(url, status);
    }



    public class  ProgressViewHolder extends BaseItemHolder {

        private ProgressView progressBar;

        public ProgressViewHolder(View itemView)
        {
            super(itemView);
            progressBar = (ProgressView)itemView.findViewById(R.id.progressBar);
        }

        public void updateItemView(Object item,View view,int position)
        {
            progressBar.setProgress(0f);
            progressBar.start();
        }
    }
}
