package com.kimeeo.library.listDataView.dataManagers;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 12/25/15.
 */
public interface OnCallService
{
    void onDataReceived(String url,Object value,Object status);
    void onDataLoadError(String url, Object status);
    void onCallStart();
    void onCallEnd(List<?> dataList,boolean isRefreshPage);
    void onLastCallEnd();
    void onFirstCallEnd();
}