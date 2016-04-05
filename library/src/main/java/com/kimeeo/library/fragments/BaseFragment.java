package com.kimeeo.library.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.kimeeo.library.R;
import com.kimeeo.library.actions.PermissionsHelper;
import com.kimeeo.library.model.BaseApplication;
import com.kimeeo.library.model.IApplicationAware;
import com.kimeeo.library.model.IFragmentData;
import com.kimeeo.library.webview.DefaultWebView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 4/10/15.
 */
abstract public class BaseFragment extends Fragment implements IApplicationAware{
    private static final String DATA= "data";
    private static final String LOG_TAG= "BaseFragment";
    protected IFragmentData fragmentData;
    PermissionListener onPermission = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            permissionGranted();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> arrayList) {
            permissionDenied(arrayList);
        }
    };

    public static BaseFragment newInstance(IFragmentData object) {
        try {
            if (object.getView() != null) {
                Constructor constructor = object.getView().getConstructor();
                BaseFragment baseFragment = (BaseFragment) constructor.newInstance();
                if (baseFragment != null) {
                    baseFragment.setFragmentData(object);
                    return baseFragment;
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        Log.e(LOG_TAG, "BaseFragment creation fail. ID:" + object.getID() + "  Name:" + object.getName());
        return null;
    }

    public static BaseFragment newWebViewInstance(IFragmentData object) {
        BaseFragment fragment = null;
        Constructor constructor = null;
        try {
            if (object.getView() != null) {
                constructor = object.getView().getConstructor();
                BaseFragment baseFragment = (BaseFragment) constructor.newInstance();

                if (baseFragment != null) {
                    fragment = baseFragment;
                    fragment.setFragmentData(object);
                }
            } else {
                BaseFragment baseFragment = new DefaultWebView();
                if (baseFragment != null) {
                    fragment = baseFragment;
                    fragment.setFragmentData(object);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        if (fragment == null)
            Log.e(LOG_TAG, "BaseFragment creation fail. ID:" + object.getID() + "  Name:" + object.getName());

        return fragment;
    }

    abstract public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    abstract protected void garbageCollectorCall();

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


        if (getAutoHandlePermissions())
            handlePermissions();
    }

    protected boolean getAutoHandlePermissions() {
        return true;
    }

    protected String[] requirePermissions() {
        return null;
    }

    protected String[] getFriendlyPermissionsMeaning() {return null;}

    protected void handlePermissions() {
        PermissionsHelper permissionsHelper = createPermissionsHelper();
        permissionsHelper.check(requirePermissions(),getFriendlyPermissionsMeaning());
    }

    protected PermissionsHelper createPermissionsHelper() {
        PermissionsHelper permissionsHelper = new PermissionsHelper(getContext());
        permissionsHelper.setShowRationaleConfirm(getShowRationaleConfirm());
        permissionsHelper.setOnPermission(onPermission);
        permissionsHelper.setRationaleConfirmText(getRationaleConfirmText());
        permissionsHelper.setRationaleMessage(getRationaleMessage());
        permissionsHelper.setShowDeniedMessage(getShowDeniedMessage());
        permissionsHelper.setDeniedCloseButtonText(getDeniedCloseButtonText());
        permissionsHelper.setDeniedMessage(getDeniedMessage());

        return permissionsHelper;
    }

    protected boolean getShowDeniedMessage() {
        return true;
    }

    protected boolean getShowRationaleConfirm() {
        return true;
    }

    protected void permissionGranted() {

    }

    protected void permissionDenied(ArrayList<String> arrayList) {

    }

    protected String getDeniedCloseButtonText() {
        return getString(R.string._permission_denied_close_button_text);
    }

    protected String getRationaleConfirmText() {
        return getString(R.string._permission_rationale_confirm_text);
    }

    protected String getRationaleMessage() {
        return getString(R.string._permission_rationale_message);
    }

    protected String getDeniedMessage() {
        return getString(R.string._permission_denied_message);
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

    public IFragmentData getFragmentData()
    {
        return fragmentData;
    }

    public void setFragmentData(IFragmentData object)
    {
        this.fragmentData = object;
        configFragmentData(fragmentData);
    }

    public Object getPramas()
    {
        if(fragmentData!=null)
            return fragmentData.getParam();
        return null;
    }

    public void setPramas(Object object)
    {
        if (fragmentData != null)
            fragmentData.setParam(object);
    }
}
