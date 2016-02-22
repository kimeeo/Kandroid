package com.kimeeo.library.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.kimeeo.library.model.BaseApplication;
import com.kimeeo.library.model.IApplicationAware;
import com.kimeeo.library.model.IFragmentData;
import com.kimeeo.library.webview.DefaultWebView;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 4/10/15.
 */
abstract public class BaseFragment extends Fragment implements IApplicationAware{
    abstract public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState);
    abstract protected void garbageCollectorCall();

    private static final String DATA= "data";
    private static final String LOG_TAG= "BaseFragment";

    protected IFragmentData fragmentData;

    public void onDestroyItem()
    {

    }
    public BaseApplication getApplication()
    {
        if(getActivity().getApplication() instanceof BaseApplication)
            return (BaseApplication)getActivity().getApplication();
        return null;
    }


    public boolean allowedBack()
    {
        return true;
    }

    public void onStart()
    {
        super.onStart();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(getHasOptionsMenu());

        if(getActivity()!=null && getActivity() instanceof IFragmentWatcher)
            ((IFragmentWatcher)getActivity()).onCreate(this);
        if(getApplication()!=null && getApplication() instanceof BaseApplication)
            getApplication().onCreate(this);
    }
    protected boolean getHasOptionsMenu()
    {
        return false;
    }
    public void onDestroy()
    {
        super.onDestroy();
        garbageCollectorCall();
        if(getActivity()!=null && getActivity() instanceof IFragmentWatcher)
            ((IFragmentWatcher)getActivity()).onDestroy(this);
        if(getApplication()!=null && getApplication() instanceof BaseApplication)
            getApplication().onDestroy(this);

    }
    public void onDestroyView() {
        super.onDestroyView();
        garbageCollectorCall();
        fragmentData =null;

    }


    public void configFragmentData(IFragmentData data)
    {
        if(data.getParam()!=null && data.getParam() instanceof Map)
        {
            Map<String,String> map = (Map<String,String>)data.getParam();
            Bundle args = new Bundle();

            for (Map.Entry<String,String> entry:map.entrySet()) {
                args.putString(entry.getKey(), entry.getValue());
            }
            setArguments(args);
        }
        else if(data.getParam()!=null && data.getParam() instanceof String)
        {
            Bundle args = new Bundle();
            args.putString(DATA, (String)data.getParam());
            setArguments(args);
        }
        else if(data.getParam()!=null)
        {
            Bundle args = new Bundle();
            args.putString(DATA,new Gson().toJson(data.getParam()));
            setArguments(args);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    public void setFragmentData(IFragmentData object)
    {
        this.fragmentData = object;
        configFragmentData(fragmentData);
    }
    public IFragmentData getFragmentData()
    {
        return fragmentData;
    }


    public void setPramas(Object object)
    {
        if(fragmentData!=null)
            fragmentData.setParam(object);
    }
    public Object getPramas()
    {
        if(fragmentData!=null)
            return fragmentData.getParam();
        return null;
    }











    public static BaseFragment newInstance(IFragmentData object)
    {
        try {
            if(object.getView()!=null)
            {
                Constructor constructor  = object.getView().getConstructor();
                BaseFragment baseFragment = (BaseFragment)constructor.newInstance();
                if(baseFragment!=null) {
                    baseFragment.setFragmentData(object);
                    return baseFragment;
                }
            }

        } catch (Exception e)
        {
            System.out.println(e);
        }
        Log.e(LOG_TAG, "BaseFragment creation fail. ID:"+object.getID()+"  Name:"+object.getName());
        return null;
    }


    public static BaseFragment newWebViewInstance(IFragmentData object)
    {
        BaseFragment fragment = null;
        Constructor constructor=null;
        try {
            if(object.getView()!=null)
            {
                constructor  = object.getView().getConstructor();
                BaseFragment baseFragment = (BaseFragment)constructor.newInstance();

                if(baseFragment!=null) {
                    fragment = baseFragment;
                    fragment.setFragmentData(object);
                }
            }
            else
            {
                BaseFragment baseFragment = new DefaultWebView();
                if(baseFragment!=null) {
                    fragment = baseFragment;
                    fragment.setFragmentData(object);
                }
            }

        } catch (Exception e)
        {
            System.out.println(e);
        }

        if(fragment==null)
            Log.e(LOG_TAG, "BaseFragment creation fail. ID:"+object.getID()+"  Name:"+object.getName());

        return fragment;
    }
}
