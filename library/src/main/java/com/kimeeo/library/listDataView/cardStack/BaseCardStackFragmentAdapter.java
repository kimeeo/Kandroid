package com.kimeeo.library.listDataView.cardStack;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.library.fragments.BaseFragment;
import com.kimeeo.library.model.IFragmentData;

/**
 * Created by bhavinpadhiyar on 2/4/16.
 */
abstract public class BaseCardStackFragmentAdapter extends BaseCardStackAdapter
{
    FragmentManager fragmentManager;
    public BaseCardStackFragmentAdapter(Activity activity,FragmentManager fragmentManager) {
        super(activity);
        this.fragmentManager =fragmentManager;
    }
    public View createView(final int position, ViewGroup container)
    {
        final View root =super.createView(position, container);
        updateView(root,position,getList().get(position));
        loadItemFragment(fragmentManager, root, position, getList().get(position));
        return root;
    }
    protected void loadItemFragment(FragmentManager fragmentManager,View root,int position,Object data)
    {
        if(data instanceof IFragmentData)
        {
            BaseFragment activePage = BaseFragment.newInstance((IFragmentData)data);
            if(activePage!=null)
            {
                fragmentManager.beginTransaction().replace(root.getId(), activePage).commit();
            }
        }
    }
}
