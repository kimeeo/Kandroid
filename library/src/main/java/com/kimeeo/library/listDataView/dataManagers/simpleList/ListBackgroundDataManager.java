package com.kimeeo.library.listDataView.dataManagers.simpleList;

import android.content.Context;
import android.os.AsyncTask;

import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.IListProvider;
import com.kimeeo.library.listDataView.dataManagers.PageData;

import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
public class ListBackgroundDataManager extends DataManager {

    private static final String LOG_TAG = "BaseDataManager";

    private IListProvider listProvider;

    public ListBackgroundDataManager(Context context, IListProvider listProvider) {
        super(context);
        this.listProvider = listProvider;
    }

    public void garbageCollectorCall() {
        super.garbageCollectorCall();
        listProvider = null;
    }

    protected void callService(String url) {
        boolean isRefreshPage = isRefreshPage(getPageData(), url);
        BackgroundTask task;
        if (isRefreshPage)
            task = new BackgroundTask(url, listProvider, getPageData(), getRefreshDataServerCallParams(getPageData()));
        else
            task = new BackgroundTask(url, listProvider, getPageData(), getNextDataServerCallParams(getPageData()));
        task.execute();
    }

    protected String getNextDataURL(PageData data) {
        return data.curruntPage + "";
    }

    protected void parseData(String url, Object value, Object status) {
        boolean isRefreshPage = isRefreshPage(getPageData(), url);
        if (isRefreshPage == false)
            updatePagingData();
        dataLoadingDone((List<?>) value, isRefreshPage);
    }

    protected void updatePagingData() {
        try {
            getPageData().curruntPage += 1;
            getPageData().totalPage += 1;
        } catch (Exception e) {
            getPageData().curruntPage = getPageData().totalPage = 1;
        }
    }

    protected boolean isRefreshPage(PageData pageData, String url) {
        if (getRefreshDataURL(pageData) != null)
            return getRefreshDataURL(pageData).equals(url);
        return false;
    }

    final public Class<BaseDataParser> getLoadedDataParsingAwareClass() {
        return null;
    }

    private class BackgroundTask extends AsyncTask<Void, Void, List<?>> {

        private String url;
        private IListProvider listProvider;
        private PageData pageData;
        private Map<String, Object> param;

        public BackgroundTask(String url, IListProvider listProvider, PageData pageData, Map<String, Object> param) {
            this.listProvider = listProvider;
            this.pageData = pageData;
            this.param = param;
            this.url = url;
        }

        protected void onPreExecute() {

        }

        @Override
        protected List<?> doInBackground(Void... params) {
            List<?> data = listProvider.getList(pageData, param);
            return data;
        }

        @Override
        protected void onPostExecute(List<?> data) {
            dataHandler(url, data, "DONE");
        }
    }
}
