package com.kimeeo.library.listDataView;

import android.os.Bundle;

import com.kimeeo.library.fragments.BaseFragment;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.OnCallService;

/**
 * Created by bhavinpadhiyar on 7/20/15.
 */
abstract public class BaseListDataView extends BaseFragment implements OnCallService
{
    private DataManager dataManager;
    abstract protected DataManager createDataManager();
    protected void garbageCollectorCall() {
        if(dataManager!=null) {
            dataManager.garbageCollectorCall();
            dataManager = null;
        }
    }
    protected void configViewParam()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = createDataManager();
        configDataManager(dataManager);
    }

    protected void configDataManager(DataManager dataManager) {

    }

    public DataManager getDataManager()
    {
        return dataManager;
    }
    protected void loadNext(){
        dataManager.loadNext();
    }
    protected void loadRefreshData()
    {
        dataManager.loadRefreshData();
    }
}
