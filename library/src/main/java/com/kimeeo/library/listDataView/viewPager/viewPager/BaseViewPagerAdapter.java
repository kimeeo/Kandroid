package com.kimeeo.library.listDataView.viewPager.viewPager;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.OnCallService;
import com.kimeeo.library.listDataView.viewPager.BaseItemHolder;
import com.rey.material.widget.ProgressView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/20/16.
 */
abstract public class BaseViewPagerAdapter extends PagerAdapter implements OnCallService{

    public void add(Object value) {
        insert(value, getDataManager().size());
    }
    public void garbageCollectorCall() {
        dataManager=null;
        onCallService=null;
    }

    public void insert(Object value, int position) {
        getDataManager().add(position, value);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        getDataManager().remove(position);
        notifyDataSetChanged();
    }

    public void clear() {
        getDataManager().clear();
        notifyDataSetChanged();
    }

    public void addAll(Object[] values) {
        int startIndex = getDataManager().size();
        getDataManager().addAll(startIndex, Arrays.asList(values));
        notifyDataSetChanged();
    }

    protected BaseItemHolder getProgressViewHolder(View view) {
        return  new ProgressViewHolder(view);
    }

    abstract protected View getView(int position,Object data);
    abstract protected BaseItemHolder getItemHolder(View view,int position,Object data);
    abstract protected void removeView(View view,int position,BaseItemHolder itemHolder);

    private DataManager dataManager;
    public boolean supportLoader = true;
    public BaseViewPagerAdapter(DataManager dataManager)
    {
        this.dataManager = dataManager;
        this.dataManager.setOnCallService(this);
    }

    public int getCount()
    {
        return dataManager.size();
    }
    public Object instantiateItem(ViewGroup container, int position)
    {
        Object data = dataManager.get(position);

        View view = getItemView(position, data,container);
        BaseItemHolder itemHolder= getItemHolderAll(view, position, data);
        view.setTag(itemHolder);
        container.addView(view, 0);
        itemHolder.updateItemView(data, position);
        return view;
    }

    protected BaseItemHolder getItemHolderAll(View view,int position,Object data)
    {
        if(data instanceof ProgressItem && supportLoader)
            return getProgressViewHolder(view);
        return getItemHolder(view,position, data);
    }


    protected View getItemView(int position,Object data,ViewGroup container)
    {
        if(data instanceof ProgressItem && supportLoader)
            return getProgressBar(container);
        return getView(position, data);
    }

    public View getProgressBar(ViewGroup container) {
        LayoutInflater li = LayoutInflater.from(container.getContext());
        View view = li.inflate(R.layout._progress_item, null, false);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object)
    {
        try
        {
            View view = (View) object;
            BaseItemHolder itemHolder = (BaseItemHolder)view.getTag();
            itemHolder.cleanView(view, position);
            removeView((View) object, position,itemHolder);
            container.removeView((View) object);
        }catch(Exception e)
        {

        }

    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public void removeOnCallService()
    {
        onCallService=null;
    }

    private OnCallService onCallService;
    protected DataManager getDataManager()
    {
        return dataManager;
    }

    public OnCallService getOnCallService()
    {
        return onCallService;
    }
    public void setOnCallService(OnCallService onCallService)
    {
        this.onCallService =onCallService;
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

    abstract public String getItemTitle(int position,Object navigationObject);
    @Override
    public CharSequence getPageTitle(int position) {
        Object data = getDataManager().get(position);
        return getItemTitle(position, data);
    }

    @Override
    public int getItemPosition(Object object) {
        View view = (View) object;
        BaseItemHolder itemHolder = (BaseItemHolder)view.getTag();
        if(itemHolder instanceof ProgressViewHolder)
            return POSITION_NONE;
        return super.getItemPosition(object);
    }


    public static class ProgressItem{}




    // Update View Here
    public class ProgressViewHolder extends BaseItemHolder {

        ProgressView progressBar;

        public ProgressViewHolder(View itemView)
        {
            super(itemView);
            progressBar = (ProgressView)itemView.findViewById(R.id.progressBar);
        }
        public void cleanView(View itemView,int position)
        {

        }

        public void updateItemView(Object item,View view,int position)
        {
            progressBar.setProgress(0f);
            progressBar.start();
        }
    }
}
