package com.kimeeo.library.listDataView.dataManagers.directoryListDataManager;

import android.Manifest;
import android.content.Context;

import com.gun0912.tedpermission.PermissionListener;
import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.PermissionsBasedDataManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
abstract public class DirectoryDataManager extends PermissionsBasedDataManager {

    private static final String LOG_TAG= "BaseDataManager";

    public DirectoryDataManager(Context context) {
        super(context);
    }

    public boolean isFileList()
    {
        return true;
    }

    @Override
    public String[] requirePermissions() {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    }

    public String[] getFriendlyPermissionsMeaning() {
        return new String[]{"Storage"};
    }

    protected void callService(final String path)
    {
        PermissionListener permissionListener = new PermissionListener()
        {
            @Override
            public void onPermissionGranted() {
                permissionGranted(path);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> arrayList) {
                permissionDenied(arrayList);
                dataHandler(path, null, "ERROR");
            }
        };
        invokePermission(permissionListener);
    }

    protected void permissionDenied(ArrayList<String> arrayList) {

    }

    protected void permissionGranted(String path) {
        File directory = new File(path);
        boolean isRefreshPage = isRefreshPage(getPageData(), path);
        File file[] = null;
        if (directory != null && directory.exists() && directory.isDirectory())
            file = directory.listFiles();

        if (file != null && file.length != 0) {
            if (isRefreshPage)
                removeAll(this);

            if (isFileList()) {
                List<File> data1 = new ArrayList<>();
                for (int i = 0; i < file.length; i++) {
                    data1.add(file[i]);
                }
                dataHandler(path, data1, "DONE");
            } else {
                List<String> data1 = new ArrayList<>();
                for (int i = 0; i < file.length; i++) {
                    data1.add(file[i].getAbsolutePath());
                }
                dataHandler(path, data1, "DONE");
            }
        }
        else
        {
            dataHandler(path, null, "ERROR");
        }
    }

    protected void parseData(String url, Object value, Object status)
    {
        boolean isRefreshPage = isRefreshPage(getPageData(),url);
        if(isRefreshPage==false)
            updatePagingData();
        dataLoadingDone((List<?>)value,isRefreshPage);
    }
    protected void updatePagingData() {
        try
        {
            getPageData().curruntPage +=1;
            getPageData().totalPage +=1;
        }catch (Exception e)
        {
            getPageData().curruntPage=getPageData().totalPage=1;
        }
    }

    protected boolean isRefreshPage(PageData pageData,String url)
    {
        if(getRefreshDataURL(pageData)!=null)
            return getRefreshDataURL(pageData).equals(url);
        return false;
    }


    final public Class<BaseDataParser> getLoadedDataParsingAwareClass()
    {
        return null;
    }
}
