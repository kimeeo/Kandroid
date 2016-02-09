package com.kimeeo.library.listDataView.cardStack;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.kimeeo.library.R;
import com.mutualmobile.cardstack.CardStackAdapter;
import com.rey.material.widget.ProgressView;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bhavinpadhiyar on 2/4/16.
 */
abstract public class BaseCardStackAdapter extends CardStackAdapter implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "BaseRecyclerViewAdapter";
    private LayoutInflater mInflater;

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
    @Override
    public int getCount() {
        if(getList()==null)
            return 0;
        return getList().size();
    }

    private List list;
    protected List getList()
    {
        if(list==null)
            list=createList();
        return list;
    }

    protected abstract List createList();


    public BaseCardStackAdapter(Activity activity) {
        super(activity);
        mInflater = LayoutInflater.from(activity);
    }

    public View createView(int position, ViewGroup container) {
        View root =mInflater.inflate(getViewResID(position,getList().get(position)),null);
        root.setId(getId());
        updateView(root,position,getList().get(position));
        return root;
    }

    public int getId()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
            return generateViewId();
        else
            return View.generateViewId();
    }
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    protected abstract void updateView(View root,int index,Object data);
    protected abstract int getViewResID(int index,Object data);

    public void garbageCollectorCall()
    {
        mInflater=null;
    }
}
