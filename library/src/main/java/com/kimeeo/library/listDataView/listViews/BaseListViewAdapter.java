package com.kimeeo.library.listDataView.listViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.dataManagers.DataChangeWatcher;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.OnCallService;
import com.rey.material.widget.ProgressView;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 1/20/16.
 */
abstract public class BaseListViewAdapter extends BaseAdapter implements OnCallService,DataChangeWatcher
{

    public static class ViewTypes {
        public static final int VIEW_PROGRESS = 0;
        public static final int VIEW_ITEM = 1;
        public static final int VIEW_HEADER = -1;
    }
    private static final String TAG = "BaseRecyclerViewAdapter";
    private AdapterView.OnItemClickListener mOnItemClickListener;
    private DataManager dataManager;

    public void garbageCollectorCall() {
        dataManager=null;
        onCallService=null;
    }

    protected DataManager getDataManager()
    {
        return dataManager;
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
    @Override
    public int getCount() {
        if(getDataManager()!=null)
            return getDataManager().size();
        return 0;
    }


    public BaseListViewAdapter(DataManager dataManager){
        this.dataManager= dataManager;
        this.dataManager.setOnCallService(this);
        this.dataManager.setDataChangeWatcher(this);
    }
    public void itemsAdded(int position,List items)
    {
        notifyDataSetChanged();
    }
    public void itemsRemoved(int position,List items)
    {
        notifyDataSetChanged();
    }
    public boolean supportLoader = true;

    public BaseItemHolder onCreateViewHolder(ViewGroup container, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        BaseItemHolder itemHolder;
        View root;
        if(viewType==ViewTypes.VIEW_PROGRESS && supportLoader)
        {
            root = getProgressItem(viewType,inflater, container);
            itemHolder = new ProgressViewHolder(root);
        }
        else {

            root= getItemView(viewType,inflater,container);
            itemHolder= getItemHolder(viewType,root);
        }
        return itemHolder;

    }


    protected View getProgressItem(int viewType,LayoutInflater inflater,ViewGroup container)
    {
        return inflater.inflate(R.layout._progress_item, container, false);
    }

    public int getItemCount() {

        return getDataManager().size();

    }
    @Override
    public Object getItem(int position) {
        return getDataManager().get(position);
    }












    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getViewTypeCount()
    {
        return getTotalViewTypeCount()+1;
    }
    abstract protected int getTotalViewTypeCount();



    private Map<Integer,Integer> viewTypes=new HashMap<>();
    private int counter=1;

    
    protected int itemViewType(int viewType)
    {
        /*
        Integer value=viewTypes.get(viewType);
        if(value==null) {
            viewTypes.put(viewType, counter);
            value=viewTypes.get(viewType);
            counter++;
        }
        */
        return viewType;
    }
    protected int getListItemViewType(int position, Object item)
    {
        return ViewTypes.VIEW_ITEM;
    }



    public void onBindViewHolder(BaseItemHolder itemHolder, int position) {
        Object item = getDataManager().get(position);
        itemHolder.updateItemView(item, position);
    }


    @Override
    public int getItemViewType(int position)
    {
        if(getDataManager().get(position) instanceof ProgressItem)
            return ViewTypes.VIEW_PROGRESS;
        else
            return itemViewType(getListItemViewType(position,getDataManager().get(position)));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseItemHolder holder = null;
        if (convertView == null) {
            int viewType = getItemViewType(position);
            holder = onCreateViewHolder(parent,viewType);
            convertView  = holder.getItemView();
            convertView.setTag(holder);
        } else {
            holder = (BaseItemHolder)convertView.getTag();
        }

        holder.position = position;
        onBindViewHolder(holder,position);

        return convertView;
    }
    public void onCallStart()
    {
        if(supportLoader) {
            try
            {
                getDataManager().add(new ProgressItem());
                notifyDataSetChanged();
            }catch (Exception e){

                //Log.e(TAG,"onCallStart: Progress bar adding fails");
            }

        }

        if(onCallService!=null)
            onCallService.onCallStart();
    }

    public static class ProgressItem
    {

    }

    public void onDataReceived(String url,Object value,Object status)
    {
        List<Object> list = getDataManager();
        if(list.size()!=0 && list.get(list.size() - 1) instanceof ProgressItem && supportLoader)
        {
            getDataManager().remove(getDataManager().size() - 1);
            notifyDataSetChanged();
        }
        if(onCallService!=null)
            onCallService.onDataReceived(url, value, status);
    }
    final public void onCallEnd(List<?> dataList,boolean isRefreshPage)
    {
        if(dataList!=null && dataList.size()!=0) {
            notifyDataSetChanged();
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

    // Update View Here
    public static class ProgressViewHolder extends BaseItemHolder {

        ProgressView progressBar;

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

